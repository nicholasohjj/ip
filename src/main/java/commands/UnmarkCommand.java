package commands;

import java.io.IOException;

import components.Storage;
import components.TaskList;
import components.Ui;
import exceptions.InvalidTaskNumberException;
import exceptions.NiniException;

/**
 * Represents a command to unmark a task as not done.
 * This command updates the task's status, notifies the user, and updates storage.
 */
public class UnmarkCommand extends Command {

    private final int[] unmarkIndices;


    /**
     * Constructs an {@code UnmarkCommand} with the specified task index.
     *
     * @param unmarkIndices The indices of the tasks to be unmarked as not done (zero-based).
     */
    public UnmarkCommand(int... unmarkIndices) {
        this.unmarkIndices = unmarkIndices;
    }

    /**
     * Executes the command to mark the specified task as not done.
     * If the task index is invalid, an exception is thrown.
     *
     * @param taskList The task list containing the task.
     * @param ui       The user interface for displaying messages.
     * @param storage  The storage component responsible for saving tasks.
     * @return A confirmation message indicating the task has been unmarked.
     * @throws NiniException If the task index is invalid or an error occurs while updating storage.
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws NiniException {
        StringBuilder confirmationMessage = new StringBuilder();

        for (int index : unmarkIndices) {
            if (!taskList.isValidIndex(index)) {
                throw new InvalidTaskNumberException("Invalid task number. Please enter a number between 1 and "
                        + taskList.size() + ".");
            }

            try {
                taskList.unmarkTask(index);
                confirmationMessage.append(ui.formatMessage("OK, I've marked this task as not done yet:\n  "
                        + taskList.getTask(index))).append("\n");
            } catch (IllegalStateException e) {
                return ui.showError("Error: Task is already unmarked.");
            }

            try {
                storage.overwriteTasks(taskList.getTasks());
            } catch (IOException e) {
                return ui.showError("Error saving updated task list: " + e.getMessage());
            }
        }
        return confirmationMessage.toString();
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
