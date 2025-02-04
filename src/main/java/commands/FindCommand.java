package commands;

import java.util.List;

import components.Storage;
import components.TaskList;
import components.Ui;
import exceptions.NiniException;
import tasks.Task;

/**
 * Represents a command that finds tasks in the task list based on a keyword.
 * The search is case-insensitive and matches tasks whose descriptions contain the keyword.
 */
public class FindCommand extends Command {

    private final String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     *
     * @param keyword The keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes the find command by searching for tasks that contain the specified keyword.
     * If matching tasks are found, they are displayed to the user. Otherwise, a message
     * indicating no matches is shown.
     *
     * @param taskList The list of tasks to search within.
     * @param ui       The user interface component to display messages.
     * @param storage  The storage component (not used in this command).
     * @return         A list of tasks matching the keyword
     * @throws NiniException If an error occurs during execution.
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws NiniException {
        List<Task> matchingTasks = taskList.findTasks(keyword);

        if (matchingTasks.isEmpty()) {
            return ui.formatMessage("No matching tasks found");
        } else {
            return ui.showTaskList(matchingTasks);
        }
    }
}
