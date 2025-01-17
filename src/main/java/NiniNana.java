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
			String[] parts = input.split(" ", 2);

			if (command.equals("mark") || command.equals("unmark")) {
				if (parts.length < 2) {
					printLineWithMessage("Please specify the task number.");
					return;
				}
				int index = Integer.parseInt(parts[1].trim()) - 1;
				if (command.equals("mark")) {
					markTask(store, index);
				} else {
					unmarkTask(store, index);
				}
				return;
			}

			if (parts.length < 2) {
				printLineWithMessage("Please specify the task details.");
				return;
			}

			String details = parts[1].trim();
			Task task = null;

			switch (command) {
				case "todo":
					task = new ToDoTask(details);
					break;
				case "deadline":
					if (!details.contains("/by")) {
						printLineWithMessage("Invalid format for deadline. Use: deadline <description> /by <time>");
						return;
					}
					String[] deadlineParts = details.split("/by", 2);
					task = new DeadlineTask(deadlineParts[0].trim(), deadlineParts[1].trim());
					break;
				case "event":
					if (!details.contains("/from") || !details.contains("/to")) {
						printLineWithMessage("Invalid format for event. Use: event <description> /from <start> /to <end>");
						return;
					}
					String[] eventParts = details.split("/from|/to");
					task = new EventTask(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim());
					break;
				default:
					printLineWithMessage("Unknown command.");
					return;
			}

			if (task != null) {
				store.add(task);
				printLineWithMessage(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
						task, store.size()));
			}
		} catch (NumberFormatException e) {
			printLineWithMessage("Invalid input format. Please use a valid task number.");
		} catch (ArrayIndexOutOfBoundsException e) {
			printLineWithMessage("Please specify the task number or task details correctly.");
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
				} else if (input.startsWith("deadline ")) {
					HandleTaskCommand(store, input, "deadline");
				} else if (input.startsWith("todo ")) {
					HandleTaskCommand(store, input, "todo");
				} else if (input.startsWith("event ")) {
					HandleTaskCommand(store, input, "event");
				} else if (!input.equalsIgnoreCase("bye")) {
					if (!input.trim().isEmpty()) {
						Task newTask = new Task(input.trim());
						store.add(newTask);
						printLineWithMessage(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.",
								newTask, store.size()));
					} else {
						printLineWithMessage("Your message is empty!");

					}

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
