package model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Event class
 * Adapted from
 * https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/blob/main/src/test/ca/ubc/cpsc210/alarm/test/EventTest.java
 */
public class EventTest {
    private Event event;
    private Date date;

    // NOTE: these tests might fail if time at which line (2) below is executed
    // is different from time that line (1) is executed. Lines (1) and (2) must
    // run in same millisecond for this test to make sense and pass.

    @BeforeEach
    public void runBefore() {
        event = new Event("Sensor open at door"); // (1)
        date = Calendar.getInstance().getTime(); // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Sensor open at door", event.getDescription());
        assertEquals(date, event.getDate());
    }

    @SuppressWarnings("unlikely-arg-type")
    @Test
    public void testEqualsHashCodeOverride() {
        Object other = null;
        assertNull(other);
        assertFalse(event.equals(other));

        assertFalse(event.equals(date)); // SuppressWarnings used here to test class equivalence

        Event firstEvent = new Event("same event");
        Event otherEvent = new Event("same event");
        assertTrue(firstEvent.equals(otherEvent) && otherEvent.equals(firstEvent));
        assertEquals(firstEvent.hashCode(), otherEvent.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(date.toString() + "\n" + "Sensor open at door", event.toString());
    }
}
