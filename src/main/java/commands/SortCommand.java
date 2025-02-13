package commands;

import components.Storage;
import components.TaskList;

/**
 * Represents a command to sort tasks by their deadlines or event start times.
 * This command modifies the task list and notifies the user of the sorting operation.
 */
public class SortCommand extends Command {

    /**
     * Executes the command to mark the specified task as not done.
     * If the task index is invalid, an exception is thrown.
     *
     * @param taskList The task list containing the task.
     * @param storage  The storage component responsible for saving tasks.
     * @return A confirmation message indicating the task has been unmarked.
     */
    @Override
    public String execute(TaskList taskList, Storage storage) {
        taskList.sortTasks();
        return "Tasks sorted by date!";
    }
}
