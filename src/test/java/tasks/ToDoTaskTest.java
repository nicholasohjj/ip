package tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ToDoTaskTest {

    @Test
    void testConstructorWithValidDescription() {
        ToDoTask task = new ToDoTask("Buy groceries");
        assertEquals("Buy groceries", task.getDescription());
        assertFalse(task.isDone());
    }

    @Test
    void testConstructorWithValidDescriptionAndIsDone() {
        ToDoTask task = new ToDoTask("Buy groceries", true);
        assertEquals("Buy groceries", task.getDescription());
        assertTrue(task.isDone());
    }

    @Test
    void testSerialize() {
        ToDoTask task = new ToDoTask("Buy groceries", true);
        assertEquals("T|1|Buy groceries", task.serialize());

        task = new ToDoTask("Buy groceries", false);
        assertEquals("T|0|Buy groceries", task.serialize());
    }

    @Test
    void testToString() {
        ToDoTask task = new ToDoTask("Buy groceries", false);
        assertEquals("[T][ ] Buy groceries", task.toString());

        task = new ToDoTask("Buy groceries", true);
        assertEquals("[T][X] Buy groceries", task.toString());
    }
}
