package exceptions;

public class InvalidFormatException extends NiniException {

    public InvalidFormatException(String message) {
        super("INVALID FORMAT! " + message);
    }
}
