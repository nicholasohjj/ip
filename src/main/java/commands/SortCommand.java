package commands;

import components.Storage;
import components.TaskList;
import components.Ui;
import exceptions.NiniException;

/**
 * Represents a command to sort tasks by their deadlines or event start times.
 * This command modifies the task list and notifies the user of the sorting operation.
 */
public class SortCommand extends Command{

    /**
     * Executes the sort command.
     * Sorts the tasks in the task list based on their deadlines or event start times
     * and displays a confirmation message to the user.
     *
     * @param taskList The task list containing tasks to be sorted.
     * @param ui       The user interface for displaying messages.
     * @param storage  The storage component (not used in this command).
     * @throws NiniException Not thrown in this implementation.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws NiniException {
        taskList.sortTasks();
        Ui.printLineWithMessage("Tasks sorted by date!");
    }
}
