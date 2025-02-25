package ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.ConversionHistory;
import model.ImageConversion;

// Image to text conversion application with 
// a current conversion instance and conversion history
// Inspiration taken from the CPSC 210 TellerApp:
// https://github.students.cs.ubc.ca/CPSC210/TellerApp
public class ImageToTextApp {
    ImageConversion conv;
    ConversionHistory history;
    Scanner input;
    List<String> selection;

    // EFFECTS: runs the image to text application
    public ImageToTextApp() {
        init();
        processInput();
    }

    // MODIFIES: this
    // EFFECTS: initializes fields
    private void init() {
        conv = new ImageConversion();
        history = new ConversionHistory();
        input = new Scanner(System.in);
        input.useDelimiter(System.getProperty("line.separator"));
        selection = new ArrayList<>();

        File[] files = new File("data\\images").listFiles();
        for (File file : files) {
            selection.add(file.getPath());
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void processInput() {
        boolean terminate = false;
        String command = null;

        System.out.println("Welcome to the Image to Text Conversion Tool!\n");

        while (!terminate) {
            conv = new ImageConversion();
            displayCommands();
            command = input.next().toLowerCase();

            if (command.equals("q")) {
                terminate = true;
            } else {
                handleCommand(command);
            }
        }
        System.out.println("See you next time!");
        input.close();
    }

    // MODIFIES: this
    // EFFECTS: handles user commands
    private void handleCommand(String command) {
        switch (command) {
            case "s":
                selectImage();
                break;
            case "v":
                selectHistory();
                break;
            default:
                System.out.println("Invalid command...\n");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: prompts user to choose an image from data/image,
    // stores file path in the ImageConversion instance
    private void selectImage() {
        displaySelectable();

        String choice = input.next().toLowerCase();
        int numericalChoice = 0;
        if (choice.equals("m")) {
            return;
        } else {
            try {
                numericalChoice = Integer.parseInt(choice);
                if (numericalChoice > 0 && numericalChoice <= selection.size()) {
                    conv.readImage(selection.get(numericalChoice - 1));
                    System.out.println("Selected successfully");
                    System.out.println("Adding instance to history...\n");
                    history.addConversion(conv);
                    extractText();
                } else {
                    System.out.println("Invalid selection!");
                    System.out.println("Returning to main menu...\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection!");
                System.out.println("Returning to main menu...\n");
            }

        }
    }

    // REQUIRES: current image conversion instance has an uploaded image
    // MODIFIES: this
    // EFFECTS: processes image and stores extracted text
    private void extractText() {
        boolean returnToMenu = false;
        processImage();

        while (!returnToMenu) {
            String choice = input.next().toLowerCase();

            switch (choice) {
                case "y":
                    System.out.println("Extracted Text:");
                    System.out.println("\"" + conv.getExtractedText() + "\"\n");
                    System.out.println("Press any key to return to the main menu");
                    input.next();
                    returnToMenu = true;
                    break;
                case "n":
                    System.out.println("Press any key to return to the main menu");
                    input.next();
                    returnToMenu = true;
                    break;
                default:
                    System.out.println("Invalid choice...\n");
                    break;
            }
        }
    }

    // EFFECTS: displays selectable images
    private void displaySelectable() {
        System.out.println("Select an image from the available options:");
        for (int i = 1; i <= selection.size(); i++) {
            System.out.println("[" + i + "] - " + selection.get(i - 1));
        }
        System.out.println("...\n");
        System.out.println("[m] - Go back to main menu");
    }

    // MODIFIES: this
    // EFFECTS: processes image and displays extraction view options
    private void processImage() {
        System.out.println("Processing image...");
        conv.processImage();
        System.out.println("Text extracted successfully!\n");

        System.out.println("Would you like to view the extracted text?");
        System.out.println("[y] - Yes");
        System.out.println("[n] - No");
    }

    // EFFECTS: displays user command choices
    private void displayCommands() {
        System.out.println("Select an option:");
        System.out.println("[s] - Select an image for conversion");
        System.out.println("[v] - View conversion history");
        System.out.println("...\n");
        System.out.println("[q] - Quit");
    }

    // EFFECTS: selects a previous image conversion from the history
    private void selectHistory() {
        displayHistory();

        String choice = input.next().toLowerCase();
        int numericalChoice = 0;
        if (choice.equals("m")) {
            return;
        } else {
            try {
                numericalChoice = Integer.parseInt(choice);
                if (numericalChoice > 0 && numericalChoice <= history.getConversions().size()) {
                    displayConversion(numericalChoice);
                } else {
                    System.out.println("Invalid selection!");
                    System.out.println("Returning to main menu...\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid selection!");
                System.out.println("Returning to main menu...\n");
            }

        }

    }

    // EFFECTS: displays the user's conversion history
    // in the current session as a list, and allows user
    // to select a previous conversion for review
    private void displayHistory() {
        System.out.println("Select a past conversion to retrieve details or remove:");
        for (int i = 1; i <= history.getConversions().size(); i++) {
            System.out.println("[" + i + "] - " + history.getByIndex(i).getFilePath());
        }
        System.out.println("...\n");

        System.out.println("[m] - Go back to main menu");
    }

    // EFFECTS: displays the file path and extracted text from
    // a previous image conversion
    private void displayConversion(int index) {
        ImageConversion conversion = history.getByIndex(index);
        boolean returnToMenu = false;
        System.out.println("Image File: " + conversion.getFilePath());
        System.out.println("Extracted Text:\n\"" + conversion.getExtractedText() + "\"");

        System.out.println("\nSelect an option:");
        System.out.println("[r] - Remove the selected conversion from history");
        System.out.println("[m] - Go back to main menu");

        while (!returnToMenu) {
            String choice = input.next().toLowerCase();
            if (choice.equals("r")) {
                removeSelected(index);
                System.out.println("Removed successfully!");
                System.out.println("Press any key to return to the main menu");
                input.next();
                returnToMenu = true;
            } else if (choice.equals("m")) {
                System.out.println("Returning to main menu...");
                returnToMenu = true;
            } else {
                System.out.println("Invalid choice...\n");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the given index-th conversion instance from history
    private void removeSelected(int index) {
        history.deleteByIndex(index);
    }

}
