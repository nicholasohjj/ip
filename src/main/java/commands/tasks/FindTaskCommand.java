package commands.tasks;

import java.util.List;
import java.util.stream.IntStream;

import commands.Command;
import components.ContactList;
import components.ContactStorage;
import components.TaskList;
import components.TaskStorage;
import exceptions.NiniException;
import tasks.Task;

/**
 * Represents a command that finds tasks in the task list based on a keyword.
 * The search is case-insensitive and matches tasks whose descriptions contain the keyword.
 */
public class FindTaskCommand extends Command {

    private static final String ASSERT_KEYWORD_NULL = "Keyword cannot be null or empty";
    private static final String ASSERT_TASKLIST_NULL = "Task list cannot be null";
    private static final String ASSERT_MATCHING_TASKS_NULL = "Matching tasks list should not be null";
    private static final String EMPTY_LIST_MESSAGE = "The list is empty.";
    private static final String TASK_LIST_HEADER = "Here are the tasks in your list:";

    private final String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     *
     * @param keyword The keyword to search for in task descriptions.
     */
    public FindTaskCommand(String keyword) {
        assert keyword != null && !keyword.isBlank() : ASSERT_KEYWORD_NULL;
        this.keyword = keyword;
    }

    /**
     * Executes the find command by searching for tasks that contain the specified keyword.
     * If matching tasks are found, they are displayed to the user. Otherwise, a message
     * indicating no matches is shown.
     *
     * @param taskList The list of tasks to search within.
     * @param taskStorage  The storage component (not used in this command).
     * @return         A list of tasks matching the keyword
     * @throws NiniException If an error occurs during execution.
     */
    @Override
    public String execute(TaskList taskList, ContactList contactList,
                          TaskStorage taskStorage, ContactStorage contactStorage) throws NiniException {
        assert taskList != null : ASSERT_TASKLIST_NULL;

        List<Task> matchingTasks = searchTasks(taskList);

        if (matchingTasks.isEmpty()) {
            return "No matching tasks found";
        } else {
            return showTaskList(matchingTasks);
        }
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
