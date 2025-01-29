package commands;

import exceptions.InvalidTaskNumberException;
import exceptions.NiniException;

import components.Ui;
import components.TaskList;
import components.Storage;

import tasks.Task;

/**
 * Represents a command to delete a task from the task list.
 * This command removes a task from the list, updates storage, and notifies the user.
 */
public class DeleteCommand extends Command{

    private final int taskIndex;

    /**
     * Constructs a {@code DeleteCommand} with the specified task index.
     *
     * @param taskIndex The index of the task to be deleted (zero-based).
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    /**
     * Executes the delete task command.
     * Removes the task from the task list, displays a confirmation message,
     * and updates the storage.
     *
     * @param taskList The task list from which the task is deleted.
     * @param ui       The user interface for displaying messages.
     * @param storage  The storage component responsible for saving tasks.
     * @throws NiniException If the task index is invalid or an error occurs while updating storage.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws NiniException {
        if (taskList.isValidIndex(taskIndex)) {
            Task removedTask = taskList.removeTask(taskIndex);
            ui.showTaskRemoved(removedTask, taskList.size());
            storage.overwriteTasks(taskList.getTasks());
        } else {
            throw new InvalidTaskNumberException("Invalid task number. Please enter a number between 1 and " + taskList.size() + ".");
        }
    }

    /**
     * Returns the index of the task to be deleted.
     *
     * @return The zero-based index of the task.
     */
    public int getTaskIndex() {
        return this.taskIndex;
    }
}
