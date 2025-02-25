package persistence;

import java.io.FileNotFoundException;

import model.ConversionHistory;

// Represents a writer that writes JSON representation of conversion history to file
// Adapted from the CPSC 210 JsonSerializationDemo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriter {

    // EFFECTS: constructs writer to write to dest(ination) file
    public JsonWriter(String dest) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: opens writer
    // throws FileNotFoundException if destination file
    // cannot be opened for writing
    public void open() throws FileNotFoundException {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of conversion history to file
    public void write(ConversionHistory history) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        // stub
    }
    
}
