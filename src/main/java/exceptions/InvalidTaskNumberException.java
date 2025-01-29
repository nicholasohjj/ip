package exceptions;

/**
 * Represents an exception that is thrown when an invalid task number is encountered.
 * This exception extends {@code NiniException} and provides a specific error message
 * for invalid task numbers.
 */
public class InvalidTaskNumberException extends NiniException {

    /**
     * Constructs a new {@code InvalidTaskNumberException} with the specified error message.
     * The message is prefixed with "INVALID TASK NUMBER!" to indicate the nature of the error.
     *
     * @param message The error message describing the invalid task number.
     */
    public InvalidTaskNumberException(String message) {
        super("INVALID TASK NUMBER! " + message);
    }
}
