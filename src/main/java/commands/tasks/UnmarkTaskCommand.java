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
 * Represents a command to unmark a task as not done.
 * This command updates the task's status, notifies the user, and updates storage.
 */
public class UnmarkTaskCommand extends Command {

    private static final String ASSERT_TASKLIST_NULL = "Task list cannot be null";
    private static final String ASSERT_STORAGE_NULL = "Storage cannot be null";
    private static final String ASSERT_TASKINDEX_NEGATIVE = "Task index must be non-negative";
    private static final String ERROR_INVALID_TASK_NUMBER = "Invalid task number. Please enter a number between 1 and ";
    private static final String ERROR_ALREADY_UNMARKED = "Error: Task is already unmarked.";
    private static final String ERROR_STORAGE_UPDATE = "Error saving updated task list: ";

    private final int[] unmarkIndices;


    /**
     * Constructs an {@code UnmarkCommand} with the specified task index.
     *
     * @param unmarkIndices The indices of the tasks to be unmarked as not done (zero-based).
     */
    public UnmarkTaskCommand(int... unmarkIndices) {
        assert unmarkIndices != null && unmarkIndices.length > 0 : "Unmark indices cannot be null or empty";
        this.unmarkIndices = unmarkIndices;
    }

    /**
     * Executes the command to mark the specified task as not done.
     * If the task index is invalid, an exception is thrown.
     *
     * @param taskList The task list containing the task.
     * @param taskStorage  The storage component responsible for saving tasks.
     * @return A confirmation message indicating the task has been unmarked.
     * @throws NiniException If the task index is invalid or an error occurs while updating storage.
     */
    @Override
    public String execute(TaskList taskList, ContactList contactList,
                          TaskStorage taskStorage, ContactStorage contactStorage) throws NiniException {
        assert taskList != null : ASSERT_TASKLIST_NULL;
        assert taskStorage != null : ASSERT_STORAGE_NULL;

        StringBuilder confirmationMessage = new StringBuilder();
        int[] uniqueIndices = Arrays.stream(unmarkIndices).distinct().toArray();

        for (int index : unmarkIndices) {
            assert index >= 0 : ASSERT_TASKINDEX_NEGATIVE;
            validateIndex(taskList, index);

            Task task = unmarkTask(taskList, index);

            confirmationMessage.append("OK, I've marked this task as not done yet:\n  ")
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
    private void validateIndex(TaskList taskList, int taskIndex) throws NiniException {
        try {
            taskList.getTask(taskIndex); // Calls TaskList's validateIndex()
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidTaskNumberException(ERROR_INVALID_TASK_NUMBER + taskList.size() + ".");
        }
    }

    /**
     * Unmarks a task as not done.
     *
     * @param taskList The task list.
     * @param taskIndex The index of the task to unmark.
     * @return The unmarked task.
     * @throws NiniException If the task is already unmarked.
     */
    private Task unmarkTask(TaskList taskList, int taskIndex) throws NiniException {
        Task task = taskList.getTask(taskIndex);
        if (!task.isDone()) {
            throw new InvalidTaskNumberException(ERROR_ALREADY_UNMARKED);
        }
        taskList.unmarkTask(taskIndex);
        return task;
    }

    /**
     * Updates storage after unmarking tasks.
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
     * Returns the one-based index of the task to be unmarked.
     *
     * @return The one-based index of the task.
     */
    public int[] getUnmarkIndices() {
        return this.unmarkIndices;
    }
}
