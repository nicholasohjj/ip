package exceptions;

/**
 * Represents an exception that is thrown when an invalid command is encountered.
 * This exception extends {@code NiniException} and provides a specific error message
 * for command-related errors.
 */
public class InvalidCommandException extends NiniException {

    /**
     * Constructs a new {@code InvalidCommandException} with the specified error message.
     * The message is prefixed with "INVALID COMMAND!" to indicate the nature of the error.
     *
     * @param message The error message describing the invalid command issue.
     */
    public InvalidCommandException(String message) {
        super("INVALID COMMAND! " + message);
    }
}
