package commands;

import java.io.IOException;

import components.Storage;
import components.TaskList;
import components.Ui;
import tasks.Task;

/**
 * Represents a command to add a task to the task list.
 * This command updates the task list, notifies the user, and saves the task to storage.
 */
public class AddCommand extends Command {

    private final Task task;

    /**
     * Constructs an {@code AddCommand} with the specified task.
     *
     * @param task The task to be added.
     */
    public AddCommand(Task task) {
        assert task != null : "task can't be null";
        this.task = task;
    }

    /**
     * Executes the add task command.
     * Adds the task to the task list, displays a confirmation message to the user,
     * and saves the task to storage.
     *
     * @param taskList The task list to which the task is added.
     * @param ui       The user interface for displaying messages.
     * @param storage  The storage component responsible for saving tasks.
     * @return
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) {
        assert taskList != null : "Task list cannot be null";
        assert ui != null : "UI cannot be null";
        assert storage != null : "Storage cannot be null";

        int initialSize = taskList.size();
        taskList.addTask(task);
        assert taskList.size() == initialSize + 1 : "Task list size should increase by 1";

        String confirmationMessage = ui.showTaskAdded(task, taskList.size());
        try {
            storage.saveTask(task);
        } catch (IOException e) {
            return e.getMessage();
        }
        return confirmationMessage;
    }

    /**
     * Returns the task associated with this command.
     *
     * @return The task that was added.
     */
    public Task getAddedTask() {
        return this.task;
    }
}
