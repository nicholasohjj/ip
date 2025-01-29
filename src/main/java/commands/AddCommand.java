package commands;

import java.io.IOException;

import components.Storage;
import components.TaskList;
import components.Ui;
import exceptions.NiniException;
import tasks.Task;

public class AddCommand extends Command {

    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) {
        taskList.addTask(task);
        ui.showTaskAdded(task, taskList.size());

        try {
            storage.saveTask(task);
        } catch (IOException e) {
            ui.showError(e.getMessage());
        }
    }

    public Task getAddedTask() {
        return this.task;
    }
}
