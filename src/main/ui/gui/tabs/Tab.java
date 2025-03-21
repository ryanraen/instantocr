package ui.gui.tabs;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import ui.ImageToTextGUI;

// A tab in the Image to Text application GUI
// Adapted from https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/tree/main/SmartHome
public abstract class Tab extends JPanel {

    private final ImageToTextGUI controller;
    
    // REQUIRES: ImageToTextGUI controller that holds this tab
    public Tab(ImageToTextGUI controller) {
        this.controller = controller;
    }

    // EFFECTS: returns the ImageToTextGUI controller for this tab
    public ImageToTextGUI getController() {
        return controller;
    }

}
