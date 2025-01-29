package components;

import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

/**
 * Manages a list of tasks, providing methods to add, remove, mark, unmark,
 * retrieve, and sort tasks based on deadlines or event start times.
 */
public class TaskList {
    
    private final List<Task> tasks;

    /**
     * Constructs an empty {@code TaskList}.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Constructs a {@code TaskList} with an existing list of tasks.
     *
     * @param tasks The list of tasks to initialize the task list with.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the list of tasks.
     *
     * @return The {@code ArrayList} containing all tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return The total number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Removes a task from the task list at the specified index.
     *
     * @param index The index of the task to be removed.
     * @return The removed task.
     */
    public Task removeTask(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Error: Invalid task index.");
        }
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the task list at the specified index.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the given index.
     */
    public Task getTask(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Error: Invalid task index.");
        }
        return tasks.get(index);
    }

    /**
     * Marks a task as done at the specified index.
     *
     * @param index The index of the task to mark as done.
     */
    public void markTask(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Error: Invalid task index.");
        }
        tasks.get(index).markAsDone();
    }

    /**
     * Unmarks a task at the specified index, setting it as not done.
     *
     * @param index The index of the task to unmark.
     */
    public void unmarkTask(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Error: Invalid task index.");
        }
        tasks.get(index).unmark();
    }

    /**
     * Checks if the provided index is valid within the task list.
     *
     * @param index The index to validate.
     * @return {@code true} if the index is valid, {@code false} otherwise.
     */
    public boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    /**
     * Sorts tasks based on their deadlines or event start times.
     * <ul>
     *     <li>{@code DeadlineTask} objects are sorted by their deadline.</li>
     *     <li>{@code EventTask} objects are sorted by their start time.</li>
     *     <li>Other tasks remain in their original order.</li>
     * </ul>
     * Tasks without dates (e.g., {@code ToDoTask}) are placed at the end.
     */
    public void sortTasks() {
        tasks.sort((task1, task2) -> {
            LocalDateTime date1 = getTaskDate(task1);
            LocalDateTime date2 = getTaskDate(task2);

            if (date1 == null && date2 == null) return 0;
            if (date1 == null) return 1;
            if (date2 == null) return -1;
            return date1.compareTo(date2);
        });
    }

    private LocalDateTime getTaskDate(Task task) {
        if (task instanceof DeadlineTask) {
            return ((DeadlineTask) task).getDeadline();
        } else if (task instanceof EventTask) {
            return ((EventTask) task).getStartDateTime();
        }
        return null;
    }
}
