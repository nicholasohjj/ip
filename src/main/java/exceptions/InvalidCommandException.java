package exceptions;

public class InvalidCommandException extends NiniException {
    public InvalidCommandException(String message) {
        super("INVALID COMMAND! " + message);
    }
}
