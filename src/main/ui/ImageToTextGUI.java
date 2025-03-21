package ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import model.ConversionHistory;
import model.ImageConversion;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.gui.tabs.ConversionTab;
import ui.gui.tabs.HistoryTab;

// GUI interface for Image to Text conversion application
// Adapted from https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/tree/main/SmartHome
public class ImageToTextGUI extends JFrame {
    public static final int CONVERSION_TAB_INDEX = 0;
    public static final int HISTORY_TAB_INDEX = 1;

    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    private static final String JSON_STORE = "./data/persistence/history.json";

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem saveOption;
    private JMenuItem loadOption;

    private JTabbedPane navBar;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private ImageConversion conversion;
    private ConversionHistory history;
    private List<String> imageSelection;

    public static void main(String[] args) {
        new ImageToTextGUI();
    }

    // MODIFIES: this
    // EFFECTS: constructs ImageToTextGUI, loads selectable images, displays
    // navigation tabs and buttons
    private ImageToTextGUI() {
        super("Image to Text Converter");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        conversion = new ImageConversion();
        history = new ConversionHistory();
        imageSelection = new ArrayList<>();
        loadImages();

        navBar = new JTabbedPane();
        navBar.setTabPlacement(JTabbedPane.TOP);

        loadMenuBar();
        setJMenuBar(menuBar);

        loadTabs();
        add(navBar);

        setVisible(true);
    }

    // EFFECTS: returns the ImageConversion object controlled by this UI
    public ImageConversion getConversion() {
        return conversion;
    }

    // EFFECTS: returns the ConversionHistory object controlled by this UI
    public ConversionHistory getHistory() {
        return history;
    }

    // EFFECTS: returns the list of selectable images paths
    public List<String> getSelection() {
        return imageSelection;
    }

    // MODIFIES: this
    // EFFECTS: loads image file paths found in data/images
    private void loadImages() {
        File[] files = new File("data\\images").listFiles();
        for (File file : files) {
            imageSelection.add(file.getPath());
        }
    }

    // MODIFIES: this
    // EFFECTS: adds conversion tab and history tab to this UI
    private void loadTabs() {
        JPanel conversionTab = new ConversionTab(this);
        JPanel historyTab = new HistoryTab(this);

        navBar.add(conversionTab, CONVERSION_TAB_INDEX);
        navBar.setTitleAt(CONVERSION_TAB_INDEX, "Convert");

        navBar.add(historyTab, HISTORY_TAB_INDEX);
        navBar.setTitleAt(HISTORY_TAB_INDEX, "History");
    }

    // MODIFIES: this
    // EFFECTS: adds menubar with save and load options
    private void loadMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        saveOption = new JMenuItem("Save");
        loadOption = new JMenuItem("Load");

        saveOption.addActionListener(e -> {
            saveHistory();
        });
        loadOption.addActionListener(e -> {
            loadHistory();
        });

        fileMenu.add(saveOption);
        fileMenu.add(loadOption);

        menuBar.add(fileMenu);
    }

    // EFFECTS: saves the conversion history to file
    private void saveHistory() {
        try {
            jsonWriter.open();
            jsonWriter.write(history);
            jsonWriter.close();
            System.out.println("Saved conversion history to " + JSON_STORE + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads conversion history from file
    private void loadHistory() {
        try {
            history = jsonReader.read();
            System.out.println("Loaded conversion history from " + JSON_STORE + "\n");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: returns navigation bar of this UI
    public JTabbedPane getNavBar() {
        return navBar;
    }

}
