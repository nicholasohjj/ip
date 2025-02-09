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

    private static final String ASSERT_KEYWORD_NULL = "Keyword cannot be null or empty";
    private static final String ASSERT_TASKLIST_NULL = "Task list cannot be null";
    private static final String ASSERT_UI_NULL = "UI cannot be null";
    private static final String ASSERT_MATCHING_TASKS_NULL = "Matching tasks list should not be null";
    private static final String NO_MATCH_MESSAGE = "No matching tasks found.";

    private final String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     *
     * @param keyword The keyword to search for in task descriptions.
     */
    public FindCommand(String keyword) {
        assert keyword != null && !keyword.isBlank() : ASSERT_KEYWORD_NULL;
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
        assert taskList != null : ASSERT_TASKLIST_NULL;
        assert ui != null : ASSERT_UI_NULL;

        List<Task> matchingTasks = searchTasks(taskList);

        if (matchingTasks.isEmpty()) {
            return "No matching tasks found";
        } else {
            return ui.showTaskList(matchingTasks);
        }
    }

    /**
     * Searches for tasks in the task list that contain the keyword.
     *
     * @param taskList The task list to search within.
     * @return A list of tasks that contain the keyword in their description.
     */
    private List<Task> searchTasks(TaskList taskList) {
        List<Task> matchingTasks = taskList.findTasks(keyword);
        assert matchingTasks != null : ASSERT_MATCHING_TASKS_NULL;
        return matchingTasks;
    }
}
