package components;

import java.util.List;
import java.util.stream.IntStream;

import tasks.Task;

/**
 * Handles user interface interactions by displaying messages to the user.
 * This class provides methods to print system messages, task lists, and error messages.
 */
public class Ui {

    private static final String GREETING_MESSAGE = "Hello! I'm NiniNana\nWhat can I do for you?";
    private static final String GOODBYE_MESSAGE = "Bye. Hope to see you again soon!";
    private static final String EMPTY_LIST_MESSAGE = "The list is empty.";
    private static final String TASK_LIST_HEADER = "Here are the tasks in your list:";

    /**
     * Displays a greeting message when the program starts.
     */
    public String showGreeting() {
        return GREETING_MESSAGE;
    }

    /**
     * Displays a goodbye message when the program exits.
     */
    public String showGoodbye() {
        return GOODBYE_MESSAGE;
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
     * Displays a message confirming that a task has been added.
     *
     * @param task The task that was added.
     * @param size The total number of tasks after the addition.
     * @return     String message showing task added
     */
    public String showTaskAdded(Task task, int size) {
        return String.format(
                "Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                task, size);
    }

    /**
     * Displays a message confirming that a task has been removed.
     *
     * @param task The task that was removed.
     * @param size The total number of tasks after the removal.
     */
    public String showTaskRemoved(Task task, int size) {
        return String.format(
                "Noted. I've removed this task:\n  %s\nNow you have %d tasks in the list.",
                task, size);
    }

}
