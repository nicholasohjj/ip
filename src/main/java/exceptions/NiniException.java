package exceptions;

/**
 * Represents a custom exception used in the application.
 * This serves as the base exception class for handling application-specific errors.
 */
public class NiniException extends Exception{

    /**
     * Constructs a new {@code NiniException} with the specified error message.
     *
     * @param message The error message describing the exception.
     */
    public NiniException(String message) {
        super(message);
    }
}
