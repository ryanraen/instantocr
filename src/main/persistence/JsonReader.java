package persistence;

import java.io.IOException;

import org.json.JSONObject;

import model.ConversionHistory;

// Represents a reader that reads conversion history from JSON data stored in file
// Adapted from the CPSC 210 JsonSerializationDemo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {

    // EFFECTS: constructs JSON reader to read from given path
    public JsonReader(String path) {
        // stub
    }

    // EFFECTS: reads ConversionHistory from file and returns it
    // throws IOException if an error arises when reading data from file
    public ConversionHistory read() throws IOException {
        return null; // stub
    }

    // EFFECTS: reads file at given path as String and returns it
    // throws IOException if an error occurs reading data from file
    private String readFile(String path) throws IOException {
        return null; // stub
    }

    // EFFECTS: parses ConversionHistory from JSON object and returns it
    private ConversionHistory parseHistory(JSONObject jsonObject) {
        return null; // stub
    }

    // MODIFIES: history
    // EFFECTS: parses ImageConversions from JSON object and adds them to history
    private void addConversions(ConversionHistory history, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: history
    // EFFECTS: parses ImageConversion from JSON object and adds it to history
    private void addConversion(ConversionHistory history, JSONObject jsonObject) {
        // stub
    }

}
