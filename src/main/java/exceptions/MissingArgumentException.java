package exceptions;

public class MissingArgumentException extends NiniException {

    public MissingArgumentException(String message) {
        super("MISSING ARGUMENT! " + message);
    }
}
