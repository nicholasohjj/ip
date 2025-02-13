package commands.tasks;

import commands.Command;
import components.ContactList;
import components.ContactStorage;
import components.TaskList;
import components.TaskStorage;

/**
 * Represents a command to sort tasks by their deadlines or event start times.
 * This command modifies the task list and notifies the user of the sorting operation.
 */
public class SortTaskCommand extends Command {

    /**
     * Executes the command to mark the specified task as not done.
     * If the task index is invalid, an exception is thrown.
     *
     * @param taskList The task list containing the task.
     * @param taskStorage  The storage component responsible for saving tasks.
     * @return A confirmation message indicating the task has been unmarked.
     */
    @Override
    public String execute(TaskList taskList, ContactList contactList,
                          TaskStorage taskStorage, ContactStorage contactStorage) {
        taskList.sortTasks();
        return "Tasks sorted by date!";
    }
}
