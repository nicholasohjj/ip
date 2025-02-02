package commands;

import java.io.IOException;

import components.Storage;
import components.TaskList;
import components.Ui;
import exceptions.InvalidTaskNumberException;
import exceptions.NiniException;

/**
 * Represents a command to mark a task as done.
 * This command updates the task's status, notifies the user, and updates storage.
 */
public class MarkCommand extends Command {

    private final int[] markIndices;

    /**
     * Constructs a {@code MarkCommand} with the specified task index.
     *
     * @param markIndices The indices of the tasks to be marked as done (zero-based).
     */
    public MarkCommand(int... markIndices) {
        this.markIndices = markIndices;
    }

    /**
     * Executes the mark task command.
     * Marks the specified task as done, displays a confirmation message,
     * and updates the storage.
     *
     * @param taskList The task list containing the task.
     * @param ui       The user interface for displaying messages.
     * @param storage  The storage component responsible for saving tasks.
     * @return A confirmation message indicating the task has been marked as done.
     * @throws NiniException If the task index is invalid or an error occurs while updating storage.
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws NiniException {
        StringBuilder confirmationMessage = new StringBuilder();

        for (int index : markIndices) {
            if (!taskList.isValidIndex(index)) {
                throw new InvalidTaskNumberException("Invalid task number. Please enter a number between 1 and "
                        + taskList.size() + ".");
            }
            try {
                taskList.markTask(index);
                confirmationMessage.append(ui.formatMessage("Nice! I've marked this task as done:\n  "
                        + taskList.getTask(index))).append("\n");
            } catch (IllegalStateException e) {
                return ui.showError("Error: Task is already marked as done.");
            }
        }
        try {
            storage.overwriteTasks(taskList.getTasks());
        } catch (IOException e) {
            return ui.showError("Error saving updated task list: " + e.getMessage());
        }
        return confirmationMessage.toString();
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
