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

    private static void listTasks(ArrayList<Task> store) {
        if (store.isEmpty()) {
            printLineWithMessage("The list is empty");
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

    private static void markTask(ArrayList<Task> store, int index) {
        if (isValidIndex(store, index)) {
            store.get(index).markAsDone();
            printLineWithMessage("Nice! I've marked this task as done:\n  " + store.get(index));
        } else {
            printLineWithMessage("Invalid task number");
        }
    }

    private static void unmarkTask(ArrayList<Task> store, int index) {
        if (isValidIndex(store, index)) {
            store.get(index).unmark();
            printLineWithMessage("OK, I've marked this task as not done yet:\n  " + store.get(index));
        } else {
            printLineWithMessage("Invalid task number");
        }
    }

    private static void HandleTaskCommand(ArrayList<Task> store, String input, String command) {
        try {
            int index = Integer.parseInt(input.split(" ")[1]) - 1;
            if (command.equals("mark")) {
                markTask(store, index);
            } else if (command.equals("unmark")) {
                unmarkTask(store, index);
            }
        } catch (NumberFormatException e) {
            printLineWithMessage("Invalid input format. Please use a valid task number.");
        } catch (ArrayIndexOutOfBoundsException e) {
            printLineWithMessage("Please specify a task number.");
        }
    }

    private static void chat() {
        ArrayList<Task> store = new ArrayList<>();

        try (Scanner sc = new Scanner(System.in)) {
            String input;
            do {
                input = sc.nextLine().trim();

                if (input.equalsIgnoreCase("list")) {
                    listTasks(store);
                } else if (input.startsWith("mark ")) {
                    HandleTaskCommand(store, input, "mark");
                } else if (input.startsWith("unmark ")) {
                    HandleTaskCommand(store, input, "unmark");
                } else if (!input.equalsIgnoreCase("bye")) {
                    store.add(new Task(input));
                    printLineWithMessage("Added: " + input);
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
