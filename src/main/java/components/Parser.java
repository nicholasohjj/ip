package components;

import commands.*;

import exceptions.InvalidCommandException;
import exceptions.InvalidFormatException;
import exceptions.NiniException;
import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.ToDoTask;

/**
 * Parses user input and returns the corresponding {@code Command} object.
 * This class processes different commands and extracts necessary arguments
 * for task operations such as adding, marking, unmarking, deleting, and sorting tasks.
 */
public class Parser {

    /**
     * Parses the user input and returns the appropriate {@code Command} object.
     *
     * @param input The raw input string entered by the user.
     * @return A {@code Command} object corresponding to the parsed input.
     * @throws NiniException If the input command is invalid or has missing/incorrect arguments.
     */
    public Command parseCommand(String input) throws NiniException {
        String[] parts = input.split(" ", 2);
        String commandType = parts[0].toLowerCase();
        String details = parts.length > 1 ? parts[1].trim() : "";

        switch (commandType) {
            case "list":
                return new ListCommand();
            case "bye":
                return new ExitCommand();
            case "mark":
                try {
                    int markIndex = Integer.parseInt(details) - 1;
                    return new MarkCommand(markIndex);
                } catch (NumberFormatException e) {
                    throw new InvalidFormatException("Invalid integer for task index");
                }
            case "unmark":
                try {
                    int unmarkIndex = Integer.parseInt(details) - 1;
                    return new UnmarkCommand(unmarkIndex);
                } catch (NumberFormatException e) {
                    throw new InvalidFormatException("Invalid integer for task index");
                }
            case "todo":
                if (details.isEmpty()) {
                    throw new InvalidFormatException("Description for todo cannot be empty");
                }
                return new AddCommand(new ToDoTask(details));
            case "event":
                if (details.isEmpty()) {
                    throw new InvalidFormatException("Description cannot be empty for event");
                }
                if (!details.contains("/from") || !details.contains("/to")) {
                    throw new InvalidFormatException("Invalid format for event. Use: event <description> /from <start> /to <end>");
                }
                String[] eventParts = details.split("/from|/to", 3);
                if (eventParts.length < 3) {
                    throw new InvalidFormatException("Invalid format for event. Use: event <description> /from <start> /to <end>");
                }
                if (eventParts[0].trim().isEmpty()) {
                    throw new InvalidFormatException("Description cannot be empty");
                }
                return new AddCommand(new EventTask(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim()));
            case "deadline":
                if (details.isEmpty()) {
                    throw new InvalidFormatException("Description cannot be empty for deadline");
                }
                if (!details.contains("/by")) {
                    throw new InvalidFormatException("Invalid format for deadline. Use: deadline <description> /by <time>");
                }
                String[] deadlineParts = details.split("/by", 2);
                if (deadlineParts.length < 2) {
                    throw new InvalidFormatException("Invalid format for deadline. Use: deadline <description> /by <time>");
                }
                if (deadlineParts[0].trim().isEmpty()) {
                    throw new InvalidFormatException("Description cannot be empty");
                }
                return new AddCommand(new DeadlineTask(deadlineParts[0].trim(), deadlineParts[1].trim()));
            case "sort":
                return new SortCommand();
            case "delete":
                try {
                    int taskIndex = Integer.parseInt(details) - 1;
                    return new DeleteCommand(taskIndex);
                } catch (NumberFormatException e) {
                    throw new InvalidFormatException("Invalid task index. Please enter a valid number.");
                }
            default:
                throw new InvalidCommandException("Unknown command");
        }
    }
}
