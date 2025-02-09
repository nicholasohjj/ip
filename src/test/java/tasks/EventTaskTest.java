package tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import exceptions.InvalidFormatException;
import exceptions.NiniException;



class EventTaskTest {

    @Test
    void testConstructorWithValidInput() {
        try {
            EventTask task = new EventTask("Conference", "25/12/2025 0900", "25/12/2025 1700");
            assertEquals("Conference", task.getDescription());
            assertEquals(LocalDateTime.of(2025, 12, 25, 9, 0), task.getStartDateTime());
            assertEquals(LocalDateTime.of(2025, 12, 25, 17, 0), task.getEndDateTime());
            assertFalse(task.isDone());
        } catch (NiniException e) {
            fail("Exception should not have been thrown for valid input.");
        }
    }

    @Test
    void testConstructorWithInvalidFormat() {
        assertThrows(InvalidFormatException.class, () -> {
            new EventTask("Conference", "25-12-2025 0900", "25/12/2025 1700");
        });
        assertThrows(InvalidFormatException.class, () -> {
            new EventTask("Conference", "25/12/2025 0900", "invalid-date");
        });
    }

    @Test
    void testConstructorWithValidInputAndIsDone() {
        try {
            EventTask task = new EventTask("Conference", "25/12/2025 0900", "25/12/2025 1700", true);
            assertEquals("Conference", task.getDescription());
            assertEquals(LocalDateTime.of(2025, 12, 25, 9, 0), task.getStartDateTime());
            assertEquals(LocalDateTime.of(2025, 12, 25, 17, 0), task.getEndDateTime());
            assertTrue(task.isDone());
        } catch (NiniException e) {
            fail("Exception should not have been thrown for valid input.");
        }
    }

    @Test
    void testSerialize() {
        try {
            EventTask task = new EventTask("Conference", "25/12/2025 0900", "25/12/2025 1700", false);
            assertEquals("E|0|Conference|25/12/2025 0900|25/12/2025 1700", task.serialize());
        } catch (NiniException e) {
            fail("Exception should not have been thrown for valid input.");
        }
    }

    @Test
    void testToString() {
        try {
            EventTask task = new EventTask("Conference", "25/12/2025 0900", "25/12/2025 1700", false);
            assertEquals("[E][ ] Conference (from: Dec 25 2025, 9:00AM to: Dec 25 2025, 5:00PM)", task.toString());
        } catch (NiniException e) {
            fail("Exception should not have been thrown for valid input.");
        }
    }

    @Test
    void testInvalidDateOrder() {
        assertThrows(InvalidFormatException.class, () -> {
            new EventTask("Conference", "25/12/2025 1700", "25/12/2025 0900");
        });
    }

    @Test
    void testEdgeCaseDates() {
        try {
            EventTask task = new EventTask("Overnight Event", "31/12/2025 2300", "01/01/2026 0200");
            assertEquals(LocalDateTime.of(2025, 12, 31, 23, 0), task.getStartDateTime());
            assertEquals(LocalDateTime.of(2026, 1, 1, 2, 0), task.getEndDateTime());
        } catch (NiniException e) {
            fail("Exception should not have been thrown for valid edge case.");
        }
    }
}
