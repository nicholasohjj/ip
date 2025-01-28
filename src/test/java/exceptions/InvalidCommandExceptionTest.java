package exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvalidCommandExceptionTest {

    @Test
    void testExceptionMessage() {
        InvalidCommandException exception = new InvalidCommandException("Unknown command");
        assertEquals("INVALID COMMAND! Unknown command", exception.getMessage());
    }

    @Test
    void testExceptionWithDifferentMessage() {
        InvalidCommandException exception = new InvalidCommandException("Command not recognised");
        assertEquals("INVALID COMMAND! Command not recognised", exception.getMessage());
    }
}
