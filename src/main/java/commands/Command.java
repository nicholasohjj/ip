package commands;

import components.Storage;
import components.TaskList;
import components.Ui;
import exceptions.NiniException;

/**
 * Represents an abstract command that can be executed within the task management system.
 * Subclasses should implement the {@code execute} method to define specific command behaviors.
 */
public abstract class Command {

    /**
     * Executes the command using the provided task list, user interface, and storage components.
     *
     * @param taskList The task list that stores tasks.
     * @param ui       The user interface responsible for displaying messages.
     * @param storage  The storage component that handles saving and loading tasks.
     * @throws NiniException If an error occurs during execution.
     */
    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws NiniException;

    /**
     * Determines whether the command should cause the program to exit.
     * By default, commands do not cause an exit.
     * Subclasses can override this method if needed.
     *
     * @return {@code true} if the command should terminate the program, {@code false} otherwise.
     */
    public boolean isExit() {
        return false;
    }

}
