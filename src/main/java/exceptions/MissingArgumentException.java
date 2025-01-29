package exceptions;

/**
 * Represents an exception that is thrown when a required argument is missing.
 * This exception extends {@code NiniException} and provides a specific error message
 * for missing arguments.
 */
public class MissingArgumentException extends NiniException {

    /**
     * Constructs a new {@code MissingArgumentException} with the specified error message.
     * The message is prefixed with "MISSING ARGUMENT!" to indicate the nature of the error.
     *
     * @param message The error message describing the missing argument.
     */
    public MissingArgumentException(String message) {
        super("MISSING ARGUMENT! " + message);
    }
}
