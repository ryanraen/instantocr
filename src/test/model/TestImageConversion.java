package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class TestImageConversion {
    ImageConversion conversion;
    ImageConversion conversion2;
    ImageConversion conversion3;
    ImageConversion conversion4;
    ImageConversion conversion5;
    ImageConversion conversion6;
    ImageConversion conversion7;
    File templatePath;
    List<CharacterTemplate> templates;

    @BeforeEach
    public void setup() {
        conversion = new ImageConversion();
        conversion2 = new ImageConversion("data\\images\\greetings.png");
        conversion4 = new ImageConversion("data\\test\\butterfly.png");
        conversion5 = new ImageConversion("data\\test\\unprocessed\\unprocessed_a.png");
        conversion6 = new ImageConversion("data\\test\\unprocessed\\word.png");
        conversion7 = new ImageConversion();

        templatePath = new File("data\\test\\templates");
        templates = new ArrayList<>();
    }

    @Test
    public void testVoidConstructor() {
        assertEquals("", conversion.getFilePath());
        assertNull(conversion.getImageMat());
        assertTrue(conversion.getSubImages().isEmpty());
        assertEquals(101, (int) conversion.getThreshold());
        assertFalse(conversion.getTemplates().isEmpty());
        assertEquals("", conversion.getExtractedText());
    }

    @Test
    public void testStringConstructor() {
        assertEquals("data\\images\\greetings.png", conversion2.getFilePath());
        assertEquals(Imgcodecs.imread("data\\images\\greetings.png").dump(), conversion2.getImageMat().dump());
        assertTrue(conversion.getSubImages().isEmpty());
        assertEquals(101, (int) conversion2.getThreshold());
        assertFalse(conversion.getTemplates().isEmpty());
        assertEquals("", conversion2.getExtractedText());
    }

    @Test
    public void testInitTemplates() {
        assertTrue(templates.isEmpty());
        assertEquals("data\\test\\templates", templatePath.getPath());

        for (File file : templatePath.listFiles()) {
            templates.add(new CharacterTemplate(file.getPath()));
        }

        assertEquals(26, templates.size());

        assertEquals("data\\test\\templates\\z.png", templates.get(templates.size() - 1).getFilePath());
        Mat expectedMat = Imgcodecs.imread("data\\test\\templates\\z.png");
        Imgproc.cvtColor(expectedMat, expectedMat, Imgproc.COLOR_BGR2GRAY);
        Imgproc.resize(expectedMat, expectedMat,
                new Size(ImageConversion.TEMPLATE_WIDTH, ImageConversion.TEMPLATE_HEIGHT));
        assertEquals(expectedMat.dump(), templates.get(templates.size() - 1).getImageMat().dump());
        assertEquals("z", templates.get(templates.size() - 1).getLabel());
    }

    @Test
    public void testReadImageOnce() {
        assertNull(conversion.getImageMat());
        conversion.readImage("data\\images\\simple_sentence.png");
        assertEquals(conversion.getFilePath(), "data\\images\\simple_sentence.png");
        assertEquals(Imgcodecs.imread("data\\images\\simple_sentence.png").dump(), conversion.getImageMat().dump());
    }

    @Test
    public void testReadImageMultipleTimes() {
        assertNull(conversion.getImageMat());
        conversion.readImage("data\\images\\simple_sentence.png");
        assertEquals(conversion.getFilePath(), "data\\images\\simple_sentence.png");
        assertEquals(Imgcodecs.imread("data\\images\\simple_sentence.png").dump(), conversion.getImageMat().dump());
        conversion.readImage("data\\templates\\a.png");
        assertEquals(conversion.getFilePath(), "data\\templates\\a.png");
        assertEquals(Imgcodecs.imread("data\\templates\\a.png").dump(), conversion.getImageMat().dump());
    }

    @Test
    public void testProcessImageOneChar() {
        conversion5.readImage();
        assertEquals(Imgcodecs.imread("data\\test\\unprocessed\\unprocessed_a.png").dump(),
                conversion5.getImageMat().dump());
        conversion5.processImage();
        assertEquals("a", conversion5.getExtractedText());
    }

    @Test
    public void testProcessImageWord() {
        conversion2.readImage();
        assertEquals(Imgcodecs.imread("data\\images\\greetings.png").dump(), conversion2.getImageMat().dump());
        conversion2.processImage();
        assertEquals("hello", conversion2.getExtractedText());
    }

    @Test
    public void testToGrayscaleWord() {
        conversion2.readImage();
        assertEquals(Imgcodecs.imread("data\\images\\greetings.png").dump(), conversion2.getImageMat().dump());
        conversion2.toGrayscale();
        Mat expected = Imgcodecs.imread("data\\test\\grayscale\\grayscale_greetings.png");
        for (int i = 0; i < expected.rows(); i++) {
            for (int j = 0; j < expected.cols(); j++) {
                assertEquals(expected.get(i, j)[0], conversion2.getImageMat().get(i, j)[0]);
            }
        }
    }

    @Test
    public void testPolarizeImageSentence() {
        conversion2.readImage();
        assertEquals(Imgcodecs.imread("data\\images\\greetings.png").dump(), conversion2.getImageMat().dump());
        conversion2.toGrayscale();
        Mat expected = Imgcodecs.imread("data\\test\\grayscale\\grayscale_greetings.png");
        for (int i = 0; i < expected.rows(); i++) {
            for (int j = 0; j < expected.cols(); j++) {
                assertEquals(expected.get(i, j)[0], conversion2.getImageMat().get(i, j)[0]);
            }
        }
        conversion2.polarizeImage(conversion2.getImageMat(), ImageConversion.POLARIZE_THRESHOLD);
        Mat expectedPolarized = Imgcodecs.imread("data\\test\\polarized\\polarized_greetings.png");
        for (int i = 0; i < expectedPolarized.rows(); i++) {
            for (int j = 0; j < expectedPolarized.cols(); j++) {
                assertEquals(expectedPolarized.get(i, j)[0], conversion2.getImageMat().get(i, j)[0]);
            }
        }
    }

    @Test
    public void testSliceImageWordThis() {
        conversion6.readImage();
        assertEquals(Imgcodecs.imread("data\\test\\unprocessed\\word.png").dump(), conversion6.getImageMat().dump());
        conversion6.toGrayscale();
        conversion6.polarizeImage(conversion6.getImageMat(), ImageConversion.POLARIZE_THRESHOLD);
        assertTrue(conversion6.getSubImages().isEmpty());
        conversion6.sliceImage();
        assertEquals(4, conversion6.getSubImages().size());
        Mat expected = Imgcodecs.imread("data\\test\\sliced_word\\T.png");
        for (int i = 0; i < expected.rows(); i++) {
            for (int j = 0; j < expected.cols(); j++) {
                assertEquals(expected.get(i, j)[0], conversion6.getSubImages().get(0).get(i, j)[0]);
            }
        }
    }

    @Test
    public void testSliceHorizontal() {
        assertNull(conversion7.getImageMat());
        conversion7.readImage("data\\test\\unprocessed\\multiple_lines.png");
        conversion7.toGrayscale();
        List<Integer> expectedRows = new ArrayList<>();
        expectedRows.add(0);
        expectedRows.add(81);
        expectedRows.add(109);
        expectedRows.add(121);
        expectedRows.add(143);
        expectedRows.add(162);
        expectedRows.add(184);
        expectedRows.add(243);
        expectedRows.add(265);
        expectedRows.add(283);
        expectedRows.add(305);
        expectedRows.add(405);
        expectedRows.add(427);
        expectedRows.add(448);
        expectedRows.add(467);
        assertEquals(expectedRows, conversion7.sliceHorizontal(conversion7.getImageMat(), 255.0));
    }

    @Test
    public void testSliceVertical() {
        assertNull(conversion7.getImageMat());
        conversion7.readImage("data\\test\\unprocessed\\horizontally_sliced.png");
        conversion7.toGrayscale();
        List<Mat> matList = new ArrayList<>();
        matList.add(conversion7.getImageMat());
        List<Integer> colsPos = conversion7.sliceVertical(matList, 255.0).get(0);
        assertEquals(79, (int) colsPos.get(0));
        assertEquals(111, (int) colsPos.get(3));
        assertEquals(144, (int) colsPos.get(8));
        assertEquals(290, (int) colsPos.get(25));
        assertEquals(423, (int) colsPos.get(41));
        assertFalse(matList.get(0) == null);
        assertTrue(255.0 == matList.get(0).get(0, 0)[0]);
        conversion7.readImage("data\\images\\greetings.png");
        conversion7.toGrayscale();
        matList = new ArrayList<>();
        matList.add(conversion7.getImageMat());
        colsPos = conversion7.sliceVertical(matList, 255.0).get(0);
        assertEquals(15, (int) colsPos.get(0));
        assertEquals(46, (int) colsPos.get(3));
        assertEquals(62, (int) colsPos.get(8));
    }

    @Test
    public void testResizeImageLetterAlreadyRightSize() {
        conversion6.readImage();
        conversion6.toGrayscale();
        conversion6.polarizeImage(conversion6.getImageMat(), ImageConversion.POLARIZE_THRESHOLD);
        assertTrue(conversion6.getSubImages().isEmpty());
        conversion6.sliceImage();
        assertEquals(4, conversion6.getSubImages().size());
        Mat expected = Imgcodecs.imread("data\\test\\sliced_word\\T.png");
        for (int i = 0; i < expected.rows(); i++) {
            for (int j = 0; j < expected.cols(); j++) {
                assertEquals(expected.get(i, j)[0], conversion6.getSubImages().get(0).get(i, j)[0]);
            }
        }
        assertEquals(22, conversion6.getSubImages().get(0).rows());
        assertEquals(17, conversion6.getSubImages().get(0).cols());
        Mat resized = conversion6.resizeImage(conversion6.getSubImages().get(0),
                17, 22);

        assertEquals(22, resized.rows());
        assertEquals(17, resized.cols());
        assertEquals(conversion6.getSubImages().get(0), resized);

    }

    @Test
    public void testResizeImageLetterSizeUp() {
        conversion6.readImage();
        conversion6.toGrayscale();
        conversion6.polarizeImage(conversion6.getImageMat(), ImageConversion.POLARIZE_THRESHOLD);
        assertTrue(conversion6.getSubImages().isEmpty());
        conversion6.sliceImage();
        assertEquals(4, conversion6.getSubImages().size());
        assertEquals(17, conversion6.getSubImages().get(3).rows());
        assertEquals(13, conversion6.getSubImages().get(3).cols());
        Mat resized = conversion6.resizeImage(conversion6.getSubImages().get(3),
                16, 22);
        assertEquals(22, resized.rows());
        assertEquals(16, resized.cols());
    }

    @Test
    public void testResizeImageLetterSizeDown() {
        conversion6.readImage();
        conversion6.toGrayscale();
        conversion6.polarizeImage(conversion6.getImageMat(), ImageConversion.POLARIZE_THRESHOLD);
        assertTrue(conversion6.getSubImages().isEmpty());
        conversion6.sliceImage();
        assertEquals(4, conversion6.getSubImages().size());
        assertEquals(22, conversion6.getSubImages().get(0).rows());
        assertEquals(17, conversion6.getSubImages().get(0).cols());
        Mat resized = conversion6.resizeImage(conversion6.getSubImages().get(3),
                5, 8);
        assertEquals(8, resized.rows());
        assertEquals(5, resized.cols());
    }

    @Test
    public void testCompareWithTemplates() {
        assertTrue(templates.isEmpty());
        assertEquals("data\\test\\templates", templatePath.getPath());

        System.out.println(templatePath.listFiles());
        for (File file : templatePath.listFiles()) {
            templates.add(new CharacterTemplate(file.getPath()));
        }
        assertEquals("a", templates.get(0).getLabel());
        assertEquals("z", templates.get(templates.size() - 1).getLabel());
        conversion2.readImage();
        conversion2.toGrayscale();
        conversion2.sliceImage();

        for (int i = 0; i < conversion2.getSubImages().size(); i++) {
            Mat image = conversion2.getSubImages().get(i);
            conversion2.resizeImage(image, ImageConversion.TEMPLATE_WIDTH,
                    ImageConversion.TEMPLATE_HEIGHT);
            conversion2.polarizeImage(image, ImageConversion.POLARIZE_THRESHOLD);
        }

        assertEquals(0, conversion2.getImageMat().type(), conversion2.getTemplates().get(0).getImageMat().type());
        assertEquals(conversion2.getTemplates().get(0).getImageMat().dims(), conversion2.getSubImages().get(0).dims());
        assertEquals("h",
                conversion2.compareWithTemplates(conversion2.getSubImages().get(0), conversion2.getTemplates()));
        assertEquals("e",
                conversion2.compareWithTemplates(conversion2.getSubImages().get(1), conversion2.getTemplates()));
    }

}
