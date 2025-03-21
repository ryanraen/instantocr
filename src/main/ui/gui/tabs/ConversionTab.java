package ui.gui.tabs;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ui.ImageToTextGUI;

// Conversion tab of Image to Text application GUI
// Adapted from https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/tree/main/SmartHome
public class ConversionTab extends Tab {
    private static final int IMAGE_RENDER_WIDTH = (int) (ImageToTextGUI.WIDTH * 0.5);
    private static final int IMAGE_RENDER_HEIGHT = (int) (ImageToTextGUI.HEIGHT * 0.5);

    private JList<String> selectionList;
    private int selectedIndex;
    private List<Image> images;
    private JLabel imageArea;
    private JScrollPane extractedTextPane;
    private JTextArea extractedTextArea;
    private JButton convertButton;

    // REQUIRES: controller holds this tab
    // EFFECTS: constructs a conversion tab for image conversion functionality
    // with list of selectable images, selected image information, and buttons
    public ConversionTab(ImageToTextGUI controller) {
        super(controller);

        setLayout(new GridLayout(2, 2));

        images = new ArrayList<>();
        for (String path : getController().getSelection()) {
            try {
                images.add(ImageIO.read(new File(path)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ImageIcon defaultIcon = new ImageIcon();
        try {
            defaultIcon.setImage(ImageIO.read(new File("data\\blank.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageArea = new JLabel();
        selectedIndex = -1;

        renderImage(defaultIcon);
        add(imageArea);
        placeImageList();
        placeExtractedTextArea();
        placeConvertButton();
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
        String buttonName = "Convert!";
        convertButton = new JButton(buttonName);
        convertButton.setSize((int) (WIDTH * 0.5), (int) (HEIGHT * 0.5));
        addConvertButtonListener(buttonName);
        add(convertButton);
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
        extractedTextPane = new JScrollPane(new JTextArea(10, 10));
        extractedTextArea = new JTextArea("", 10, 10);
        extractedTextArea.setVisible(true);

        add(extractedTextPane);
    }

}
