package components;

import exceptions.NiniException;
import org.junit.jupiter.api.Test;
import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.Task;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {

    @Test
    void testAddTask() {
        TaskList taskList = new TaskList();
        Task task = new Task("Test Task");

        taskList.addTask(task);

        assertEquals(1, taskList.size());
        assertEquals(task, taskList.getTask(0));
    }

    @Test
    void testRemoveTask() {
        TaskList taskList = new TaskList();
        Task task = new Task("Test Task");

        taskList.addTask(task);
        Task removedTask = taskList.removeTask(0);

        assertEquals(task, removedTask);
        assertTrue(taskList.getTasks().isEmpty());
    }

    @Test
    void testMarkTask() {
        TaskList taskList = new TaskList();
        Task task = new Task("Test Task");

        taskList.addTask(task);
        taskList.markTask(0);

        assertTrue(task.isDone());
    }

    @Test
    void testUnmarkTask() {
        TaskList taskList = new TaskList();
        Task task = new Task("Test Task");
        task.markAsDone();

        taskList.addTask(task);
        taskList.unmarkTask(0);

        assertFalse(task.isDone());
    }

    @Test
    void testIsValidIndex() {
        TaskList taskList = new TaskList();
        Task task = new Task("Test Task");

        taskList.addTask(task);

        assertTrue(taskList.isValidIndex(0));
        assertFalse(taskList.isValidIndex(1));
    }

    @Test
    void testSortTasks() {
        TaskList taskList = new TaskList();

        try {
            DeadlineTask deadlineTask = new DeadlineTask("Deadline Task", "25/12/2025 1800");

            EventTask eventTask = new EventTask("Event Task", "24/12/2025 0900", "24/12/2025 1700");
            Task simpleTask = new Task("Simple Task");

            taskList.addTask(simpleTask);
            taskList.addTask(deadlineTask);
            taskList.addTask(eventTask);

            taskList.sortTasks();

            assertEquals(eventTask, taskList.getTask(0));
            assertEquals(deadlineTask, taskList.getTask(1));
            assertEquals(simpleTask, taskList.getTask(2));
        } catch (NiniException e) {
            fail("Exception should not have been thrown for valid input.");
        }
    }

    @Test
    void testSize() {
        TaskList taskList = new TaskList();
        assertEquals(0, taskList.size());

        Task task = new Task("Test Task");
        taskList.addTask(task);

        assertEquals(1, taskList.size());
    }
}