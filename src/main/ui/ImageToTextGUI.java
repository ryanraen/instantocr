package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ui.gui.tabs.ConversionTab;
import ui.gui.tabs.HistoryTab;

// GUI interface for Image to Text conversion application
// Adapted from https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/tree/main/SmartHome
public class ImageToTextGUI extends JFrame {

    public static void main(String args[]) {
        // stub
    }
    
    // MODIFIES: this
    // EFFECTS: constructs ImageToTextGUI, loads selectable images, displays navigation tabs and buttons
    private ImageToTextGUI() {
        // stub
    }

    // EFFECTS: returns the ImageToTextGUI object controlled by this UI
    public ImageToTextGUI getGUI() {
        return null; // stub
    }

    // MODIFIES: this
    // EFFECTS: loads image file paths found in data/images
    private void loadImages() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: adds conversion tab and history tab to this UI
    private void loadTabs() {
        // stub
    }

    // EFFECTS: returns menu bar of this UI
    public JTabbedPane getTabbedPane() {
        return null; // stub
    }
    
}
