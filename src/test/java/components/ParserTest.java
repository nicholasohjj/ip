package components;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import commands.AddCommand;
import commands.Command;
import commands.DeleteCommand;
import commands.ExitCommand;
import commands.ListCommand;
import commands.MarkCommand;
import commands.SortCommand;
import commands.UnmarkCommand;
import exceptions.InvalidCommandException;
import exceptions.InvalidFormatException;
import exceptions.NiniException;
import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.ToDoTask;

class ParserTest {

    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    // ========================
    // Tests for Simple Commands
    // ========================

    @Test
    void testParseListCommand() throws NiniException {
        assertTrue(parser.parseCommand("list") instanceof ListCommand);
    }

    @Test
    void testParseExitCommand() throws NiniException {
        assertTrue(parser.parseCommand("bye") instanceof ExitCommand);
    }

    @Test
    void testParseSortCommand() throws NiniException {
        assertTrue(parser.parseCommand("sort") instanceof SortCommand);
    }

    // ===========================
    // Tests for Task Modification
    // ===========================

    @Test
    void testParseMarkCommand() throws NiniException {
        Command command = parser.parseCommand("mark 1 3 5");
        assertTrue(command instanceof MarkCommand);
        assertArrayEquals(new int[]{0, 2, 4}, ((MarkCommand) command).getMarkIndices());
    }

    @Test
    void testParseUnmarkCommand() throws NiniException {
        Command command = parser.parseCommand("unmark 2 4");
        assertTrue(command instanceof UnmarkCommand);
        assertArrayEquals(new int[]{1, 3}, ((UnmarkCommand) command).getUnmarkIndices());
    }

    @Test
    void testParseDeleteCommand() throws NiniException {
        Command command = parser.parseCommand("delete 3 5 7");
        assertTrue(command instanceof DeleteCommand);
        assertArrayEquals(new int[]{2, 4, 6}, ((DeleteCommand) command).getDeleteIndices());
    }

    // ======================
    // Tests for Add Commands
    // ======================

    @Test
    void testParseAddToDoCommand() throws NiniException {
        Command command = parser.parseCommand("todo Buy groceries");
        assertTrue(command instanceof AddCommand);
        assertTrue(((AddCommand) command).getAddedTask() instanceof ToDoTask);
        assertEquals("Buy groceries", ((AddCommand) command).getAddedTask().getDescription());
    }

    @Test
    void testParseAddEventCommand() throws NiniException {
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
        Command command = parser.parseCommand("deadline Submit report /by 1/1/2025 1800");
        assertTrue(command instanceof AddCommand);
        assertTrue(((AddCommand) command).getAddedTask() instanceof DeadlineTask);

        DeadlineTask task = (DeadlineTask) ((AddCommand) command).getAddedTask();
        assertEquals("Submit report", task.getDescription());
        assertEquals("2025-01-01T18:00", task.getDeadline().toString());
    }

    // ===========================
    // Tests for Invalid Commands
    // ===========================

    @Test
    void testParseInvalidCommand() {
        assertThrows(InvalidCommandException.class, () -> parser.parseCommand("invalidcommand"));
    }

    @Test
    void testParseMarkCommandWithInvalidIndex() {
        assertThrows(InvalidFormatException.class, () -> parser.parseCommand("mark abc"));
    }

    @Test
    void testParseUnmarkCommandWithInvalidIndex() {
        assertThrows(InvalidFormatException.class, () -> parser.parseCommand("unmark abc"));
    }

    @Test
    void testParseAddToDoCommandWithEmptyDescription() {
        assertThrows(InvalidFormatException.class, () -> parser.parseCommand("todo "));
    }

    @Test
    void testParseAddEventCommandWithInvalidFormat() {
        assertThrows(InvalidFormatException.class, () -> parser.parseCommand("event Meeting /from 10:00"));
    }

    @Test
    void testParseAddDeadlineCommandWithInvalidFormat() {
        assertThrows(InvalidFormatException.class, () -> parser.parseCommand("deadline Submit report"));
    }
}
