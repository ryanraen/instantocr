package model;

import java.util.ArrayList;
import java.util.List;

// Represents a list of previous image conversion instances
public class ConversionHistory {

    // EFFECTS: initializes list of previous converions as empty list
    public ConversionHistory() {
        // stub
    }

    public List<ImageConversion> getConversions() {
        return null; // stub
    }

    // EFFECTS: adds the given conversion to the conversion history
    public void addConversion(ImageConversion conversion) {
        // stub
    }

    // REQUIRES: conversions must contain an ImageConversion instance with specified file path
    // EFFECTS: retrieves the least recent conversion instance that has the given file path
    public ImageConversion getByFilePath(String filePath) {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: clears conversion history
    public void clearHistory() {
        // stub
    }

    // REQUIRES: conversions must contain an ImageConversion instance with specified file path
    // MODIFIES: this
    // EFFECTS: deletes the least recent conversion instance that has the given file path
    public void deleteByFilePath(String filePath) {
        // stub
    }
    
}
