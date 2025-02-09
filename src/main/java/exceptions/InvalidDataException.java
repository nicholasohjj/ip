package exceptions;

/**
 * Represents an exception that is thrown when an invalid data is encountered.
 * This exception extends {@code NiniException} and provides a specific error message
 * for invalid data.
 */
public class InvalidDataException extends NiniException {

    private static final String DEFAULT_MESSAGE = "INVALID FORMAT!.";

    /**
     * Constructs a new {@code InvalidDataException} with a custom error message.
     * If a message is provided, it is appended to the default error message.
     *
     * @param message The custom error message describing the invalid task number.
     */
    public InvalidDataException(String message) {
        super(message == null || message.isBlank() ? DEFAULT_MESSAGE : DEFAULT_MESSAGE + " " + message);
    }
}
