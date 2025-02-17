package components;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import commands.Command;
import commands.ExitCommand;
import commands.tasks.AddTaskCommand;
import commands.tasks.DeleteTaskCommand;
import commands.tasks.ListTaskCommand;
import commands.tasks.MarkTaskCommand;
import commands.tasks.SortTaskCommand;
import commands.tasks.UnmarkTaskCommand;
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

    @Test
    void testParseListCommand() throws NiniException {
        assertTrue(parser.parseCommand("listtasks") instanceof ListTaskCommand);
    }

    @Test
    void testParseExitCommand() throws NiniException {
        assertTrue(parser.parseCommand("bye") instanceof ExitCommand);
    }

    @Test
    void testParseSortCommand() throws NiniException {
        assertTrue(parser.parseCommand("sorttasks") instanceof SortTaskCommand);
    }

    // ===========================
    // Tests for Task Modification
    // ===========================

    @Test
    void testParseMarkCommand() throws NiniException {
        Command command = parser.parseCommand("marktasks 1 3 5");
        assertTrue(command instanceof MarkTaskCommand);
        assertArrayEquals(new int[]{0, 2, 4}, ((MarkTaskCommand) command).getMarkIndices());
    }

    @Test
    void testParseUnmarkCommand() throws NiniException {
        Command command = parser.parseCommand("unmarktasks 2 4");
        assertTrue(command instanceof UnmarkTaskCommand);
        assertArrayEquals(new int[]{1, 3}, ((UnmarkTaskCommand) command).getUnmarkIndices());
    }

    @Test
    void testParseDeleteCommand() throws NiniException {
        Command command = parser.parseCommand("deletetasks 3 5 7");
        assertTrue(command instanceof DeleteTaskCommand);
        assertArrayEquals(new int[]{2, 4, 6}, ((DeleteTaskCommand) command).getDeleteIndices());
    }

    // ======================
    // Tests for Add Commands
    // ======================

    @Test
    void testParseAddToDoCommand() throws NiniException {
        Command command = parser.parseCommand("todo Buy groceries");
        assertTrue(command instanceof AddTaskCommand);
        assertTrue(((AddTaskCommand) command).getAddedTask() instanceof ToDoTask);
        assertEquals("Buy groceries", ((AddTaskCommand) command).getAddedTask().getDescription());
    }

    @Test
    void testParseAddEventCommand() throws NiniException {
        Command command = parser.parseCommand("event Meeting /from 1/1/2025 1000 /to 1/1/2025 1200");
        assertTrue(command instanceof AddTaskCommand);
        assertTrue(((AddTaskCommand) command).getAddedTask() instanceof EventTask);

        EventTask task = (EventTask) ((AddTaskCommand) command).getAddedTask();
        assertEquals("Meeting", task.getDescription());
        assertEquals("2025-01-01T10:00", task.getStartDateTime().toString());
        assertEquals("2025-01-01T12:00", task.getEndDateTime().toString());
    }

    @Test
    void testParseAddDeadlineCommand() throws NiniException {
        Command command = parser.parseCommand("deadline Submit report /by 1/1/2025 1800");
        assertTrue(command instanceof AddTaskCommand);
        assertTrue(((AddTaskCommand) command).getAddedTask() instanceof DeadlineTask);

        DeadlineTask task = (DeadlineTask) ((AddTaskCommand) command).getAddedTask();
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
        assertThrows(InvalidFormatException.class, () -> parser.parseCommand("marktasks abc"));
    }

    @Test
    void testParseUnmarkCommandWithInvalidIndex() {
        assertThrows(InvalidFormatException.class, () -> parser.parseCommand("unmarktasks abc"));
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
