package commands;

import java.io.IOException;

import components.Storage;
import components.TaskList;
import components.Ui;
import exceptions.InvalidTaskNumberException;
import exceptions.NiniException;

public class MarkCommand extends Command {
    private final int markIndex;

    public MarkCommand(int markIndex) {
        this.markIndex = markIndex;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws NiniException {
        if (!taskList.isValidIndex(markIndex)) {
            throw new InvalidTaskNumberException("Invalid task number. Please enter a number between 1 and " + taskList.size() + ".");
        }

        try {
            taskList.markTask(markIndex);
            ui.printLineWithMessage("Nice! I've marked this task as done:\n  " + taskList.getTask(markIndex));
            storage.overwriteTasks(taskList.getTasks());
        } catch (IllegalStateException e) {
            ui.showError("Error: Task is already marked as done.");
        } catch (IOException e) {
            ui.showError("Error saving updated task list: " + e.getMessage());
        }
    }

    public int getTaskIndex() {
        return this.markIndex;
    }
}
