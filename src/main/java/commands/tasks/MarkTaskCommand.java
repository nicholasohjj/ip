package commands.tasks;

import java.io.IOException;
import java.util.Arrays;

import commands.Command;
import components.ContactList;
import components.ContactStorage;
import components.TaskList;
import components.TaskStorage;
import exceptions.InvalidTaskNumberException;
import exceptions.NiniException;
import tasks.Task;

/**
 * Represents a command to mark a task as done.
 * This command updates the task's status, notifies the user, and updates storage.
 */
public class MarkTaskCommand extends Command {

    private static final String ASSERT_TASKLIST_NULL = "Task list cannot be null";
    private static final String ASSERT_STORAGE_NULL = "Storage cannot be null";
    private static final String ASSERT_TASKINDEX_NEGATIVE = "Task index must be non-negative";
    private static final String ERROR_INVALID_TASK_NUMBER = "Invalid task number. Please enter a number between 1 and ";
    private static final String ERROR_ALREADY_MARKED = "Error: Task is already marked as done.";
    private static final String ERROR_STORAGE_UPDATE = "Error saving updated task list: ";

    private final int[] markIndices;

    /**
     * Constructs a {@code MarkCommand} with the specified task index.
     *
     * @param markIndices The indices of the tasks to be marked as done (zero-based).
     */
    public MarkTaskCommand(int... markIndices) {
        assert markIndices != null && markIndices.length > 0 : "Mark indices cannot be null or empty";
        this.markIndices = markIndices;
    }

    /**
     * Executes the mark task command.
     * Marks the specified task as done, displays a confirmation message,
     * and updates the storage.
     *
     * @param taskList The task list containing the task.
     * @param taskStorage  The storage component responsible for saving tasks.
     * @return A confirmation message indicating the task has been marked as done.
     * @throws NiniException If the task index is invalid or an error occurs while updating storage.
     */
    @Override
    public String execute(TaskList taskList, ContactList contactList,
                          TaskStorage taskStorage, ContactStorage contactStorage) throws NiniException {
        assert taskList != null : ASSERT_TASKLIST_NULL;
        assert taskStorage != null : ASSERT_STORAGE_NULL;

        StringBuilder confirmationMessage = new StringBuilder();
        int[] uniqueIndices = Arrays.stream(markIndices).distinct().toArray();

        for (int index : markIndices) {
            assert index >= 0 : ASSERT_TASKINDEX_NEGATIVE;
            validateIndex(taskList, index);

            Task task = markTaskAsDone(taskList, index);
            confirmationMessage.append("Nice! I've marked this task as done:\n  ")
                        .append(task).append("\n");
        }

        updateStorage(taskStorage, taskList, confirmationMessage);
        return confirmationMessage.toString().trim();
    }

    /**
     * Validates if the task index is within the valid range.
     *
     * @param taskList The task list.
     * @param taskIndex The task index to validate.
     * @throws InvalidTaskNumberException If the index is out of bounds.
     */
    private void validateIndex(TaskList taskList, int taskIndex) throws InvalidTaskNumberException {
        try {
            taskList.getTask(taskIndex); // Calls TaskList's validateIndex()
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidTaskNumberException(ERROR_INVALID_TASK_NUMBER + taskList.size() + ".");
        }
    }

    /**
     * Marks a task as done.
     *
     * @param taskList The task list.
     * @param taskIndex The index of the task to mark.
     * @return The marked task.
     * @throws IllegalStateException If the task is already marked as done.
     */
    private Task markTaskAsDone(TaskList taskList, int taskIndex) throws IllegalStateException {
        Task task = taskList.getTask(taskIndex);
        if (task.isDone()) {
            throw new IllegalStateException(ERROR_ALREADY_MARKED);
        }
        taskList.markTask(taskIndex);
        return task;
    }

    /**
     * Updates storage after marking tasks as done.
     *
     * @param taskStorage The storage component.
     * @param taskList The updated task list.
     * @param confirmationMessage The confirmation message builder.
     */
    private void updateStorage(TaskStorage taskStorage, TaskList taskList, StringBuilder confirmationMessage) {
        try {
            taskStorage.overwriteTasks(taskList.getTasks());
        } catch (IOException e) {
            confirmationMessage.append("\n").append(ERROR_STORAGE_UPDATE).append(e.getMessage());
        }
    }

    /**
     * Returns the index of the task to be marked as done.
     *
     * @return The zero-based index of the task.
     */
    public int[] getMarkIndices() {
        return markIndices;
    }
}
