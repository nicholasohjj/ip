package exceptions;

/**
 * Represents an exception that is thrown when an input format is invalid.
 * This exception extends {@code NiniException} and provides a specific error message
 * for format-related errors.
 */
public class InvalidFormatException extends NiniException {

    /**
     * Constructs a new {@code InvalidFormatException} with the specified error message.
     * The message is prefixed with "INVALID FORMAT!" to indicate the nature of the error.
     *
     * @param message The error message describing the invalid format issue.
     */
    public InvalidFormatException(String message) {
        super("INVALID FORMAT! " + message);
    }
}
