package commands;

import components.ContactList;
import components.ContactStorage;
import components.TaskList;
import components.TaskStorage;
import exceptions.NiniException;

/**
 * Represents an abstract command that can be executed within the task management system.
 * Subclasses should implement the {@code execute} method to define specific command behaviors.
 */
public abstract class Command {

    public abstract String execute(TaskList taskList, ContactList contactList,
                                   TaskStorage taskStorage, ContactStorage contactStorage) throws NiniException;

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
