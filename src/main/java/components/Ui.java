package components;

import tasks.Task;

import java.util.ArrayList;

/**
 * Handles user interface interactions by displaying messages to the user.
 * This class provides methods to print system messages, task lists, and error messages.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";

    /**
     * Prints a horizontal line to separate sections in the output.
     */
    public static void printLine() {
        System.out.println(LINE);
    }

    /**
     * Prints a horizontal line along with a specified message.
     *
     * @param message The message to be displayed between the lines.
     */
    public static void printLineWithMessage(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    /**
     * Displays a greeting message when the program starts.
     */
    public void showGreeting() {
        printLineWithMessage(" Hello! I'm NiniNana\n What can I do for you?");
    }

    /**
     * Displays a goodbye message when the program exits.
     */
    public void showGoodbye() {
        printLineWithMessage("Bye. Hope to see you again soon!");
    }

    /**
     * Displays an error message.
     *
     * @param errorMessage The error message to be displayed.
     */
    public void showError(String errorMessage) {
        printLineWithMessage(errorMessage);
    }

    /**
     * Displays the list of tasks currently stored.
     *
     * @param tasks The list of tasks to be displayed.
     */
    public void showTaskList(ArrayList<Task> tasks) {
        printLine();
        if (tasks.isEmpty()) {
            System.out.println("The list is empty.");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, tasks.get(i));
            }
        }
        printLine();
    }

    /**
     * Displays a message confirming that a task has been added.
     *
     * @param task The task that was added.
     * @param size The total number of tasks after the addition.
     */
    public void showTaskAdded(Task task, int size) {
        printLineWithMessage(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.", task, size));
    }

    /**
     * Displays a message confirming that a task has been removed.
     *
     * @param task The task that was removed.
     * @param size The total number of tasks after the removal.
     */
    public void showTaskRemoved(Task task, int size) {
        printLineWithMessage(String.format("Noted. I've removed this task:\n  %s\nNow you have %d tasks in the list.", task, size));
    }
}
