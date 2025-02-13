package commands.tasks;

import java.util.List;
import java.util.stream.IntStream;

import commands.Command;
import components.ContactList;
import components.ContactStorage;
import components.TaskList;
import components.TaskStorage;
import tasks.Task;

/**
 * Represents a command to display the list of tasks.
 * This command retrieves the tasks from the task list and displays them to the user.
 */
public class ListTaskCommand extends Command {

    private static final String EMPTY_LIST_MESSAGE = "The task list is empty.";
    private static final String TASK_LIST_HEADER = "Here are the tasks in your list:";

    /**
     * Executes the list command.
     * Retrieves and displays all tasks stored in the task list.
     *
     * @param taskList The task list containing the tasks.
     * @param taskStorage  The storage component (not used in this command).
     * @return         The list of tasks.
     */
    @Override
    public String execute(TaskList taskList, ContactList contactList,
                          TaskStorage taskStorage, ContactStorage contactStorage) {
        return showTaskList(taskList.getTasks());
    }

    /**
     * Displays the list of tasks currently stored.
     *
     * @param tasks The list of tasks to be displayed.
     */
    public String showTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return EMPTY_LIST_MESSAGE;
        }

        return IntStream.range(0, tasks.size())
                .mapToObj(i -> String.format("%d. %s", i + 1, tasks.get(i)))
                .reduce(TASK_LIST_HEADER, (list, task) -> list + "\n" + task);
    }
}
