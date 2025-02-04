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

    // REQUIRES: conversions must contain an ImageConversion instance with specified file path
    // EFFECTS: retrieves the least recent conversion instance that has the given file path
    public ImageConversion getByFilePath(String filePath) {
        return null; // stub
    }

    // REQUIRES: conversions must contain an ImageConversion instance with
    // specified file name; that is, its path ends with fileName; fileName must be
    // of the form .*\.(jpg|JPG|jpeg|JPEG|png|PNG|bmp|BMP)
    // EFFECTS: retrieves the least recent conversion instance whose file path ends with
    // fileName
    public ImageConversion getByFileName(String fileName) {
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

    // REQUIRES: conversions must contain an ImageConversion instance with
    // specified file name; that is, its path ends with fileName; fileName must be
    // of the form .+\.(jpg|JPG|jpeg|JPEG|png|PNG|bmp|BMP)
    // MODIFIES: this
    // EFFECTS: deletes the least recent conversion instance whose file path ends with
    // fileName
    public void deleteByFileName(String fileName) {
        // stub
    }
    
}
