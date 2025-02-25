package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.ConversionHistory;
import model.ImageConversion;

// Represents a reader that reads conversion history from JSON data stored in file
// Adapted from the CPSC 210 JsonSerializationDemo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String path;

    // EFFECTS: constructs JSON reader to read from given path
    public JsonReader(String path) {
        this.path = path;
    }

    // EFFECTS: reads ConversionHistory from file and returns it
    // throws IOException if an error arises when reading data from file
    public ConversionHistory read() throws IOException {
        String jsonData = readFile(path);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseHistory(jsonObject);
    }

    // EFFECTS: reads file at given path as String and returns it
    // throws IOException if an error occurs reading data from file
    private String readFile(String path) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ConversionHistory from JSON object and returns it
    private ConversionHistory parseHistory(JSONObject jsonObject) {
        ConversionHistory history = new ConversionHistory();
        addConversions(history, jsonObject);
        return history;
    }

    // MODIFIES: history
    // EFFECTS: parses ImageConversions from JSON object and adds them to history
    private void addConversions(ConversionHistory history, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("conversions");
        for (Object json : jsonArray) {
            JSONObject nextConversion = (JSONObject) json;
            addConversion(history, nextConversion);
        }
    }

    // MODIFIES: history
    // EFFECTS: parses ImageConversion from JSON object and adds it to history
    private void addConversion(ConversionHistory history, JSONObject jsonObject) {
        String filePath = jsonObject.getString("filePath");
        String extractedText = jsonObject.getString("extractedText");
        ImageConversion conversion = new ImageConversion(filePath, extractedText);
        history.addConversion(conversion);
    }

}
