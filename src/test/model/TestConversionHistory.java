package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestConversionHistory {
    ConversionHistory history;
    ImageConversion conversion1;
    ImageConversion conversion2;

    @BeforeEach
    public void setup() {
        history = new ConversionHistory();
        conversion1 = new ImageConversion("data/images/simple_sentence.png");
        conversion2 = new ImageConversion("data/templates/a.png");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, history.getConversions().size());
    }

    @Test
    public void testAddOneConversion() {
        assertEquals(0, history.getConversions().size());
        history.addConversion(conversion1);
        assertEquals(1, history.getConversions().size());
        assertEquals(conversion1, history.getByIndex(1));
    }

    @Test
    public void testAddMultipleConversions() {
        assertEquals(0, history.getConversions().size());
        history.addConversion(conversion1);
        assertEquals(1, history.getConversions().size());
        assertEquals(conversion1, history.getByIndex(1));
        history.addConversion(conversion2);
        assertEquals(2, history.getConversions().size());
        assertEquals(conversion2, history.getByIndex(1));
        assertEquals(conversion1, history.getByIndex(2));
    }

    @Test
    public void testGetByIndexOneElement() {
        assertEquals(0, history.getConversions().size());
        history.addConversion(conversion1);
        assertEquals(conversion1, history.getByIndex(1));
    } 

    @Test
    public void testGetByIndexMultipleElements() {
        assertEquals(0, history.getConversions().size());
        history.addConversion(conversion1);
        history.addConversion(conversion2);
        assertEquals(conversion2, history.getByIndex(1));
        assertEquals(conversion1, history.getByIndex(2));
    } 

    @Test
    public void testGetByFilePath() {
        assertEquals(0, history.getConversions().size());
        history.addConversion(conversion2);
        assertEquals(conversion2, history.getByFilePath("data/templates/a.png"));
    }

    @Test
    public void testGetByFilePathDeep() {
        assertEquals(0, history.getConversions().size());
        history.addConversion(conversion1);
        history.addConversion(conversion2);
        assertEquals(conversion2, history.getByFilePath("data/templates/a.png"));
    }

    @Test
    public void testGetByFilePathNotFound() {
        assertEquals(0, history.getConversions().size());
        history.addConversion(conversion1);
        history.addConversion(conversion2);
        assertNull(history.getByFilePath("data/templates/z.png"));
    }

    @Test
    public void testClearEmptyOnce() {
        assertEquals(0, history.getConversions().size());
        history.clear();
        assertEquals(0, history.getConversions().size());
    }

    @Test
    public void testClearNotEmptyOnce() {
        assertEquals(0, history.getConversions().size());
        history.addConversion(conversion2);
        assertEquals(1, history.getConversions().size());
        history.clear();
        assertEquals(0, history.getConversions().size());
    }

    @Test
    public void testClearNotEmptyMultipleTimes() {
        assertEquals(0, history.getConversions().size());
        history.addConversion(conversion2);
        assertEquals(1, history.getConversions().size());
        history.clear();
        assertEquals(0, history.getConversions().size());
        history.addConversion(conversion1);
        history.addConversion(conversion2);
        assertEquals(2, history.getConversions().size());
        history.clear();
        assertEquals(0, history.getConversions().size());
    }

    @Test
    public void testDeleteByFilePath() {
        assertEquals(0, history.getConversions().size());
        history.addConversion(conversion2);
        assertEquals(1, history.getConversions().size());
        assertTrue(history.deleteByFilePath("data/templates/a.png"));
        assertEquals(0, history.getConversions().size());
    }

    @Test
    public void testDeleteByFilePathDeep() {
        assertEquals(0, history.getConversions().size());
        history.addConversion(conversion2);
        history.addConversion(conversion1);
        assertEquals(2, history.getConversions().size());
        assertTrue(history.deleteByFilePath("data/images/simple_sentence.png"));
        assertEquals(1, history.getConversions().size());
        assertEquals(conversion2, history.getByIndex(1));
    }
    
    @Test
    public void testDeleteByFilePathNotFound() {
        assertEquals(0, history.getConversions().size());
        history.addConversion(conversion2);
        history.addConversion(conversion1);
        assertEquals(2, history.getConversions().size());
        assertFalse(history.deleteByFilePath("data/images/simple_sentence.png"));
        assertEquals(2, history.getConversions().size());
        assertEquals(conversion1, history.getByIndex(1));
        assertEquals(conversion2, history.getByIndex(2));
    }
}
