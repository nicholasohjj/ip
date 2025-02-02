package components;


import commands.AddCommand;
import commands.Command;
import commands.DeleteCommand;
import commands.ExitCommand;
import commands.FindCommand;
import commands.ListCommand;
import commands.MarkCommand;
import commands.SortCommand;
import commands.UnmarkCommand;
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
            return new MarkCommand(parseIndices(details));
        case "unmark":
            return new UnmarkCommand(parseIndices(details));
        case "todo":
            validateArguments(details, "Description for todo cannot be empty");
            return new AddCommand(new ToDoTask(details));
        case "event":
            return parseEvent(details);
        case "deadline":
            return parseDeadline(details);
        case "sort":
            return new SortCommand();
        case "delete":
            return new DeleteCommand(parseIndices(details));
        case "find":
            return new FindCommand(details);
        default:
            throw new InvalidCommandException("Unknown command");
        }
    }

    private Command parseDeadline(String details) throws NiniException {
        validateArguments(details, "Description cannot be empty for deadline");
        if (!details.contains("/by")) {
            throw new InvalidFormatException("Invalid format for deadline. Use: deadline <description> /by <time>");
        }

        String[] deadlineParts = details.split("/by", 2);
        if (deadlineParts.length < 2) {
            throw new InvalidFormatException("Invalid format for deadline. Use: deadline <description> /by <time>");
        }
        validateArguments(deadlineParts[0].trim(), "Description cannot be empty");

        return new AddCommand(new DeadlineTask(deadlineParts[0].trim(), deadlineParts[1].trim()));
    }


    private int[] parseIndices(String input) throws InvalidFormatException {
        try {
            String[] parts = input.split("\\s+"); // Split input by spaces
            int[] indices = new int[parts.length];

            for (int i = 0; i < parts.length; i++) {
                indices[i] = Integer.parseInt(parts[i]) - 1; // Convert to zero-based index
            }

            return indices;
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Invalid task indices. Please enter valid numbers separated by spaces.");
        }
    }


    private void validateArguments(String input, String... errorMessages) throws InvalidFormatException {
        if (input.isEmpty()) {
            throw new InvalidFormatException(String.join(" ", errorMessages));
        }
    }
    private Command parseEvent(String details) throws NiniException {
        validateArguments(details, "Description cannot be empty for event");
        if (!details.contains("/from") || !details.contains("/to")) {
            throw new InvalidFormatException("Invalid format for event."
                    + " Use: event <description> /from <start> /to <end>");
        }

        String[] eventParts = details.split("/from|/to", 3);
        if (eventParts.length < 3) {
            throw new InvalidFormatException("Invalid format for event."
                    + "Use: event <description> /from <start> /to <end>");
        }
        validateArguments(eventParts[0].trim(), "Description cannot be empty");

        return new AddCommand(new EventTask(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim()));
    }


}
