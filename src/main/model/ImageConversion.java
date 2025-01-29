package model;

import java.awt.image.BufferedImage;

// Represents an image conversion having a file path, uploaded image, 
// a list of sliced sub-images, a black and white polarization threshold, 
// a list of character templates, and the extracted text
public class ImageConversion {

    // EFFECTS: initializes filePath, extractedText as empty Strings,
    // initializes image as null, and initializes character template list
    public ImageConversion() {
        // stub
    }

    public String getFilePath() {
        return null; // stub
    }

    public BufferedImage getImage() {
        return null; // stub
    }

    public String getExtractedText() {
        return null; // stub
    }

    // REQURES: path must be a valid file path to an image on the user device
    // MODIFIES: this
    // EFFECTS: stores path as filePath, reads the image at path, 
    // and stores it in image
    public void readImage(String path) {
        // stub
    }

    // REQUIRES: image must be a valid image containing only standard lines of plain text
    // MODIFIES: this
    // EFFECTS: processes image and saves recognized text in extractedText
    public void processImage() {
        // stub
    }

    // REQUIRES: image must be a standard RGB BufferedImage
    // MODIFIES: this
    // EFFECTS: converts image to grayscale
    private void toGrayscale() {
        // stub
    }

    // REQUIRES: image must be in grayscale
    // MODIFIES: this
    // EFFECTS: convert all pixels to either black or white using numerical threshold
    private void polarizeImage() {
        // stub
    }

    // REQUIRES: there must be >= 1 pixel of white margin between text characters in image
    // MODIFIES: this
    // EFFECTS: slices image into a list of sub-images containing 1 text character each
    private void sliceImage() {
        // stub
        // Note to self: slice out rows first then columns
    }

    // MODIFIES: this
    // EFFECTS: compares each sub-image with all template character images and assigns 
    // a similarity score out of 1 for comparison, takes highest scored template character 
    // and appends the corresponding text character it to extractedText
    private void compareWithTemplates() {
        // stub
    }

}