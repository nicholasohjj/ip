package commands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import components.Storage;
import components.TaskList;
import components.Ui;
import exceptions.InvalidTaskNumberException;
import exceptions.NiniException;
import tasks.Task;

public class FindCommand extends Command {

    private final String keyword;


    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws NiniException {
        List<Task> matchingTasks = taskList.getTasks().stream()
                .filter(task -> task.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        if (matchingTasks.isEmpty()) {
            ui.printLineWithMessage("No matching tasks found");
        } else {
            ui.showTaskList((ArrayList<Task>) matchingTasks);
        }


    }
}
