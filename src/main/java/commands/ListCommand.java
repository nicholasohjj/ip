package commands;

import components.Storage;
import components.TaskList;
import components.Ui;

/**
 * Represents a command to display the list of tasks.
 * This command retrieves the tasks from the task list and displays them to the user.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command.
     * Retrieves and displays all tasks stored in the task list.
     *
     * @param taskList The task list containing the tasks.
     * @param ui       The user interface responsible for displaying messages.
     * @param storage  The storage component (not used in this command).
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showTaskList(taskList.getTasks());
    }
}
