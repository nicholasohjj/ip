package commands;

import components.Storage;
import components.TaskList;
import components.Ui;
import exceptions.InvalidTaskNumberException;
import exceptions.NiniException;

public class UnmarkCommand extends Command{
    private final int unmarkIndex;

    public UnmarkCommand(int unmarkIndex) {
        this.unmarkIndex = unmarkIndex;
    }
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws NiniException {
        if (taskList.isValidIndex(unmarkIndex)) {
            try {
                taskList.unmarkTask(unmarkIndex);
                Ui.printLineWithMessage("OK, I've marked this task as not done yet:\n  " + taskList.getTask(unmarkIndex));
                storage.overwriteTasks(taskList.getTasks());
            } catch (IllegalStateException e) {
                ui.showError(e.getMessage());
            }
        } else {
            throw new InvalidTaskNumberException("Invalid task number. Please enter a number between 1 and " + taskList.size() + ".");
        }
    }

    public int getTaskIndex() {
        return this.unmarkIndex;
    }
}
