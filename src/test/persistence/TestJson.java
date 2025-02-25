package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.ImageConversion;

// Adapted from the CPSC 210 JsonSerializationDemo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class TestJson {
    protected void checkConversion(String filePath, String extractedText, ImageConversion conversion) {
        assertEquals(filePath, conversion.getFilePath());
        assertEquals(extractedText, conversion.getExtractedText());
    }
}