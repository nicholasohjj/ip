package commands;

import java.io.IOException;

import exceptions.InvalidTaskNumberException;
import exceptions.NiniException;

import components.Ui;
import components.TaskList;
import components.Storage;

import tasks.Task;

public class DeleteCommand extends Command {

    private final int taskIndex;

    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws NiniException {
        if (!taskList.isValidIndex(taskIndex)) {
            throw new InvalidTaskNumberException("Invalid task number. Please enter a number between 1 and " + taskList.size() + ".");
        }

        try {
            Task removedTask = taskList.removeTask(taskIndex);
            ui.showTaskRemoved(removedTask, taskList.size());
            storage.overwriteTasks(taskList.getTasks());
        } catch (IOException e) {
            ui.showError("Error saving updated task list: " + e.getMessage());
        }
    }

    public int getDeleteIndex() {
        return this.taskIndex;
    }
}
