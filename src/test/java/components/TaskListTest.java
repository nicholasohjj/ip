package components;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.NiniException;
import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.Task;
import tasks.ToDoTask;

class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
    }

    @Test
    void testAddTask() {
        // Arrange
        Task task = new ToDoTask("Test Task");

        // Act
        taskList.addTask(task);

        // Assert
        assertEquals(1, taskList.size());
        assertEquals(task, taskList.getTask(0));
    }

    @Test
    void testRemoveTask_validIndex() {
        // Arrange
        Task task = new ToDoTask("Test Task");
        taskList.addTask(task);

        // Act
        Task removedTask = taskList.removeTask(0);

        // Assert
        assertEquals(task, removedTask);
        assertTrue(taskList.getTasks().isEmpty());
    }

    @Test
    void testRemoveTask_invalidIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.removeTask(0));
    }

    @Test
    void testMarkTask_validIndex() {
        // Arrange
        Task task = new ToDoTask("Test Task");
        taskList.addTask(task);

        // Act
        taskList.markTask(0);

        // Assert
        assertTrue(task.isDone());
    }

    @Test
    void testMarkTask_invalidIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.markTask(0));
    }

    @Test
    void testUnmarkTask_validIndex() {
        // Arrange
        Task task = new ToDoTask("Test Task");
        task.markAsDone();
        taskList.addTask(task);

        // Act
        taskList.unmarkTask(0);

        // Assert
        assertFalse(task.isDone());
    }

    @Test
    void testUnmarkTask_invalidIndexThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> taskList.unmarkTask(0));
    }

    @Test
    void testSortTasks() {
        try {
            // Arrange
            DeadlineTask deadlineTask = new DeadlineTask("Deadline Task", "25/12/2025 1800");
            EventTask eventTask = new EventTask("Event Task", "24/12/2025 0900", "24/12/2025 1700");
            Task simpleTask = new ToDoTask("Simple Task");

            taskList.addTask(simpleTask);
            taskList.addTask(deadlineTask);
            taskList.addTask(eventTask);

            // Act
            taskList.sortTasks();

            // Assert
            assertEquals(eventTask, taskList.getTask(0));
            assertEquals(deadlineTask, taskList.getTask(1));
            assertEquals(simpleTask, taskList.getTask(2));
        } catch (NiniException e) {
            fail("Exception should not have been thrown for valid input.");
        }
    }

    @Test
    void testSize() {
        assertEquals(0, taskList.size());

        Task task = new ToDoTask("Test Task");
        taskList.addTask(task);

        assertEquals(1, taskList.size());
    }
}
