package commands;

import components.Storage;
import components.TaskList;
import components.Ui;
import exceptions.InvalidTaskNumberException;
import exceptions.NiniException;

public class MarkCommand extends Command{
    private final int markIndex;

    public MarkCommand(int markIndex) {
        this.markIndex = markIndex;
    }
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws NiniException {
        if (taskList.isValidIndex(markIndex)) {
            try {
                taskList.markTask(markIndex);
                Ui.printLineWithMessage("Nice! I've marked this task as done:\n  " + taskList.getTask(markIndex));
                storage.overwriteTasks(taskList.getTasks());
            } catch (IllegalStateException e) {
                ui.showError(e.getMessage());
            }
        } else {
            throw new InvalidTaskNumberException("Invalid task number. Please enter a number between 1 and " + taskList.size() + ".");
        }
    }

    public int getTaskIndex() {
        return this.markIndex;
    }
}
