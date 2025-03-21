package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.ConversionHistory;
import model.ImageConversion;

// Adapted from the CPSC 210 JsonSerializationDemo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
class TestJsonReader extends TestJson {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/test/persistence/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyHistory() {
        JsonReader reader = new JsonReader("./data/test/persistence/testReaderEmptyHistory.json");
        try {
            ConversionHistory history = reader.read();
            assertTrue(history.getConversions().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralHistory() {
        JsonReader reader = new JsonReader("./data/test/persistence/testReaderGeneralHistory.json");
        try {
            ConversionHistory history = reader.read();
            List<ImageConversion> conversions = history.getConversions();
            assertEquals(2, conversions.size());
            checkConversion("data/test/persistence/first_image.png", "hello", conversions.get(0));
            checkConversion("data/test/persistence/second_image.png", "banana", conversions.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}