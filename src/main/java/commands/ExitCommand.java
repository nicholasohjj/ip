package commands;

import exceptions.NiniException;

import components.Ui;
import components.TaskList;
import components.Storage;

/**
 * Represents a command to exit the program.
 * This command displays a goodbye message and signals the application to terminate.
 */
public class ExitCommand extends Command{

    /**
     * Executes the exit command.
     * Displays a goodbye message to the user.
     *
     * @param taskList The task list (not modified by this command).
     * @param ui       The user interface for displaying messages.
     * @param storage  The storage component (not used by this command).
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showGoodbye();
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
