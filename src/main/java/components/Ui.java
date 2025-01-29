package components;

import tasks.Task;

import java.util.List;

public class Ui {

    private static final String LINE = "____________________________________________________________";


    public void showGreeting() {
        printLineWithMessage(" Hello! I'm NiniNana\n What can I do for you?");
    }

    public void showGoodbye() {
        printLineWithMessage("Bye. Hope to see you again soon!");
    }

    public void showError(String errorMessage) {
        printLineWithMessage(errorMessage);
    }

    public void showTaskList(List<Task> tasks) {
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

    public void showTaskAdded(Task task, int size) {
        printLineWithMessage(String.format(
                "Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                task, size));
    }

    public void showTaskRemoved(Task task, int size) {
        printLineWithMessage(String.format(
                "Noted. I've removed this task:\n  %s\nNow you have %d tasks in the list.",
                task, size));
    }

    public void printLine() {
        System.out.println(LINE);
    }

    public void printLineWithMessage(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

}
