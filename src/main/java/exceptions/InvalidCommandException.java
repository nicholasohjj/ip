package exceptions;

/**
 * Represents an exception that is thrown when an invalid command is encountered.
 * This exception extends {@code NiniException} and provides a specific error message
 * for invalid commands.
 */
public class InvalidCommandException extends NiniException {

    private static final String DEFAULT_MESSAGE = "INVALID COMMAND!";

    /**
     * Constructs a new {@code InvalidCommandException} with a custom error message.
     * If a message is provided, it is appended to the default error message.
     *
     * @param message The custom error message describing the invalid task number.
     */
    public InvalidCommandException(String message) {
        super(message == null || message.isBlank() ? DEFAULT_MESSAGE : DEFAULT_MESSAGE + " " + message);
    }
}
