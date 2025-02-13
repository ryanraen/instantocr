package model;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

// Represents a character template, having a file path, 
// a matrix that holds all pixels in the template image, 
// and a String holding the English alphabet identity of the template
public class CharacterTemplate {

    // REQUIRES: path be a relative path pointing to a valid template
    // image in the project data
    // EFFECTS: initializes the character template and reads
    // the image file at path, storing its file name as 
    // the label (excluding file extension)
    public CharacterTemplate(String path) {
        // stub
    }

    public String getFilePath() {
        return null; // stub
    }

    public Mat getImageMat() {
        return null; // stub
    }

    public char getLabel() {
        return 'a'; // stub
    }

}
