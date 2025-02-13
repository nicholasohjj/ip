package commands;

import java.io.IOException;
import java.util.Arrays;

import components.Storage;
import components.TaskList;
import exceptions.InvalidTaskNumberException;
import exceptions.NiniException;
import tasks.Task;

/**
 * Represents a command to delete a task from the task list.
 * This command removes a task from the list, updates storage, and notifies the user.
 */
public class DeleteCommand extends Command {

    private static final String ASSERT_TASKLIST_NULL = "Task list cannot be null";
    private static final String ASSERT_STORAGE_NULL = "Storage cannot be null";
    private static final String ASSERT_TASKINDEX_NEGATIVE = "Task index must be non-negative";
    private static final String ERROR_INVALID_TASK_NUMBER = "Invalid task number. Please enter a number between 1 and ";
    private static final String ERROR_STORAGE_UPDATE = "Error saving updated task list: ";

    private final int[] taskIndices;

    /**
     * Constructs a {@code DeleteCommand} with the specified task index.
     *
     * @param taskIndices The indices of the tasks to be deleted (zero-based).
     */
    public DeleteCommand(int... taskIndices) {
        assert taskIndices != null && taskIndices.length > 0 : "Task indices cannot be null or empty";
        this.taskIndices = taskIndices;
    }

    /**
     * Executes the delete task command.
     * Removes the task from the task list, displays a confirmation message,
     * and updates the storage.
     *
     * @param taskList The task list from which the task is deleted.
     * @param storage  The storage component responsible for saving tasks.
     * @throws NiniException If the task index is invalid or an error occurs while updating storage.
     */
    @Override
    public String execute(TaskList taskList, Storage storage) throws NiniException {
        assert taskList != null : ASSERT_TASKLIST_NULL;
        assert storage != null : ASSERT_STORAGE_NULL;

        int initialSize = taskList.size();
        StringBuilder confirmationMessage = new StringBuilder();

        int[] sortedIndices = Arrays.stream(taskIndices)
                .distinct()
                .sorted()
                .toArray();

        for (int taskIndex : sortedIndices) {
            assert taskIndex >= 0 : ASSERT_TASKINDEX_NEGATIVE;
            validateIndex(taskList, taskIndex);

            Task removedTask = taskList.removeTask(taskIndex);
            confirmationMessage.append(showTaskRemoved(removedTask, taskList.size())).append("\n");
        }

        updateStorage(storage, taskList, confirmationMessage, initialSize);
        return confirmationMessage.toString().trim();
    }

    /**
     * Displays a message confirming that a task has been removed.
     *
     * @param task The task that was removed.
     * @param size The total number of tasks after the removal.
     */
    public String showTaskRemoved(Task task, int size) {
        return String.format(
                "Noted. I've removed this task:\n  %s\nNow you have %d tasks in the list.",
                task, size);
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
            taskList.getTask(taskIndex); // Triggers TaskList's validateIndex()
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidTaskNumberException(ERROR_INVALID_TASK_NUMBER + taskList.size() + ".");
        }
    }

    /**
     * Updates storage after tasks are deleted.
     *
     * @param storage The storage component.
     * @param taskList The updated task list.
     * @param confirmationMessage The confirmation message builder.
     * @param initialSize The initial size of the task list.
     */
    private void updateStorage(Storage storage, TaskList taskList, StringBuilder confirmationMessage, int initialSize) {
        try {
            if (taskList.size() < initialSize) {
                storage.overwriteTasks(taskList.getTasks());
            }
        } catch (IOException e) {
            confirmationMessage.append("\n").append(ERROR_STORAGE_UPDATE).append(e.getMessage());
        }
    }

    /**
     * Returns the index of the task to be deleted.
     *
     * @return The zero-based index of the task.
     */
    public int[] getDeleteIndices() {
        return this.taskIndices;
    }
}
