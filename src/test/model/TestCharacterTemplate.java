package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class TestCharacterTemplate {
    CharacterTemplate template;

    @BeforeEach
    public void setup() {
        template = new CharacterTemplate("data/templates/a.png");
    }

    @Test
    public void testConstructor() {
        assertEquals("data/templates/a.png", template.getFilePath());
        Mat expected = Imgcodecs.imread("data\\test\\templates\\a.png");
        for (int i = 0; i < expected.rows(); i++) {
            for (int j = 0; j < expected.cols(); j++) {
                assertTrue(expected.get(i, j)[0] == template.getImageMat().get(i, j)[0]);
            }
        }
        assertEquals("a", template.getLabel());
    }

}
