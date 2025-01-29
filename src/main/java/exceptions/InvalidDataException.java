package exceptions;

/**
 * Represents an exception that is thrown when invalid data is encountered.
 * This exception extends {@code NiniException} and provides a specific error message
 * for data-related issues.
 */
public class InvalidDataException extends NiniException {

    /**
     * Constructs a new {@code InvalidDataException} with the specified error message.
     * The message is prefixed with "INVALID DATA!" to indicate the nature of the error.
     *
     * @param message The error message describing the invalid data issue.
     */
    public InvalidDataException(String message) {
        super("INVALID DATA! " + message);
    }
}
