package model;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;

// Represents a list of previous image conversion instances
public class ConversionHistory {
    private List<ImageConversion> conversions;

    // EFFECTS: initializes list of previous converions as empty list
    public ConversionHistory() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.conversions = new ArrayList<>();
    }

    public List<ImageConversion> getConversions() {
        return this.conversions;
    }

    // EFFECTS: adds the given conversion to the conversion history
    public void addConversion(ImageConversion conversion) {
        this.conversions.add(conversion);
    }

    // REQUIRES: index <= size of list conversion history
    // EFFECTS: returns the index-th (starting from 1) most recent conversion
    public ImageConversion getByIndex(int index) {
        return conversions.get(conversions.size() - index);
    }

    // EFFECTS: retrieves the least recent (earliest) conversion instance
    // that has the given file path; returns null if not found
    public ImageConversion getByFilePath(String filePath) {
        for (ImageConversion conv : conversions) {
            if (conv.getFilePath().equals(filePath)) {
                return conv;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: clears conversion history
    public void clear() {
        conversions.clear();
    }

    // MODIFIES: this
    // EFFECTS: deletes the index-th (starting from 1) most recent conversion
    // and returns true; returns false if index > conversions size
    public boolean deleteByIndex(int index) {
        if (index > conversions.size()) {
            return false;
        } else {
            conversions.remove(conversions.size() - index);
            return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes the least recent conversion instance that
    // has the given file path and returns true;
    // returns false if not found
    public boolean deleteByFilePath(String filePath) {
        for (int i = 0; i < conversions.size(); i++) {
            if (conversions.get(i).getFilePath().equals(filePath)) {
                conversions.remove(i);
                return true;
            }
        }
        return false;
    }

}
