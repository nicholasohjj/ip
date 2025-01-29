package commands;

import exceptions.NiniException;

import components.Ui;
import components.TaskList;
import components.Storage;

public class ListCommand extends Command {

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        ui.showTaskList(taskList.getTasks());
    }
}
