package ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import com.formdev.flatlaf.FlatIntelliJLaf;

import model.ConversionHistory;
import model.Event;
import model.EventLog;
import model.ImageConversion;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tabs.ConversionTab;
import ui.tabs.HistoryTab;

// GUI interface for Image to Text conversion application
// Adapted from https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/tree/main/SmartHome
public class ImageToTextGUI extends JFrame {
    public static final int CONVERSION_TAB_INDEX = 0;
    public static final int HISTORY_TAB_INDEX = 1;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private static final String JSON_STORE = "./data/persistence/history.json";

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem saveOption;
    private JMenuItem loadOption;

    private JPanel conversionTab;
    private JPanel historyTab;
    private JTabbedPane navBar;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private ImageConversion conversion;
    private ConversionHistory history;
    private List<String> imageSelection;

    public static void main(String[] args) {
        FlatIntelliJLaf.setup();
        new ImageToTextGUI();
    }

    // MODIFIES: this
    // EFFECTS: constructs ImageToTextGUI, loads selectable images, displays
    // navigation tabs and buttons
    private ImageToTextGUI() {
        super("Image to Text Converter");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        addClosingListener();

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

    // MODIFIES: this
    // EFFECTS: adds window listener to print event logs upon closing the app
    public void addClosingListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                for (Event event : EventLog.getInstance()) {
                    System.out.println(formatter.format(event.getDate()) + ": " + event.getDescription());
                }
                System.exit(0);
            }
        });
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
    // EFFECTS: converts selected image and returns extracted text
    public String convertImage(String path) {
        conversion.readImage(path);
        conversion.processImage();
        history.addConversion(conversion);
        return conversion.getExtractedText();
    }

    // MODIFIES: this
    // EFFECTS: replaces current conversion with a new instance
    public void initializeConversion() {
        conversion = new ImageConversion();
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
        conversionTab = new ConversionTab(this);
        historyTab = new HistoryTab(this);

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
        } catch (FileNotFoundException e) {
            // unable to save history
        }
    }

    // MODIFIES: this
    // EFFECTS: loads conversion history from file
    private void loadHistory() {
        try {
            history = jsonReader.read();
        } catch (IOException e) {
            // unable to load history
        }
        HistoryTab historyTab = (HistoryTab) navBar.getComponent(HISTORY_TAB_INDEX);
        historyTab.updateHistoryList();
    }

    // EFFECTS: returns navigation bar of this UI
    public JTabbedPane getNavBar() {
        return navBar;
    }

}
