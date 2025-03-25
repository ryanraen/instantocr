package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import persistence.Writable;

// Represents an image conversion having a file path, matrix that holds the image
// a list of matrices holding sliced sub-images of individual characters, 
// a black and white polarization threshold constant, 
// the standard width and height of comparison templates,
// a list of character templates, and the extracted text
// JSON related methods adapted from the CPSC 210 JsonSerializationDemo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class ImageConversion implements Writable {
    public static final int POLARIZE_THRESHOLD = 101;
    public static final int TEMPLATE_WIDTH = 8;
    public static final int TEMPLATE_HEIGHT = 16;
    private String filePath;
    private Mat imageMat;
    private List<Mat> subImages;
    private List<CharacterTemplate> templates;
    private String extractedText;

    // EFFECTS: initializes filePath, extractedText as empty Strings, and
    // initializes imageMat as null, subImages as empty list,
    // and set up all templates in data/templates
    public ImageConversion() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.filePath = "";
        this.imageMat = null;
        this.subImages = new ArrayList<>();
        this.templates = new ArrayList<>();
        this.extractedText = "";

        initTemplates();

        EventLog.getInstance()
                .logEvent(new Event("Successfully initialized "
                        + "new image conversion instance with no specified filepath."));
    }

    // EFFECTS: initializes filePath with given filePath, and set
    // appropriate values for all other fields (refer to void constructor)
    public ImageConversion(String filePath) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.filePath = filePath;
        this.imageMat = Imgcodecs.imread(filePath);
        this.subImages = new ArrayList<>();
        this.templates = new ArrayList<>();
        this.extractedText = "";

        initTemplates();

        EventLog.getInstance()
                .logEvent(new Event("Successfully initialized new image conversion instance for '" + filePath + "'."));
    }

    // EFFECTS: initializes filePath with given filePath,
    // extractedText with given extracted text, and set
    // appropriate values for all other fields (refer to void constructor)
    public ImageConversion(String filePath, String extractedText) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.filePath = filePath;
        this.imageMat = Imgcodecs.imread(filePath);
        this.subImages = new ArrayList<>();
        this.templates = new ArrayList<>();
        this.extractedText = extractedText;

        initTemplates();

        EventLog.getInstance()
                .logEvent(new Event("Successfully initialized new image conversion instance for '" + filePath
                        + "' with extracted text: '" + extractedText + "'."));
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Mat getImageMat() {
        return this.imageMat;
    }

    public List<Mat> getSubImages() {
        return this.subImages;
    }

    public double getThreshold() {
        return POLARIZE_THRESHOLD;
    }

    public List<CharacterTemplate> getTemplates() {
        return this.templates;
    }

    public String getExtractedText() {
        return this.extractedText;
    }

    // MODIFIES: this
    // EFFECTS: reads all template images in data/templates
    // and stores in character templates
    public void initTemplates() {
        File[] files = new File("data/templates").listFiles();
        for (File file : files) {
            templates.add(new CharacterTemplate(file.getPath()));
        }

        EventLog.getInstance().logEvent(new Event("Loaded character templates from 'data/templates'."));
    }

    // REQUIRES: this image conversion instance must have an existing
    // stored valid filePath
    // MODIFIES: this
    // EFFECTS: reads the image at filePath and stores it in imageMat
    public void readImage() {
        this.imageMat = Imgcodecs.imread(filePath);

        EventLog.getInstance().logEvent(new Event("Successfully read image file from '" + filePath + "'."));
    }

    // REQURES: path must be a valid file path to an image in data/images
    // MODIFIES: this
    // EFFECTS: stores path as filePath, reads the image at path,
    // and stores it in imageMat
    public void readImage(String path) {
        this.filePath = path;
        this.imageMat = Imgcodecs.imread(path);

        EventLog.getInstance().logEvent(new Event("Successfully read image file from '" + path + "'."));
    }

    // REQUIRES: imageMat must be a valid image containing lines
    // of Robotto font plain text
    // MODIFIES: this
    // EFFECTS: processes image and saves recognized text in extractedText
    public void processImage() {
        toGrayscale();
        polarizeImage(imageMat, POLARIZE_THRESHOLD);
        sliceImage();
        for (Mat image : subImages) {
            resizeImage(image, TEMPLATE_WIDTH, TEMPLATE_HEIGHT);
            this.extractedText += compareWithTemplates(image, templates);
        }

        EventLog.getInstance().logEvent(new Event("Processed image file and successfully extracted text."));
    }

    // REQUIRES: imageMat must contain a standard RGB image
    // MODIFIES: this
    // EFFECTS: converts image to grayscale
    public void toGrayscale() {
        Imgproc.cvtColor(imageMat, imageMat, Imgproc.COLOR_BGR2GRAY);
    }

    // EFFECTS: returns cropped image to the outermost colorRGB pixel
    public Mat crop(Mat image, double colorRGB) {
        int minY = image.rows();
        int minX = image.cols();
        int maxY = 0;
        int maxX = 0;

        for (int y = 0; y < image.rows(); y++) {
            for (int x = 0; x < image.cols(); x++) {
                if (image.get(y, x)[0] != colorRGB) {
                    if (y < minY) {
                        minY = y;
                    } else if (y > maxY) {
                        maxY = y;
                    }
                    if (x < minX) {
                        minX = x;
                    } else if (x > maxX) {
                        maxX = x;
                    }
                }
            }
        }

        return new Mat(image, new Rect(minX, minY, (maxX - minX) + 1, (maxY - minY) + 1));
    }

    // REQUIRES: image must contain an image in grayscale
    // MODIFIES: this
    // EFFECTS: convert all pixels to either black or white using numerical
    // threshold
    public void polarizeImage(Mat image, int threshold) {
        Imgproc.adaptiveThreshold(image, image, 255,
                Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                Imgproc.THRESH_BINARY,
                threshold, 5);
    }

    // REQUIRES: imageMat must contain a grayscale image
    // there must be at least 1 pixel of white margin between adjacent
    // text characters in the image
    // MODIFIES: this
    // EFFECTS: slices image into a list of sub-images containing
    // 1 text character each, in the order that they appear left-to-right
    public void sliceImage() {
        List<Mat> rows = new ArrayList<>();
        double whiteRGB = 255.0;

        List<Integer> rowsPos = sliceHorizontal(imageMat, whiteRGB);
        for (int i = 1; i < rowsPos.size() - 1; i += 2) {
            int row = rowsPos.get(i);
            int next = rowsPos.get(i + 1);
            Mat subImage = new Mat(imageMat, new Rect(0, row, imageMat.cols(), next - row));
            rows.add(subImage);
        }

        List<List<Integer>> colsPos = sliceVertical(rows, whiteRGB);
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < colsPos.get(i).size() - 1; j += 2) {
                int col = colsPos.get(i).get(j);
                int next = colsPos.get(i).get(j + 1);
                Mat subImage = new Mat(rows.get(i), new Rect(col, 0, (next - col), rows.get(i).rows()));
                subImage = crop(subImage, whiteRGB);
                subImages.add(subImage);
            }
        }
    }

    // REQUIRES: image is a grayscale image matrix
    // EFFECTS: slices image horizontally to return
    // list of row indices bounding sub images
    public List<Integer> sliceHorizontal(Mat image, double whiteRGB) {
        List<Integer> rowsPos = new ArrayList<>();
        boolean onWhiteRow = false;

        for (int row = 0; row < image.rows(); row++) {
            boolean allWhiteRow = true;
            for (int col = 0; col < image.cols(); col++) {
                if (image.get(row, col)[0] != whiteRGB) {
                    allWhiteRow = false;
                    break;
                }
            }
            if (!onWhiteRow && allWhiteRow) {
                onWhiteRow = true;
                rowsPos.add(row);
            } else if (onWhiteRow && !allWhiteRow) {
                onWhiteRow = false;
                rowsPos.add(row);
            }
        }
        return rowsPos;
    }

    // REQUIRES: rows contains lines of text sliced by sliceHorizontal
    // EFFECTS: slices image vertically to return
    // list of list of col indices bounding sub images for
    // each sub image row
    public List<List<Integer>> sliceVertical(List<Mat> rows, double whiteRGB) {
        List<List<Integer>> colsPos = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            colsPos.add(new ArrayList<>());
            Mat image = rows.get(i);
            boolean onWhiteCol = (image.get(0, 0)[0] == whiteRGB);
            for (int col = 0; col < image.cols(); col++) {
                boolean allWhiteCol = true;
                for (int row = 0; row < image.rows(); row++) {
                    if (image.get(row, col)[0] != whiteRGB) {
                        allWhiteCol = false;
                        break;
                    }
                }
                if (!onWhiteCol && allWhiteCol) {
                    onWhiteCol = true;
                    colsPos.get(i).add(col);
                } else if (onWhiteCol && !allWhiteCol) {
                    onWhiteCol = false;
                    colsPos.get(i).add(col);
                }
            }
        }
        return colsPos;
    }

    // REQUIRES: width > 0 && height > 0
    // MODIFIES: this, image
    // EFFECTS: returns resized image that matches given width and height
    public Mat resizeImage(Mat image, int width, int height) {
        Imgproc.resize(image, image, new Size(width, height));
        return image;
    }

    // REQUIRES: image must have the same dimensions as each template in templates,
    // given image matrix must contain exactly 1 recognizable English character,
    // and this character must match an existing template in templates
    // EFFECTS: compares a sliced image with all template character images and
    // assigns a similarity score out of 1 for the comparison; returns English
    // character with greatest similarity score
    public String compareWithTemplates(Mat image, List<CharacterTemplate> templates) {
        double maxScore = 0.0;
        String maxScoreChar = null;
        image.convertTo(image, CvType.CV_8U);
        for (CharacterTemplate template : templates) {
            Mat result = Mat.eye(1, 1, CvType.CV_8U);
            Imgproc.matchTemplate(image, template.getImageMat(), result, Imgproc.TM_CCOEFF_NORMED);
            double score = result.get(0, 0)[0];
            if (score > maxScore) {
                maxScore = score;
                maxScoreChar = template.getLabel();
            }
        }

        EventLog.getInstance()
                .logEvent(new Event("Character '" + maxScoreChar + "' extracted with confidence: " + maxScore + "."));

        return maxScoreChar;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("filePath", filePath);
        json.put("extractedText", extractedText);
        return json;
    }

}