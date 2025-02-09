package exceptions;

/**
 * Represents an exception that is thrown when an invalid task number is encountered.
 * This exception extends {@code NiniException} and provides a specific error message
 * for invalid task numbers.
 */
public class InvalidTaskNumberException extends NiniException {

    private static final String DEFAULT_MESSAGE = "INVALID TASK NUMBER! Please provide a valid task number.";

    /**
     * Constructs a new {@code InvalidTaskNumberException} with a custom error message.
     * If a message is provided, it is appended to the default error message.
     *
     * @param message The custom error message describing the invalid task number.
     */
    public InvalidTaskNumberException(String message) {
        super(message == null || message.isBlank() ? DEFAULT_MESSAGE : DEFAULT_MESSAGE + " " + message);
    }
}
