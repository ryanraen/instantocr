package ui.tabs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.ImageToTextGUI;

// History tab of Image to Text application GUI
// Adapted from https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/tree/main/SmartHome
public class HistoryTab extends Tab {
    private static final int HISTORY_LIST_WIDTH = (int) (ImageToTextGUI.WIDTH * 0.3);
    private static final int HISTORY_LIST_HEIGHT = ImageToTextGUI.HEIGHT;

    private static final int BUTTON_WIDTH = (int) (ImageToTextGUI.WIDTH * 0.3);
    private static final int BUTTON_HEIGHT = (int) (ImageToTextGUI.HEIGHT * 0.15);

    private JList<String> historyList;
    private int selectedIndex;

    private JScrollPane listScrollPane;
    private JScrollPane extractedTextPane;
    private JTextArea extractedTextArea;
    private JButton deleteButton;
    private JButton clearButton;
    private JPanel rightPanel;
    private JPanel buttonRow;

    // REQUIRES: controller holds this tab
    // EFFECTS: creates history tab with historical conversions list and buttons
    public HistoryTab(ImageToTextGUI controller) {
        super(controller);

        setLayout(new BoxLayout(this, 0));

        selectedIndex = -1;

        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, 1));

        placeHistoryList();
        add(listScrollPane, 0);

        placeConversionText();
        placeDeleteButton();
        placeClearButton();
        rightPanel.add(buttonRow);

        add(rightPanel);
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

        listScrollPane.setMaximumSize(new Dimension(HISTORY_LIST_WIDTH, HISTORY_LIST_HEIGHT));
        listScrollPane.setMinimumSize(new Dimension(HISTORY_LIST_WIDTH, HISTORY_LIST_HEIGHT));
        listScrollPane.setPreferredSize(new Dimension(HISTORY_LIST_WIDTH, HISTORY_LIST_HEIGHT));
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
        JLabel textAreaLabel = new JLabel("Extracted Text:");
        extractedTextPane = new JScrollPane(new JTextArea(10, 10));
        extractedTextArea = new JTextArea("", 10, 10);
        extractedTextArea.setVisible(true);

        rightPanel.add(textAreaLabel);
        rightPanel.add(extractedTextPane);
        textAreaLabel.setAlignmentX(CENTER_ALIGNMENT);
        textAreaLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 3, 10));
        extractedTextArea.setEditable(false);
        extractedTextArea.setBackground(Color.white);
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
                        selectedIndex = -1;
                    }
                }
            }
        });

        deleteButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        deleteButton.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        deleteButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        deleteButton.setAlignmentX(CENTER_ALIGNMENT);
        deleteButton.setAlignmentY(CENTER_ALIGNMENT);

        buttonRow = formatButtonRow(deleteButton);
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
                    selectedIndex = -1;
                }
            }
        });

        clearButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        clearButton.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        clearButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        clearButton.setAlignmentX(CENTER_ALIGNMENT);
        clearButton.setAlignmentY(CENTER_ALIGNMENT);

        buttonRow.add(clearButton);
    }

}
