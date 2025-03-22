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
class TestJsonWriter extends TestJson {

    @Test
    void testWriterInvalidFile() {
        try {
            new ConversionHistory();
            JsonWriter writer = new JsonWriter("./data/test/persistence/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyHistory() {
        try {
            ConversionHistory history = new ConversionHistory();
            JsonWriter writer = new JsonWriter("./data/test/persistence/testWriterEmptyHistory.json");
            writer.open();
            writer.write(history);
            writer.close();

            JsonReader reader = new JsonReader("./data/test/persistence/testWriterEmptyHistory.json");
            history = reader.read();
            assertTrue(history.getConversions().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralHistory() {
        try {
            ConversionHistory history = new ConversionHistory();
            history.addConversion(new ImageConversion("data/test/persistence/first_image.png"));
            history.addConversion(new ImageConversion("data/test/persistence/second_image.png"));
            history.getConversions().get(0).processImage();
            history.getConversions().get(1).processImage();
            JsonWriter writer = new JsonWriter("./data/test/persistence/testWriterGeneralHistory.json");
            writer.open();
            writer.write(history);
            writer.close();

            JsonReader reader = new JsonReader("./data/test/persistence/testWriterGeneralHistory.json");
            history = reader.read();
            List<ImageConversion> conversions = history.getConversions();
            assertEquals(2, conversions.size());
            checkConversion("data/test/persistence/first_image.png", "hello", conversions.get(0));
            checkConversion("data/test/persistence/second_image.png", "banana", conversions.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}