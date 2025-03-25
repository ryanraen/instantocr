package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.opencv.core.Core;

import persistence.Writable;

// Represents a list of previous image conversion instances
// JSON related methods adapted from the CPSC 210 JsonSerializationDemo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class ConversionHistory implements Writable {
    private List<ImageConversion> conversions;

    // EFFECTS: initializes list of previous converions as empty list
    public ConversionHistory() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.conversions = new ArrayList<>();
        EventLog.getInstance().logEvent(new Event("Successfully initialized conversion history."));
    }

    public List<ImageConversion> getConversions() {
        return this.conversions;
    }

    // MODIFIES: this
    // EFFECTS: adds the given conversion to the conversion history
    public void addConversion(ImageConversion conversion) {
        this.conversions.add(conversion);
        EventLog.getInstance().logEvent(new Event("Added conversion instance to history."));
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
        EventLog.getInstance().logEvent(new Event("Cleared conversion history."));
    }

    // MODIFIES: this
    // EFFECTS: deletes the index-th (starting from 1) most recent conversion
    // and returns true; returns false if index > conversions size
    public boolean deleteByIndex(int index) {
        if (index > conversions.size()) {
            EventLog.getInstance().logEvent(new Event("WARNING: could not delete conversion instance"
                    + "with index '" + index + "'."));
            return false;
        } else {
            conversions.remove(conversions.size() - index);
            EventLog.getInstance().logEvent(new Event("Deleted conversion instance from history."));
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
                EventLog.getInstance().logEvent(new Event("Deleted conversion instance from history."));
                return true;
            }
        }
        EventLog.getInstance().logEvent(new Event("WARNING: could not delete conversion instance"
                + "with file path '" + filePath + "'."));
        return false;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("conversions", conversionsToJson());
        return json;
    }

    // EFFECTS: returns conversions in this conversion history as a JSON array
    private JSONArray conversionsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (ImageConversion conv : conversions) {
            jsonArray.put(conv.toJson());
        }

        return jsonArray;
    }

}
