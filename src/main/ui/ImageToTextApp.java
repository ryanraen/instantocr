package ui;

import model.ImageConversion;

// Image to text conversion application with 
// a conversion instance and conversion history
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

    // REQUIRES: path must be a valid file path to an image on the user device
    // MODIFIES: this
    // EFFECTS: stores file path in the ImageConversion instance and reads it as an image
    private void uploadImage(String path) {
        // stub
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

    // REQUIRES: id < size of conversion history, and
    // id must be the index of a valid ImageConversion 
    // instance in the conversion history
    // EFFECTS: displays the file path and extracted text from
    // a previous image conversion
    private void displayPrevConversion(int id) {
        // stub
    }
    
}
