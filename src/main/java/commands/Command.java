package commands;

import components.Storage;
import components.TaskList;
import components.Ui;
import exceptions.NiniException;

public abstract class Command {
    public abstract void execute(TaskList taskList, Ui ui, Storage storage) throws NiniException;

    public boolean isExit() {
        return false;
    }

}