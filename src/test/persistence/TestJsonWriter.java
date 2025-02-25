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
            ConversionHistory history = new ConversionHistory();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
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
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyHistory.json");
            writer.open();
            writer.write(history);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyHistory.json");
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
            history.addConversion(new ImageConversion("data/images/greetings.png"));
            history.addConversion(new ImageConversion("data/images/banana.png"));
            history.getConversions().get(0).processImage();
            history.getConversions().get(1).processImage();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralHistory.json");
            writer.open();
            writer.write(history);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralHistory.json");
            history = reader.read();
            List<ImageConversion> conversions = history.getConversions();
            assertEquals(2, conversions.size());
            checkConversion("data/images/greetings.png", "hello", conversions.get(0));
            checkConversion("data/images/banana.png", "banana", conversions.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}