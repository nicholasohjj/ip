package commands;

import components.Storage;
import components.TaskList;
import components.Ui;
import exceptions.NiniException;

public class SortCommand extends Command{
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws NiniException {
        taskList.sortTasks();
        Ui.printLineWithMessage("Tasks sorted by date!");
    }
}
