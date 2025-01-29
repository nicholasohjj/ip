package commands;

import components.Storage;
import components.TaskList;
import components.Ui;
import exceptions.InvalidTaskNumberException;
import exceptions.NiniException;

/**
 * Represents a command to mark a task as done.
 * This command updates the task's status, notifies the user, and updates storage.
 */
public class MarkCommand extends Command{
    private final int markIndex;

    /**
     * Constructs a {@code MarkCommand} with the specified task index.
     *
     * @param markIndex The index of the task to be marked as done (zero-based).
     */
    public MarkCommand(int markIndex) {
        this.markIndex = markIndex;
    }

    /**
     * Executes the mark task command.
     * Marks the specified task as done, displays a confirmation message,
     * and updates the storage.
     *
     * @param taskList The task list containing the task.
     * @param ui       The user interface for displaying messages.
     * @param storage  The storage component responsible for saving tasks.
     * @throws NiniException If the task index is invalid or an error occurs while updating storage.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws NiniException {
        if (taskList.isValidIndex(markIndex)) {
            try {
                taskList.markTask(markIndex);
                Ui.printLineWithMessage("Nice! I've marked this task as done:\n  " + taskList.getTask(markIndex));
                storage.overwriteTasks(taskList.getTasks());
            } catch (IllegalStateException e) {
                ui.showError(e.getMessage());
            }
        } else {
            throw new InvalidTaskNumberException("Invalid task number. Please enter a number between 1 and " + taskList.size() + ".");
        }
    }

    /**
     * Returns the index of the task to be marked as done.
     *
     * @return The zero-based index of the task.
     */
    public int getTaskIndex() {
        return markIndex;
    }
}
