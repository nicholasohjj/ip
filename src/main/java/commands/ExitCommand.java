package commands;

import components.ContactList;
import components.ContactStorage;
import components.TaskList;
import components.TaskStorage;

/**
 * Represents a command to exit the program.
 * This command displays a goodbye message and signals the application to terminate.
 */
public class ExitCommand extends Command {

    private static final String GOODBYE_MESSAGE = "Bye. Hope to see you again soon!";

    /**
     * Executes the exit command.
     * Displays a goodbye message to the user.
     *
     * @param taskList The task list (not modified by this command).
     * @param taskStorage  The storage component (not used by this command).
     * @return         The goodbye message
     */
    @Override
    public String execute(TaskList taskList, ContactList contactList,
                          TaskStorage taskStorage, ContactStorage contactStorage) {
        return GOODBYE_MESSAGE;
    }

    /**
     * Indicates that this command will terminate the program.
     *
     * @return {@code true}, indicating that this command triggers an exit.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
