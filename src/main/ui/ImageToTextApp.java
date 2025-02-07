package ui;

import java.awt.Image;

import model.ImageConversion;

// Image to text conversion application with 
// a current conversion instance and conversion history
public class ImageToTextApp {

    // EFFECTS: runs the image to text application
    public ImageToTextApp() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: initializes conversion instance and conversion history
    private void init() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void processInput() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: handles user commands
    private void handleCommand(String command) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: prompts user to choose an image from data/image,
    // stores file path in the ImageConversion instance
    private void uploadImage() {
        // stub
    }

    // REQUIRES: current image conversion instance has an uploaded image
    // MODIFIES: this
    // EFFECTS: processes image and returns extracted text
    private String extractText() {
        return null; // stub
    }

    // EFFECTS: displays user command choices
    private void displayCommands() {
        // stub
    }

    // EFFECTS: displays the user's conversion history
    // in the current session as a list, and allows user
    // to select a previous conversion for review
    private void displayHistory() {
        // stub
    }

    // EFFECTS: displays the file path and extracted text from
    // a previous image conversion
    private void displayConversion(ImageConversion conversion) {
        // stub
    }
    
}
