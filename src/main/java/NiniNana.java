import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

import java.util.Scanner;
import java.util.ArrayList;

import exceptions.InvalidCommandException;
import exceptions.NiniException;
import exceptions.MissingArgumentException;
import exceptions.InvalidFormatException;
import exceptions.InvalidTaskNumberException;

public class NiniNana {
    private static final String LINE = "____________________________________________________________";
    private static final String GREETING = " Hello! I'm NiniNana\n What can I do for you?";
    private static final String GOODBYE = "Bye. Hope to see you again soon!";

    private static final String FILENAME = "./data/chat.txt";

    private static void ensureFileDirectoryExists() {
        File directory = new File("./data");
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            if (!isCreated) {
                printLineWithMessage("Failed to create the 'data' directory for saving tasks.");
            }
        }
    }

    private enum Command {
        LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE;

        public static Command fromString(String command) throws InvalidCommandException {
            try {
                return Command.valueOf(command.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new InvalidCommandException("I'm sorry, but I don't know what that means.");
            }
        }
    }

    private static void printLine() {
        System.out.println(LINE);
    }

    private static void printLineWithMessage(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    private static void saveTaskToFile(Task task) {
        ensureFileDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            writer.write(task.serialize() + System.lineSeparator());
        } catch (IOException e) {
            printLineWithMessage("Error saving task to file: " + e.getMessage());
        }
    }

    private static void overwriteFile(ArrayList<Task> store) {
        ensureFileDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Task task : store) {
                writer.write(task.serialize() + System.lineSeparator());
            }
        } catch (IOException e) {
            printLineWithMessage("Error overwriting tasks to file: " + e.getMessage());
        }
    }


    private static ArrayList<Task> loadTasksFromFile() {
        ArrayList<Task> store = new ArrayList<>();
        File file = new File(FILENAME);

        if (!file.exists()) {
            return store;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    Task task = Task.deserialize(line);
                    if (task != null) {
                        store.add(task);
                    } else {
                        printLineWithMessage("Skipped a line due to invalid format: " + line);
                    }
                } catch (Exception e) {
                    printLineWithMessage("Error processing line: \"" + line + "\". Skipping it.");
                }
            }
        } catch (IOException e) {
            printLineWithMessage("Error loading tasks from file: " + e.getMessage());
        }

        return store;
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
            overwriteFile(store);
        } else {
            throw new InvalidTaskNumberException("Invalid task number. Please enter a number between 1 and " + store.size() + ".");
        }
    }

    private static void deleteTask(ArrayList<Task> store, int index) throws InvalidTaskNumberException {
        if (isValidIndex(store, index)) {
            Task removedTask = store.remove(index);
            printLineWithMessage(String.format("Noted. I've removed this task:\n  %s\nNow you have %d tasks in the list.",
                    removedTask, store.size()));
            overwriteFile(store);
        } else {
            throw new InvalidTaskNumberException("Invalid task number. Please enter a number between 1 and " + store.size() + ".");
        }
    }


    private static void unmarkTask(ArrayList<Task> store, int index) throws InvalidTaskNumberException {
        if (isValidIndex(store, index)) {
            store.get(index).unmark();
            printLineWithMessage("OK, I've marked this task as not done yet:\n  " + store.get(index));
            overwriteFile(store);
        } else {
            throw new InvalidTaskNumberException("Invalid task number. Please enter a number between 1 and " + store.size() + ".");
        }
    }

    private static void HandleTaskCommand(ArrayList<Task> store, String input, Command command) {
        try {
            String[] parts = input.split(" ", 2);

            if (parts.length < 2 && (command == Command.MARK || command == Command.UNMARK || command == Command.DELETE)) {
                throw new MissingArgumentException("Please specify the task number. Example: 'mark 2'.");
            }

            if (command == Command.MARK || command == Command.UNMARK || command == Command.DELETE) {
                try {
                    int index = Integer.parseInt(parts[1].trim()) - 1;
                    if (command == Command.MARK) {
                        markTask(store, index);
                    } else if (command == Command.UNMARK) {
                        unmarkTask(store, index);
                    } else {
                        deleteTask(store, index);
                    }

                    return;

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
                case TODO:
                    task = new ToDoTask(details);
                    break;
                case DEADLINE:
                    if (!details.contains("/by")) {
                        throw new InvalidFormatException("Invalid format for deadline. Use: deadline <description> /by <time>");
                    }
                    String[] deadlineParts = details.split("/by", 2);
                    if (deadlineParts.length < 2 || deadlineParts[1].trim().isEmpty()) {
                        throw new InvalidFormatException("Invalid format for deadline. Use: deadline <description> /by <time>");
                    }
                    task = new DeadlineTask(deadlineParts[0].trim(), deadlineParts[1].trim());
                    break;
                case EVENT:
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
            saveTaskToFile(task);
        } catch (Exception e) {
            printLineWithMessage(e.getMessage());
        }
    }

    private static void chat() {
        ArrayList<Task> store = loadTasksFromFile();

        try (Scanner sc = new Scanner(System.in)) {
            String input = null;
            do {
                try {
                    input = sc.nextLine().trim();

                    if (input.equalsIgnoreCase("bye")) {
                        break;
                    }

                    String[] parts = input.split(" ", 2);

                    Command command = Command.fromString(parts[0]);

                    if (command == Command.LIST) {
                        listTasks(store);
                    } else {
                        HandleTaskCommand(store, input, command);
                    }
                } catch (NiniException e) {
                    printLineWithMessage(e.getMessage());
                }
            } while (true);

        }
    }

    public static void main(String[] args) {
        printLineWithMessage(GREETING);
        chat();
        printLineWithMessage(GOODBYE);
    }
}
