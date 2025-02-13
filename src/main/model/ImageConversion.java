package model;

import java.util.List;

import org.opencv.core.Mat;

// Represents an image conversion having a file path, matrix that holds the image
// a list of matrices holding sliced sub-images of individual characters, 
// a black and white polarization threshold constant, 
// the standard width and height of comparison templates,
// a list of character templates, and the extracted text
public class ImageConversion {

    // EFFECTS: initializes filePath, extractedText as empty Strings, and
    // initializes imageMat, subImages, and templates as null
    public ImageConversion() {
        // stub
    }

    // EFFECTS: initializes filePath with given filePath, and set
    // appropriate values for all other fields (refer to void constructor)
    public ImageConversion(String filePath) {
        // stub
    }

    public String getFilePath() {
        return null; // stub
    }

    public Mat getImageMat() {
        return null; // stub
    }

    public List<Mat> getSubImages() {
        return null; // stub
    }

    public double getThreshold() {
        return 0; // stub
    }

    public List<CharacterTemplate> getTemplates() {
        return null; // stub
    }

    public String getExtractedText() {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: reads all template images in data/templates
    // and stores in character templates
    public void initTemplates() {
        // stub
    }

    // REQUIRES: this image conversion instance must have an existing
    // stored valid filePath
    // MODIFIES: this
    // EFFECTS: reads the image at filePath and stores it in imageMat
    public void readImage() {
        // stub
    }

    // REQURES: path must be a valid file path to an image in data/images
    // MODIFIES: this
    // EFFECTS: stores path as filePath, reads the image at path,
    // and stores it in imageMat
    public void readImage(String path) {
        // stub
    }

    // REQUIRES: imageMat must be a valid image containing lines
    // of Robotto font plain text
    // MODIFIES: this
    // EFFECTS: processes image and saves recognized text in extractedText
    public void processImage() {
        // stub
    }

    // REQUIRES: imageMat must contain a standard RGB image
    // MODIFIES: this
    // EFFECTS: converts image to grayscale
    public void toGrayscale() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: crops image to the outermost black pixel
    public void crop(Mat image) {
        // stub
    }

    // REQUIRES: imageMat must contain an image in grayscale
    // MODIFIES: this
    // EFFECTS: convert all pixels to either black or white using numerical
    // threshold
    public void polarizeImage() {
        // stub
    }

    // REQUIRES: imageMat must contain an image polarized grayscale image
    // there must be at least 1 pixel of white margin between adjacent
    // text characters in the image
    // MODIFIES: this
    // EFFECTS: slices image into a list of sub-images containing
    // 1 text character each, in the order that they appear left-to-right
    public void sliceImage() {
        // stub
        // Note to self: slice out rows first then columns
    }

    // REQUIRES: width > 0 && height > 0 
    // image must be a polarized and slice grayscale pixel matrix
    // EFFECTS: returns resized image that matches given width and height
    public Mat resizeImage(Mat image, int width, int height) {
        return null; // stub
    }

    // REQUIRES: image must have the same dimensions as each template in templates,
    // given image matrix must contain exactly 1 recognizable English character,
    // and this character must match an existing template in templates
    // EFFECTS: compares a sliced image with all template character images and
    // assigns a similarity score out of 1 for the comparison; returns English
    // character with greatest similarity score
    public char compareWithTemplates(Mat image, List<CharacterTemplate> templates) {
        return 'a'; // stub
    }

}