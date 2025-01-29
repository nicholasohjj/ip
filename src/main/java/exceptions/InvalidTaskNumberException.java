package exceptions;

public class InvalidTaskNumberException extends NiniException {

    public InvalidTaskNumberException(String message) {
        super("INVALID TASK NUMBER! " + message);
    }
}
