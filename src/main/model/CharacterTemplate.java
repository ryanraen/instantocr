package model;

import java.io.File;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

// Represents a character template, having a file path, 
// a matrix that holds all pixels in the template image, 
// and a String holding the English alphabet identity of the template
public class CharacterTemplate {
    private String filePath;
    private Mat imageMat;
    private String label;

    // REQUIRES: path be a relative path pointing to a valid template
    // image in the project data
    // EFFECTS: initializes the character template and reads
    // the image file at path, storing its file name as
    // the label (excluding file extension)
    public CharacterTemplate(String path) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        this.filePath = path;
        this.imageMat = Imgcodecs.imread(path);
        Imgproc.cvtColor(imageMat, imageMat, Imgproc.COLOR_BGR2GRAY);
        Imgproc.resize(imageMat, imageMat, new Size(ImageConversion.TEMPLATE_WIDTH, ImageConversion.TEMPLATE_HEIGHT));
        this.label = new File(path).getName().replaceFirst("\\..+", "");
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Mat getImageMat() {
        return this.imageMat;
    }

    public String getLabel() {
        return this.label;
    }

}
