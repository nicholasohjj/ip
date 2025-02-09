package components;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tasks.Task;

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
        assert tasks != null : "Tasks list cannot be null";
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
        assert task != null : "Task to add cannot be null";
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
            LocalDateTime date1 = task1.getRelevantDate();
            LocalDateTime date2 = task2.getRelevantDate();

            if (date1 == null && date2 == null) {
                return 0;
            }
            if (date1 == null) {
                return 1;
            }
            if (date2 == null) {
                return -1;
            }
            return date1.compareTo(date2);
        });
    }

    /**
     * Finds and returns a list of tasks whose descriptions contain the specified keyword.
     * The search is case-insensitive.
     *
     * @param keyword The keyword to search for in task descriptions.
     * @return A list of tasks whose descriptions contain the specified keyword.
     */
    public List<Task> findTasks(String keyword) {
        assert keyword != null && !keyword.isBlank() : "Search keyword cannot be null or empty";

        return this.tasks.stream()
                .filter(task -> task.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
