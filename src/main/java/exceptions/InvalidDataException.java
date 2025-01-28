package exceptions;

public class InvalidDataException extends NiniException {
    public InvalidDataException(String message) {
        super("INVALID DATA! " + message);
    }
}
