package components;

import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Manages a list of tasks, providing methods to add, remove, mark, unmark,
 * retrieve, and sort tasks based on deadlines or event start times.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

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
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the list of tasks.
     *
     * @return The {@code ArrayList} containing all tasks.
     */
    public ArrayList<Task> getTasks() {
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
     * Removes a task from the task list at the specified index.
     *
     * @param index The index of the task to be removed.
     * @return The removed task.
     */
    public Task removeTask(int index) {
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the task list at the specified index.
     *
     * @param index The index of the task to retrieve.
     * @return The task at the given index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Marks a task as done at the specified index.
     *
     * @param index The index of the task to mark as done.
     */
    public void markTask(int index) {
        tasks.get(index).markAsDone();
    }

    /**
     * Unmarks a task at the specified index, setting it as not done.
     *
     * @param index The index of the task to unmark.
     */
    public void unmarkTask(int index) {
        tasks.get(index).unmark();
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
            LocalDateTime date1 = null;
            LocalDateTime date2 = null;

            if (task1 instanceof DeadlineTask) {
                date1 = ((DeadlineTask) task1).getDeadline();
            } else if (task1 instanceof EventTask) {
                date1 = ((EventTask) task1).getStartDateTime(); // Assume tasks.EventTask has a startDateTime field
            }

            if (task2 instanceof DeadlineTask) {
                date2 = ((DeadlineTask) task2).getDeadline();
            } else if (task2 instanceof EventTask) {
                date2 = ((EventTask) task2).getStartDateTime();
            }

            if (date1 == null && date2 == null) {
                return 0;
            } else if (date1 == null) {
                return 1; // Null dates go to the end
            } else if (date2 == null) {
                return -1; // Null dates go to the end
            } else {
                return date1.compareTo(date2); // Compare the dates
            }
        });
    }
}
