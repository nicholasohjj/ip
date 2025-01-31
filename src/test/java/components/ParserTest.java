package components;

import commands.*;
import exceptions.*;
import org.junit.jupiter.api.Test;
import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.ToDoTask;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void testParseListCommand() throws NiniException {
        Parser parser = new Parser();
        Command command = parser.parseCommand("list");
        assertTrue(command instanceof ListCommand);
    }

    @Test
    void testParseExitCommand() throws NiniException {
        Parser parser = new Parser();
        Command command = parser.parseCommand("bye");
        assertTrue(command instanceof ExitCommand);
    }

    @Test
    void testParseMarkCommand() throws NiniException {
        Parser parser = new Parser();
        Command command = parser.parseCommand("mark 1");
        assertTrue(command instanceof MarkCommand);
        assertEquals(0, ((MarkCommand) command).getTaskIndex());
    }

    @Test
    void testParseUnmarkCommand() throws NiniException {
        Parser parser = new Parser();
        Command command = parser.parseCommand("unmark 2");
        assertTrue(command instanceof UnmarkCommand);
        assertEquals(1, ((UnmarkCommand) command).getUnmarkIndex());
    }

    @Test
    void testParseAddToDoCommand() throws NiniException {
        Parser parser = new Parser();
        Command command = parser.parseCommand("todo Buy groceries");
        assertTrue(command instanceof AddCommand);
        assertTrue(((AddCommand) command).getAddedTask() instanceof ToDoTask);
        assertEquals("Buy groceries", ((AddCommand) command).getAddedTask().getDescription());
    }

    @Test
    void testParseAddEventCommand() throws NiniException {
        Parser parser = new Parser();
        Command command = parser.parseCommand("event Meeting /from 1/1/2025 1000 /to 1/1/2025 1200");
        assertTrue(command instanceof AddCommand);
        assertTrue(((AddCommand) command).getAddedTask() instanceof EventTask);
        EventTask task = (EventTask) ((AddCommand) command).getAddedTask();
        assertEquals("Meeting", task.getDescription());
        assertEquals("2025-01-01T10:00", task.getStartDateTime().toString());
        assertEquals("2025-01-01T12:00", task.getEndDateTime().toString());
    }

    @Test
    void testParseAddDeadlineCommand() throws NiniException {
        Parser parser = new Parser();
        Command command = parser.parseCommand("deadline Submit report /by 1/1/2025 1800");
        assertTrue(command instanceof AddCommand);
        assertTrue(((AddCommand) command).getAddedTask() instanceof DeadlineTask);
        DeadlineTask task = (DeadlineTask) ((AddCommand) command).getAddedTask();
        assertEquals("Submit report", task.getDescription());
        assertEquals("2025-01-01T18:00", task.getDeadline().toString());
    }


    @Test
    void testParseSortCommand() throws NiniException {
        Parser parser = new Parser();
        Command command = parser.parseCommand("sort");
        assertTrue(command instanceof SortCommand);
    }

    @Test
    void testParseDeleteCommand() throws NiniException {
        Parser parser = new Parser();
        Command command = parser.parseCommand("delete 3");
        assertTrue(command instanceof DeleteCommand);
        assertEquals(2, ((DeleteCommand) command).getDeleteIndex());
    }

    @Test
    void testParseInvalidCommand() {
        Parser parser = new Parser();
        assertThrows(InvalidCommandException.class, () -> parser.parseCommand("invalidcommand"));
    }

    @Test
    void testParseMarkCommandWithInvalidIndex() {
        Parser parser = new Parser();
        assertThrows(InvalidFormatException.class, () -> parser.parseCommand("mark abc"));
    }

    @Test
    void testParseUnmarkCommandWithInvalidIndex() {
        Parser parser = new Parser();
        assertThrows(InvalidFormatException.class, () -> parser.parseCommand("unmark abc"));
    }

    @Test
    void testParseAddToDoCommandWithEmptyDescription() {
        Parser parser = new Parser();
        assertThrows(InvalidFormatException.class, () -> parser.parseCommand("todo "));
    }

    @Test
    void testParseAddEventCommandWithInvalidFormat() {
        Parser parser = new Parser();
        assertThrows(InvalidFormatException.class, () -> parser.parseCommand("event Meeting /from 10:00"));
    }

    @Test
    void testParseAddDeadlineCommandWithInvalidFormat() {
        Parser parser = new Parser();
        assertThrows(InvalidFormatException.class, () -> parser.parseCommand("deadline Submit report"));
    }
}