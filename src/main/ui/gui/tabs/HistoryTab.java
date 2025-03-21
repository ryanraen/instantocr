package ui.gui.tabs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.ImageToTextGUI;

// History tab of Image to Text application GUI
// Adapted from https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/tree/main/SmartHome
public class HistoryTab extends Tab {
    private JList<String> historyList;
    private int selectedIndex;

    private JScrollPane listScrollPane;
    private JScrollPane extractedTextPane;
    private JTextArea extractedTextArea;
    private JButton deleteButton;
    private JButton clearButton;

    // REQUIRES: controller holds this tab
    // EFFECTS: creates history tab with historical conversions list and buttons
    public HistoryTab(ImageToTextGUI controller) {
        super(controller);

        setLayout(new GridLayout(2, 2));

        selectedIndex = -1;

        placeHistoryList();
        add(listScrollPane, 0);
        placeConversionText();
        placeDeleteButton();
        placeClearButton();
    }

    // MODIFIES: this
    // EFFECTS: creates area that displays list of past conversions
    private void placeHistoryList() {
        listScrollPane = new JScrollPane();
        historyList = new JList<>();
        int length = getController().getHistory().getConversions().size();
        String[] pathArray = new String[length];
        for (int i = 0; i < length; i++) {
            pathArray[i] = getController().getHistory().getConversions().get(i).getFilePath();
        }
        historyList.setListData(pathArray);
        addSelectionListener();

        listScrollPane.setViewportView(historyList);
    }

    // REQUIRES: this contains the listScrollPane component
    // MODIFIES: this
    // EFFECTS: updates area that displays history list
    public void updateHistoryList() {
        remove(listScrollPane);
        placeHistoryList();
        extractedTextArea.setText("");
        extractedTextPane.setViewportView(extractedTextArea);
        add(listScrollPane, 0);
    }

    // MODIFIES: this
    // EFFECTS: adds a listener to selectionList to detect changes in image list
    // selection
    private void addSelectionListener() {
        historyList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (event.getSource() instanceof JList<?>) {
                    selectedIndex = ((JList<?>) event.getSource()).getSelectedIndex();
                }
                extractedTextArea
                        .setText(getController().getHistory().getConversions().get(selectedIndex).getExtractedText());
                extractedTextPane.setViewportView(extractedTextArea);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates area that displays selected conversion information
    private void placeConversionText() {
        extractedTextPane = new JScrollPane(new JTextArea(10, 10));
        extractedTextArea = new JTextArea("", 10, 10);
        extractedTextArea.setVisible(true);

        add(extractedTextPane);
    }

    // MODIFIES: this
    // EFFECTS: creates Delete buttons
    private void placeDeleteButton() {
        String deleteButtonName = "Delete";
        deleteButton = new JButton(deleteButtonName);
        deleteButton.setSize((int) (WIDTH * 0.5), (int) (HEIGHT * 0.25));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(deleteButtonName)) {
                    if (selectedIndex != -1) {
                        getController().getHistory().getConversions().remove(selectedIndex);
                        updateHistoryList();
                        validate();
                    }
                }
            }
        });

        add(deleteButton);
    }

    // MODIFIES: this
    // EFFECTS: creates Clear button
    private void placeClearButton() {
        String clearButtonName = "Clear";
        clearButton = new JButton(clearButtonName);
        clearButton.setSize((int) (WIDTH * 0.5), (int) (HEIGHT * 0.25));
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(clearButtonName)) {
                    getController().getHistory().clear();
                    updateHistoryList();
                    validate();
                }
            }
        });

        add(clearButton);
    }

}
