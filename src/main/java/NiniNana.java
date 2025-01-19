import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.ArrayList;

public class NiniNana {
    private static final String LINE = "____________________________________________________________";
    private static final String GREETING = " Hello! I'm NiniNana\n What can I do for you?";
    private static final String GOODBYE = "Bye. Hope to see you again soon!";

    private static void printLine() {
        System.out.println(LINE);
    }

    private static void printLineWithMessage(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    private static void listTasks(ArrayList<Task> store) throws MissingArgumentException {
        if (store.isEmpty()) {
            throw new MissingArgumentException("The list is empty. Add a task using 'todo', 'deadline', or 'event'.");
        }
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < store.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, store.get(i));
        }
        printLine();
    }

    private static boolean isValidIndex(ArrayList<Task> store, int index) {
        return index >= 0 && index < store.size();
    }

    private static void markTask(ArrayList<Task> store, int index) throws InvalidTaskNumberException {
        if (isValidIndex(store, index)) {
            store.get(index).markAsDone();
            printLineWithMessage("Nice! I've marked this task as done:\n  " + store.get(index));
        } else {
            throw new InvalidTaskNumberException("Invalid task number. Please enter a number between 1 and " + store.size() + ".");
        }
    }

    private static void deleteTask(ArrayList<Task> store, int index) throws InvalidTaskNumberException {
        if (isValidIndex(store, index)) {
            Task removedTask = store.remove(index);
            printLineWithMessage(String.format("Noted. I've removed this task:\n  %s\nNow you have %d tasks in the list.",
                    removedTask, store.size()));
        } else {
            throw new InvalidTaskNumberException("Invalid task number. Please enter a number between 1 and " + store.size() + ".");
        }
    }


    private static void unmarkTask(ArrayList<Task> store, int index) throws InvalidTaskNumberException {
        if (isValidIndex(store, index)) {
            store.get(index).unmark();
            printLineWithMessage("OK, I've marked this task as not done yet:\n  " + store.get(index));
        } else {
            throw new InvalidTaskNumberException("Invalid task number. Please enter a number between 1 and " + store.size() + ".");
        }
    }

    private static void HandleTaskCommand(ArrayList<Task> store, String input, String command) {
        try {
            String[] parts = input.split(" ", 2);

            if (!parts[0].equals(command)) {
                throw new InvalidCommandException("I'm sorry, but I don't know what that means.");
            }

            if (command.equals("mark") || command.equals("unmark") || command.equals("delete")) {
                if (parts.length < 2) {
                    throw new MissingArgumentException("Please specify the task number. Example: 'mark 2'.");
                }
                try {
                    int index = Integer.parseInt(parts[1].trim()) - 1;
                    if (command.equals("mark")) {
                        markTask(store, index);
                    } else if (command.equals("unmark")) {
                        unmarkTask(store, index);
                    } else {
                        deleteTask(store, index);
                    }
                } catch (NumberFormatException e) {
                    throw new InvalidTaskNumberException("Invalid task number. Please enter a valid number. Example: 'mark 2'.");
                }
            }

            if (parts.length < 2) {
                throw new MissingArgumentException("OOPS!!! The description of a " + command + " cannot be empty.");
            }

            String details = parts[1].trim();
            Task task = null;

            switch (command) {
                case "todo":
                    task = new ToDoTask(details);
                    break;
                case "deadline":
                    if (!details.contains("/by")) {
                        throw new InvalidFormatException("Invalid format for deadline. Use: deadline <description> /by <time>");
                    }
                    String[] deadlineParts = details.split("/by", 2);
                    if (deadlineParts.length < 2 || deadlineParts[1].trim().isEmpty()) {
                        throw new InvalidFormatException("Invalid format for deadline. Use: deadline <description> /by <time>");
                    }
                    task = new DeadlineTask(deadlineParts[0].trim(), deadlineParts[1].trim());
                    break;
                case "event":
                    if (!details.contains("/from") || !details.contains("/to")) {
                        throw new InvalidFormatException("Invalid format for event. Use: event <description> /from <start> /to <end>");
                    }
                    String[] eventParts = details.split("/from|/to");
                    if (eventParts.length < 3 || eventParts[1].trim().isEmpty() || eventParts[2].trim().isEmpty()) {
                        throw new InvalidFormatException("Invalid format for event. Use: event <description> /from <start> /to <end>");
                    }
                    task = new EventTask(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
                    break;
                default:
                    throw new InvalidCommandException("Unknown command.");

            }
            store.add(task);
            printLineWithMessage(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
                    task, store.size()));
        } catch (Exception e) {
            printLineWithMessage(e.getMessage());
        }
    }

    private static void chat() {
        ArrayList<Task> store = new ArrayList<>();

        try (Scanner sc = new Scanner(System.in)) {
            String input = null;
            do {
                try {
                    input = sc.nextLine().trim();

                    if (input.equalsIgnoreCase("list")) {
                        listTasks(store);
                    } else if (input.startsWith("mark")) {
                        HandleTaskCommand(store, input, "mark");
                    } else if (input.startsWith("unmark")) {
                        HandleTaskCommand(store, input, "unmark");
                    } else if (input.startsWith("deadline")) {
                        HandleTaskCommand(store, input, "deadline");
                    } else if (input.startsWith("todo")) {
                        HandleTaskCommand(store, input, "todo");
                    } else if (input.startsWith("event")) {
                        HandleTaskCommand(store, input, "event");
                    } else if (input.startsWith("delete")) {
                        HandleTaskCommand(store, input, "delete");
                    } else if (!input.equalsIgnoreCase("bye")) {
                        throw new InvalidCommandException("I'm sorry, but I don't know what that means.");
                    }
                } catch (NiniException e) {
                    printLineWithMessage(e.getMessage());
                }
            } while (!input.equalsIgnoreCase("bye"));

        }
    }

    public static void main(String[] args) {
        printLineWithMessage(GREETING);
        chat();
        printLineWithMessage(GOODBYE);
    }
}
