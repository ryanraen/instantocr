package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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
        assertEquals(Imgcodecs.imread("data/templates/a.png"), template.getImageMat());
        assertEquals("a", template.getLabel());
    }
    
}
