package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class TestImageConversion {
    ImageConversion conversion;
    ImageConversion conversion2;
    ImageConversion conversion3;
    ImageConversion conversion4;
    ImageConversion conversion5;
    ImageConversion conversion6;
    File templatePath;
    List<CharacterTemplate> templates;

    @BeforeEach
    public void setup() {
        conversion = new ImageConversion();
        conversion2 = new ImageConversion("data/images/simple_sentence.png");
        conversion3 = new ImageConversion("data/templates/a.png");
        conversion4 = new ImageConversion("data/test/butterfly.png");
        conversion5 = new ImageConversion("data/test/unprocessed_a.png");
        conversion6 = new ImageConversion("data/test/word.png");
        
        templatePath = new File("data/test/templates");
        templates = new ArrayList<>();
    }

    @Test
    public void testVoidConstructor() {
        assertEquals("", conversion.getFilePath());
        assertNull(conversion.getImageMat());
        assertNull(conversion.getSubImages());
        assertEquals(255, (int) conversion.getThreshold());
        assertNull(conversion.getTemplates());
        assertEquals("", conversion.getExtractedText());
    }

    @Test
    public void testStringConstructor() {
        assertEquals("data/images/simple_sentence.png", conversion2.getFilePath());
        assertNull(conversion2.getImageMat());
        assertNull(conversion2.getSubImages());
        assertEquals(255, (int) conversion2.getThreshold());
        assertNull(conversion2.getTemplates());
        assertEquals("", conversion2.getExtractedText());
    }

    @Test
    public void testInitTemplates() {
        assertTrue(templates.isEmpty());
        assertEquals("data/test/templates", templatePath.getPath());

        for (File file : templatePath.listFiles()) {
            templates.add(new CharacterTemplate(file.getPath()));
        }

        assertEquals(26, templates.size());

        assertEquals("data/test/templates/a.png", templates.get(0).getFilePath());
        assertEquals(Imgcodecs.imread("data/test/templates/a.png"), templates.get(0).getImageMat());
        assertEquals('a', templates.get(0).getLabel());

        assertEquals("data/test/templates/z.png", templates.get(templates.size() - 1).getFilePath());
        assertEquals(Imgcodecs.imread("data/test/templates/z.png"), templates.get(templates.size() - 1).getImageMat());
        assertEquals('z', templates.get(templates.size() - 1).getLabel());
    }

    @Test
    public void testReadImageOnce() {
        assertNull(conversion.getImageMat());
        conversion.readImage("data/images/simple_sentence.png");
        assertEquals(conversion.getFilePath(), "data/images/simple_sentence.png");
        assertEquals(Imgcodecs.imread("data/images/simple_sentence.png"), conversion.getImageMat());
    }

    @Test
    public void testReadImageMultipleTimes() {
        assertNull(conversion.getImageMat());
        conversion.readImage("data/images/simple_sentence.png");
        assertEquals(conversion.getFilePath(), "data/images/simple_sentence.png");
        assertEquals(Imgcodecs.imread("data/images/simple_sentence.png"), conversion.getImageMat());
        conversion.readImage("data/templates/a.png");
        assertEquals(conversion.getFilePath(), "data/templates/a.png");
        assertEquals(Imgcodecs.imread("data/templates/a.png"), conversion.getImageMat());
    }

    @Test
    public void testProcessImageOneChar() {
        assertNull(conversion3.getImageMat());
        conversion3.readImage();
        assertEquals(Imgcodecs.imread("data/templates/a.png"), conversion3.getImageMat());
        conversion3.processImage();
        assertEquals("a", conversion3.getExtractedText());
    }

    @Test
    public void testProcessImageSentence() {
        assertNull(conversion2.getImageMat());
        conversion2.readImage();
        assertEquals(Imgcodecs.imread("data/images/simple_sentence.png"), conversion2.getImageMat());
        conversion2.processImage();
        assertEquals("This is a simple sentence.", conversion2.getExtractedText());
    }

    @Test
    public void testToGrayscaleLetter() {
        assertNull(conversion5.getImageMat());
        conversion5.readImage();
        assertEquals(Imgcodecs.imread("data/test/unprocessed/unprocessed_a.png"), conversion5.getImageMat());
        conversion5.toGrayscale();
        assertEquals(Imgcodecs.imread("data/test/grayscale/grayscale_a.png"), conversion5.getImageMat());
    }

    @Test
    public void testToGrayscaleSentence() {
        assertNull(conversion2.getImageMat());
        conversion2.readImage();
        assertEquals(Imgcodecs.imread("data/images/simple_sentence.png"), conversion2.getImageMat());
        conversion2.toGrayscale();
        assertEquals(Imgcodecs.imread("data/test/grayscale/grayscale_simple_sentence.png"), conversion2.getImageMat());
    }

    @Test
    public void testToGrayscalePicture() {
        assertNull(conversion4.getImageMat());
        conversion4.readImage();
        assertEquals(Imgcodecs.imread("data/test/unprocessed/butterfly.png"), conversion4.getImageMat());
        conversion4.toGrayscale();
        assertEquals(Imgcodecs.imread("data/test/grayscale/grayscale_butterfly.png"), conversion4.getImageMat());
    }

    @Test
    public void testPolarizeImageSentence() {
        assertNull(conversion2.getImageMat());
        conversion2.readImage();
        assertEquals(Imgcodecs.imread("data/images/simple_sentence.png"), conversion2.getImageMat());
        conversion2.toGrayscale();
        assertEquals(Imgcodecs.imread("data/test/grayscale/grayscale_simple_sentence.png"), conversion2.getImageMat());
        conversion2.polarizeImage();
        assertEquals(Imgcodecs.imread("data/test/polarized/polarized_simple_sentence.png"), conversion2.getImageMat());
    }

    @Test
    public void testSliceImageWordThis() {
        assertNull(conversion6.getImageMat());
        conversion6.readImage();
        assertEquals(Imgcodecs.imread("data/test/unprocessed/word.png"), conversion6.getImageMat());
        conversion6.toGrayscale();
        assertEquals(Imgcodecs.imread("data/test/grayscale/grayscale_word.png"), conversion6.getImageMat());
        conversion6.polarizeImage();
        assertEquals(Imgcodecs.imread("data/test/polarized/polarized_word.png"), conversion6.getImageMat());
        assertTrue(conversion6.getSubImages().isEmpty());
        conversion6.sliceImage();
        assertEquals(4, conversion6.getSubImages().size());
        assertEquals(Imgcodecs.imread("data/test/sliced_word/T.png"), conversion6.getSubImages().get(0));
        assertEquals(Imgcodecs.imread("data/test/sliced_word/h.png"), conversion6.getSubImages().get(1));
        assertEquals(Imgcodecs.imread("data/test/sliced_word/i.png"), conversion6.getSubImages().get(2));
        assertEquals(Imgcodecs.imread("data/test/sliced_word/s.png"), conversion6.getSubImages().get(3));
    }

    @Test
    public void testResizeImageLetterAlreadyRightSize() {
        assertNull(conversion6.getImageMat());
        conversion6.readImage();
        assertEquals(Imgcodecs.imread("data/test/unprocessed/word.png"), conversion6.getImageMat());
        conversion6.toGrayscale();
        assertEquals(Imgcodecs.imread("data/test/grayscale/grayscale_word.png"), conversion6.getImageMat());
        conversion6.polarizeImage();
        assertEquals(Imgcodecs.imread("data/test/polarized/polarized_word.png"), conversion6.getImageMat());
        assertTrue(conversion6.getSubImages().isEmpty());
        conversion6.sliceImage();
        assertEquals(4, conversion6.getSubImages().size());
        assertEquals(Imgcodecs.imread("data/test/sliced_word/T.png"), conversion6.getSubImages().get(0));
        assertEquals(Imgcodecs.imread("data/test/sliced_word/h.png"), conversion6.getSubImages().get(1));
        assertEquals(Imgcodecs.imread("data/test/sliced_word/i.png"), conversion6.getSubImages().get(2));
        assertEquals(Imgcodecs.imread("data/test/sliced_word/s.png"), conversion6.getSubImages().get(3));

        Mat resized = conversion6.resizeImage(conversion6.getSubImages().get(0),
                16, 22);

        assertEquals(16, resized.rows());
        assertEquals(22, resized.cols());
        assertEquals(conversion6.getSubImages().get(0), resized);

    }

    @Test
    public void testResizeImageLetterSizeUp() {
        assertNull(conversion6.getImageMat());
        conversion6.readImage();
        assertEquals(Imgcodecs.imread("data/test/unprocessed/word.png"), conversion6.getImageMat());
        conversion6.toGrayscale();
        assertEquals(Imgcodecs.imread("data/test/grayscale/grayscale_word.png"), conversion6.getImageMat());
        conversion6.polarizeImage();
        assertEquals(Imgcodecs.imread("data/test/polarized/polarized_word.png"), conversion6.getImageMat());
        assertTrue(conversion6.getSubImages().isEmpty());
        conversion6.sliceImage();
        assertEquals(4, conversion6.getSubImages().size());
        assertEquals(Imgcodecs.imread("data/test/sliced_word/T.png"), conversion6.getSubImages().get(0));
        assertEquals(Imgcodecs.imread("data/test/sliced_word/h.png"), conversion6.getSubImages().get(1));
        assertEquals(Imgcodecs.imread("data/test/sliced_word/i.png"), conversion6.getSubImages().get(2));
        assertEquals(Imgcodecs.imread("data/test/sliced_word/s.png"), conversion6.getSubImages().get(3));

        Mat resized = conversion6.resizeImage(conversion6.getSubImages().get(3),
                16, 22);

        assertEquals(16, resized.rows());
        assertEquals(22, resized.cols());
        assertEquals(Imgcodecs.imread("data/resized/resized_s.png"), resized);
    }

    @Test
    public void testResizeImageLetterSizeDown() {
        assertNull(conversion6.getImageMat());
        conversion6.readImage();
        assertEquals(Imgcodecs.imread("data/test/unprocessed/word.png"), conversion6.getImageMat());
        conversion6.toGrayscale();
        assertEquals(Imgcodecs.imread("data/test/grayscale/grayscale_word.png"), conversion6.getImageMat());
        conversion6.polarizeImage();
        assertEquals(Imgcodecs.imread("data/test/polarized/polarized_word.png"), conversion6.getImageMat());
        assertTrue(conversion6.getSubImages().isEmpty());
        conversion6.sliceImage();
        assertEquals(4, conversion6.getSubImages().size());
        assertEquals(Imgcodecs.imread("data/test/sliced_word/T.png"), conversion6.getSubImages().get(0));
        assertEquals(Imgcodecs.imread("data/test/sliced_word/h.png"), conversion6.getSubImages().get(1));
        assertEquals(Imgcodecs.imread("data/test/sliced_word/i.png"), conversion6.getSubImages().get(2));
        assertEquals(Imgcodecs.imread("data/test/sliced_word/s.png"), conversion6.getSubImages().get(3));

        Mat resized = conversion6.resizeImage(conversion6.getSubImages().get(0),
                5, 8);

        assertEquals(5, resized.rows());
        assertEquals(8, resized.cols());
        assertEquals(Imgcodecs.imread("data/resized/resized_T.png"), resized);
    }

    @Test
    public void testCompareWithTemplates() {
        assertTrue(templates.isEmpty());
        assertEquals("data/test/templates", templatePath.getPath());

        for (File file : templatePath.listFiles()) {
            templates.add(new CharacterTemplate(file.getPath()));
        }

        assertEquals(26, templates.size());

        assertEquals("data/test/templates/a.png", templates.get(0).getFilePath());
        assertEquals(Imgcodecs.imread("data/test/templates/a.png"), templates.get(0).getImageMat());
        assertEquals('a', templates.get(0).getLabel());

        assertEquals("data/test/templates/z.png", templates.get(templates.size() - 1).getFilePath());
        assertEquals(Imgcodecs.imread("data/test/templates/z.png"), templates.get(templates.size() - 1).getImageMat());
        assertEquals('z', templates.get(templates.size() - 1).getLabel());

        assertNull(conversion6.getImageMat());
        conversion6.readImage();
        assertEquals(Imgcodecs.imread("data/test/unprocessed/word.png"), conversion6.getImageMat());
        conversion6.toGrayscale();
        assertEquals(Imgcodecs.imread("data/test/grayscale/grayscale_word.png"), conversion6.getImageMat());
        conversion6.polarizeImage();
        assertEquals(Imgcodecs.imread("data/test/polarized/polarized_word.png"), conversion6.getImageMat());
        assertTrue(conversion6.getSubImages().isEmpty());
        conversion6.sliceImage();
        assertEquals(4, conversion6.getSubImages().size());
        assertEquals(Imgcodecs.imread("data/test/sliced_word/T.png"), conversion6.getSubImages().get(0));
        assertEquals(Imgcodecs.imread("data/test/sliced_word/h.png"), conversion6.getSubImages().get(1));
        assertEquals(Imgcodecs.imread("data/test/sliced_word/i.png"), conversion6.getSubImages().get(2));
        assertEquals(Imgcodecs.imread("data/test/sliced_word/s.png"), conversion6.getSubImages().get(3));

        assertEquals('s', conversion6.compareWithTemplates(conversion6.getSubImages().get(3), templates));
        assertEquals('h', conversion6.compareWithTemplates(conversion6.getSubImages().get(1), templates));
    }

}
