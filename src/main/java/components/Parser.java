package components;

import commands.Command;
import commands.ExitCommand;
import commands.contacts.AddContactCommand;
import commands.contacts.DeleteContactCommand;
import commands.contacts.FindContactCommand;
import commands.contacts.ListContactCommand;
import commands.tasks.AddTaskCommand;
import commands.tasks.DeleteTaskCommand;
import commands.tasks.FindTaskCommand;
import commands.tasks.ListTaskCommand;
import commands.tasks.MarkTaskCommand;
import commands.tasks.SortTaskCommand;
import commands.tasks.UnmarkTaskCommand;
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

    private static final String ERROR_INVALID_DEADLINE_FORMAT = "Invalid format for deadline."
            + " Use: deadline <description> /by <time>";
    private static final String ERROR_INVALID_EVENT_FORMAT = "Invalid format for event."
            + " Use: event <description> /from <start> /to <end>";
    private static final String ERROR_EMPTY_DESCRIPTION = "Description cannot be empty";
    private static final String ERROR_INVALID_CONTACT_FORMAT = "Invalid format for adding a contact."
            + " Use: addcontact <name> /p <phone> /e <email>";
    /**
     * Parses the user input and returns the appropriate {@code Command} object.
     *
     * @param input The raw input string entered by the user.
     * @return A {@code Command} object corresponding to the parsed input.
     * @throws NiniException If the input command is invalid or has missing/incorrect arguments.
     */
    public Command parseCommand(String input) throws NiniException {
        assert input != null && !input.isBlank() : "Input command cannot be null or empty";

        String[] parts = input.split(" ", 2);
        String commandType = parts[0].toLowerCase();
        String details = parts.length > 1 ? parts[1].trim() : "";

        switch (commandType) {
        case "listtasks":
            return new ListTaskCommand();
        case "bye":
            return new ExitCommand();
        case "marktasks":
            return new MarkTaskCommand(parseIndices(details));
        case "unmarktasks":
            return new UnmarkTaskCommand(parseIndices(details));
        case "todo":
            return parseTodo(details);
        case "event":
            return parseEvent(details);
        case "deadline":
            return parseDeadline(details);
        case "sorttasks":
            return new SortTaskCommand();
        case "deletetasks":
            return new DeleteTaskCommand(parseIndices(details));
        case "findtasks":
            return new FindTaskCommand(details);
        case "addcontact":
            return parseAddContact(details);
        case "deletecontact":
            return new DeleteContactCommand(parseIndices(details));
        case "listcontacts":
            return new ListContactCommand();
        case "findcontact":
            return new FindContactCommand(details);
        default:
            throw new InvalidCommandException("Unknown command");
        }
    }

    private Command parseTodo(String details) throws NiniException {
        validateNonEmpty(details, "Description for todo cannot be empty");
        return new AddTaskCommand(new ToDoTask(details));
    }

    private Command parseDeadline(String details) throws NiniException {
        validateNonEmpty(details, ERROR_EMPTY_DESCRIPTION);
        String[] deadlineParts = splitDetails(details);

        if (deadlineParts.length < 2 || deadlineParts[1].trim().isEmpty()) {
            throw new InvalidFormatException(ERROR_INVALID_DEADLINE_FORMAT);
        }

        return new AddTaskCommand(new DeadlineTask(deadlineParts[0].trim(), deadlineParts[1].trim()));
    }

    private Command parseEvent(String details) throws NiniException {
        validateNonEmpty(details, ERROR_EMPTY_DESCRIPTION);

        if (!details.contains("/from") || !details.contains("/to")) {
            throw new InvalidFormatException(ERROR_INVALID_EVENT_FORMAT);
        }

        String[] eventParts = details.split("/from|/to", -1);
        if (eventParts.length < 3 || eventParts[0].trim().isEmpty()
                || eventParts[1].trim().isEmpty() || eventParts[2].trim().isEmpty()) {
            throw new InvalidFormatException(ERROR_INVALID_EVENT_FORMAT);
        }

        return new AddTaskCommand(new EventTask(eventParts[0].trim(), eventParts[1].trim(), eventParts[2].trim()));
    }

    private int[] parseIndices(String input) throws InvalidFormatException {
        assert input != null && !input.isBlank() : "Task indices input cannot be null or empty";

        try {
            String[] parts = input.split("\\s+"); // Split input by spaces
            int[] indices = new int[parts.length];

            for (int i = 0; i < parts.length; i++) {
                indices[i] = Integer.parseInt(parts[i]) - 1; // Convert to zero-based index
            }

            return indices;
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Invalid indices. Please enter valid numbers separated by spaces.");
        }
    }

    private Command parseAddContact(String details) throws NiniException {
        validateNonEmpty(details, ERROR_INVALID_CONTACT_FORMAT);

        String[] contactParts = details.split(" /p | /e", -1);

        if (contactParts.length < 3 || contactParts[0].trim().isEmpty()
                || contactParts[1].trim().isEmpty() || contactParts[2].trim().isEmpty()) {
            throw new InvalidFormatException(ERROR_INVALID_CONTACT_FORMAT);
        }

        Contact contact = new Contact(contactParts[0].trim(), contactParts[1].trim(), contactParts[2].trim());
        return new AddContactCommand(contact);
    }

    private void validateNonEmpty(String input, String errorMessage) throws InvalidFormatException {
        if (input.isEmpty()) {
            throw new InvalidFormatException(errorMessage);
        }
    }

    private String[] splitDetails(String details) throws InvalidFormatException {
        String[] parts = details.split("/by", -1);
        if (parts.length < 2 || parts[0].trim().isEmpty()) {
            throw new InvalidFormatException(Parser.ERROR_INVALID_DEADLINE_FORMAT);
        }
        return parts;
    }


}
