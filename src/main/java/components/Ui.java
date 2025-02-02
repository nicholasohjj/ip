package components;

import java.util.List;

import tasks.Task;

/**
 * Handles user interface interactions by displaying messages to the user.
 * This class provides methods to print system messages, task lists, and error messages.
 */
public class Ui {

    private static final String LINE = "____________________________________________________________";

    /**
     * Displays a greeting message when the program starts.
     */
    public String showGreeting() {
        return formatMessage(" Hello! I'm NiniNana\n What can I do for you?");
    }

    /**
     * Displays a goodbye message when the program exits.
     */
    public String showGoodbye() {
        return formatMessage("Bye. Hope to see you again soon!");
    }

    /**
     * Displays an error message.
     *
     * @param errorMessage The error message to be displayed.
     */
    public String showError(String errorMessage) {
        return formatMessage(errorMessage);
    }

    /**
     * Displays the list of tasks currently stored.
     *
     * @param tasks The list of tasks to be displayed.
     */
    public String showTaskList(List<Task> tasks) {
        if (tasks.isEmpty()) {
            return formatMessage("The list is empty.");
        } else {
            StringBuilder listMessage = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                listMessage.append(String.format("%d. %s%n", i + 1, tasks.get(i)));
            }
            return listMessage.toString();
        }
    }

    /**
     * Displays a message confirming that a task has been added.
     *
     * @param task The task that was added.
     * @param size The total number of tasks after the addition.
     * @return
     */
    public String showTaskAdded(Task task, int size) {
        return formatMessage(String.format(
                "Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                task, size));
    }

    /**
     * Displays a message confirming that a task has been removed.
     *
     * @param task The task that was removed.
     * @param size The total number of tasks after the removal.
     */
    public String showTaskRemoved(Task task, int size) {
        return formatMessage(String.format(
                "Noted. I've removed this task:\n  %s\nNow you have %d tasks in the list.",
                task, size));
    }

    /**
     * Formats a message with horizontal lines for consistency.
     *
     * @param message The message to format.
     * @return The formatted message.
     */
    public String formatMessage(String message) {
        return String.format("%s%n%s%n%s", LINE, message, LINE);
    }

}
