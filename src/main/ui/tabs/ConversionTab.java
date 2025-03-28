package ui.tabs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.ImageToTextGUI;

// Conversion tab of Image to Text application GUI
// Adapted from https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/tree/main/SmartHome
public class ConversionTab extends Tab {
    private static final int IMAGE_RENDER_WIDTH = (int) (ImageToTextGUI.WIDTH * 0.6);
    private static final int IMAGE_RENDER_HEIGHT = (int) (ImageToTextGUI.HEIGHT * 0.4);

    private static final int TEXT_AREA_WIDTH = (int) (ImageToTextGUI.WIDTH * 0.6);
    private static final int TEXT_AREA_HEIGHT = (int) (ImageToTextGUI.HEIGHT * 0.3);

    private static final int CONVERT_BUTTON_WIDTH = (int) (ImageToTextGUI.WIDTH * 0.3);
    private static final int CONVERT_BUTTON_HEIGHT = (int) (ImageToTextGUI.HEIGHT * 0.15);

    private JList<String> selectionList;
    private int selectedIndex;
    private List<Image> images;
    private JLabel imageArea;
    private JScrollPane extractedTextPane;
    private JTextArea extractedTextArea;
    private JButton convertButton;
    private JPanel leftPanel;

    // REQUIRES: controller holds this tab
    // EFFECTS: constructs a conversion tab for image conversion functionality
    // with list of selectable images, selected image information, and buttons
    public ConversionTab(ImageToTextGUI controller) {
        super(controller);

        // setLayout(new GridLayout(1, 2));
        setLayout(new BoxLayout(this, 0));

        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, 1));

        images = new ArrayList<>();
        for (String path : getController().getSelection()) {
            try {
                images.add(ImageIO.read(new File(path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        imageArea = new JLabel();
        selectedIndex = -1;

        placeComponents();
    }

    // MODIFIES: this
    // EFFECTS: places conversion tab components
    private void placeComponents() {
        ImageIcon defaultIcon = new ImageIcon();
        try {
            defaultIcon.setImage(ImageIO.read(new File("data\\blank.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        renderImage(defaultIcon);
        leftPanel.add(imageArea);
        placeImageList();
        placeExtractedTextArea();
        placeConvertButton();

        setComponentPref();

        add(leftPanel);
    }

    // MODIFIES: this
    // EFFECTS: sets appropriate layout preferences for components
    private void setComponentPref() {
        imageArea.setMaximumSize(new Dimension(IMAGE_RENDER_WIDTH, IMAGE_RENDER_HEIGHT));
        imageArea.setMinimumSize(new Dimension(IMAGE_RENDER_WIDTH, IMAGE_RENDER_HEIGHT));
        imageArea.setPreferredSize(new Dimension(IMAGE_RENDER_WIDTH, IMAGE_RENDER_HEIGHT));
        imageArea.setAlignmentX(CENTER_ALIGNMENT);
        imageArea.setAlignmentY(CENTER_ALIGNMENT);

        extractedTextPane.setMaximumSize(new Dimension(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT));
        extractedTextPane.setMinimumSize(new Dimension(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT));
        extractedTextPane.setPreferredSize(new Dimension(TEXT_AREA_WIDTH, TEXT_AREA_HEIGHT));
        extractedTextPane.setAlignmentX(CENTER_ALIGNMENT);
        extractedTextPane.setAlignmentY(CENTER_ALIGNMENT);

        convertButton.setMaximumSize(new Dimension(CONVERT_BUTTON_WIDTH, CONVERT_BUTTON_HEIGHT));
        convertButton.setMinimumSize(new Dimension(CONVERT_BUTTON_WIDTH, CONVERT_BUTTON_HEIGHT));
        convertButton.setPreferredSize(new Dimension(CONVERT_BUTTON_WIDTH, CONVERT_BUTTON_HEIGHT));
        convertButton.setAlignmentX(CENTER_ALIGNMENT);
        convertButton.setAlignmentY(CENTER_ALIGNMENT);
    }

    // MODIFIES: this
    // EFFECTS: creates area that renders the selected image
    private void renderImage(ImageIcon selectedIcon) {
        if (selectedIcon.getImage() != null) {
            int width = selectedIcon.getImage().getWidth(this);
            int height = selectedIcon.getImage().getHeight(this);
            if (Math.max(width, height) == width) {
                selectedIcon.setImage(selectedIcon.getImage()
                        .getScaledInstance(IMAGE_RENDER_WIDTH, -1, Image.SCALE_SMOOTH));
            } else {
                selectedIcon.setImage(selectedIcon.getImage()
                        .getScaledInstance(-1, IMAGE_RENDER_HEIGHT, Image.SCALE_SMOOTH));
            }

            imageArea.setIcon(selectedIcon);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates area that displays list of selectable images
    private void placeImageList() {
        JScrollPane scrollPane = new JScrollPane();
        selectionList = new JList<>();
        String[] pathArray = new String[getController().getSelection().size()];
        for (int i = 0; i < getController().getSelection().size(); i++) {
            pathArray[i] = getController().getSelection().get(i);
        }
        selectionList.setListData(pathArray);
        addSelectionListener();

        scrollPane.setViewportView(selectionList);

        add(scrollPane);
    }

    // MODIFIES: this
    // EFFECTS: adds a listener to selectionList to detect changes in image list
    // selection
    private void addSelectionListener() {
        selectionList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (event.getSource() instanceof JList<?>) {
                    selectedIndex = ((JList<?>) event.getSource()).getSelectedIndex();
                }
                renderImage(new ImageIcon(images.get(selectedIndex)));
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates Convert button
    private void placeConvertButton() {
        String buttonName = "Convert";
        convertButton = new JButton(buttonName);
        convertButton.setSize((int) (WIDTH * 0.5), (int) (HEIGHT * 0.5));
        addConvertButtonListener(buttonName);
        leftPanel.add(convertButton);
    }

    // MODIFIES: this
    // EFFECTS: adds action listener for convert button
    private void addConvertButtonListener(String buttonName) {
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals(buttonName)) {
                    if (selectedIndex == -1) {
                        extractedTextArea.setText("<No image selected>");
                        extractedTextPane.setViewportView(extractedTextArea);
                    } else {
                        String extractedText = getController().convertImage(
                                getController().getSelection().get(selectedIndex));
                        extractedTextArea.setText(extractedText);
                        extractedTextPane.setViewportView(extractedTextArea);
                        getController().initializeConversion();

                        HistoryTab tab = (HistoryTab) getController().getNavBar()
                                .getComponentAt(ImageToTextGUI.HISTORY_TAB_INDEX);
                        tab.updateHistoryList();
                    }
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates area that displays extracted text
    private void placeExtractedTextArea() {
        JLabel textAreaLabel = new JLabel("Extracted Text:");
        extractedTextPane = new JScrollPane(new JTextArea(10, 10));
        extractedTextArea = new JTextArea("", 10, 10);
        extractedTextArea.setVisible(true);

        leftPanel.add(textAreaLabel);
        leftPanel.add(extractedTextPane);
        textAreaLabel.setAlignmentX(CENTER_ALIGNMENT);
        textAreaLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 3, 10));
        extractedTextArea.setEditable(false);
        extractedTextArea.setBackground(Color.white);
    }

}
