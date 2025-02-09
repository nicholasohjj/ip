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

    private static final String ERROR_STORAGE = "Error saving task to storage: ";
    private static final String ASSERT_TASK_NULL = "Task cannot be null";
    private static final String ASSERT_TASKLIST_NULL = "Task list cannot be null";
    private static final String ASSERT_UI_NULL = "UI cannot be null";
    private static final String ASSERT_STORAGE_NULL = "Storage cannot be null";
    private static final String ASSERT_TASKLIST_SIZE = "Task list size should increase by 1";

    private final Task task;

    /**
     * Constructs an {@code AddCommand} with the specified task.
     *
     * @param task The task to be added.
     */
    public AddCommand(Task task) {
        assert task != null : ASSERT_TASK_NULL;
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
        assert taskList != null : ASSERT_TASKLIST_NULL;
        assert ui != null : ASSERT_UI_NULL;
        assert storage != null : ASSERT_STORAGE_NULL;

        int initialSize = taskList.size();
        taskList.addTask(task);
        assert taskList.size() == initialSize + 1 : ASSERT_TASKLIST_SIZE;

        String confirmationMessage = ui.showTaskAdded(task, taskList.size());
        return saveTaskToStorage(storage, confirmationMessage);
    }

    /**
     * Saves the task to storage and returns an appropriate message.
     *
     * @param storage             The storage component responsible for saving tasks.
     * @param confirmationMessage The confirmation message to return on success.
     * @return The confirmation message if successful, or an error message if saving fails.
     */
    private String saveTaskToStorage(Storage storage, String confirmationMessage) {
        try {
            storage.saveTask(task);
            return confirmationMessage;
        } catch (IOException e) {
            return ERROR_STORAGE + e.getMessage();
        }
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
