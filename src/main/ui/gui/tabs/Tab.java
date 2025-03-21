package ui.gui.tabs;

import javax.swing.JButton;
import javax.swing.JPanel;

import ui.ImageToTextGUI;

// A tab in the Image to Text application GUI
// Adapted from https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/tree/main/SmartHome
public abstract class Tab extends JPanel {
    
    // REQUIRES: ImageToTextGUI controller that holds this tab
    public Tab(ImageToTextGUI controller) {
        // stub
    }

    // EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        return null; // stub
    }

    // EFFECTS: returns the ImageToTextGUI controller for this tab
    public ImageToTextGUI getController() {
        return null; // stub
    }

}
