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

public class DeadlineTaskTest {

    @Test
    public void testConstructorWithValidInput() {
        try {
            DeadlineTask task = new DeadlineTask("Submit report", "25/12/2025 1800");
            assertEquals("Submit report", task.getDescription());
            assertEquals(LocalDateTime.of(2025, 12, 25, 18, 0), task.getDeadline());
            assertFalse(task.isDone());
        } catch (NiniException e) {
            fail("Exception should not have been thrown for valid input");
        }
    }

    @Test
    void testConstructorWithInvalidDeadlineFormat() {
        assertThrows(InvalidFormatException.class, () -> {
            new DeadlineTask("Submit report", "25-12-2025 1800");
        });
    }

    @Test
    void testConstructorWithValidInputAndIsDone() {
        try {
            DeadlineTask task = new DeadlineTask("Submit report", "25/12/2025 1800", true);
            assertEquals("Submit report", task.getDescription());
            assertEquals(LocalDateTime.of(2025, 12, 25, 18, 0), task.getDeadline());
            assertTrue(task.isDone());
        } catch (NiniException e) {
            fail("Exception should not have been thrown for valid input.");
        }
    }

    @Test
    void testSerialize() {
        try {
            DeadlineTask task = new DeadlineTask("Submit report", "25/12/2025 1800", true);
            assertEquals("D|1|Submit report|25/12/2025 1800", task.serialize());
        } catch (NiniException e) {
            fail("Exception should not have been thrown for valid input.");
        }
    }

    @Test
    void testToString() {
        try {
            DeadlineTask task = new DeadlineTask("Submit report", "25/12/2025 1800", false);
            assertEquals("[D][ ] Submit report (deadline: Dec 25 2025, 6:00PM)", task.toString());
        } catch (NiniException e) {
            fail("Exception should not have been thrown for valid input.");
        }
    }

    @Test
    void testInvalidDeadlineThrowsException() {
        assertThrows(InvalidFormatException.class, () -> {
            new DeadlineTask("Invalid date", "invalid-date");
        });
    }

    @Test
    void testEdgeCaseDeadline() {
        try {
            DeadlineTask task = new DeadlineTask("New Year's Eve Party", "31/12/2025 2359");
            assertEquals(LocalDateTime.of(2025, 12, 31, 23, 59), task.getDeadline());
        } catch (NiniException e) {
            fail("Exception should not have been thrown for valid edge case.");
        }
    }
}
