package components;

import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task removeTask(int index) {
        return tasks.remove(index);
    }

    public Task getTask(int index) {
        return tasks.get(index);
    }

    public void markTask(int index) {
        tasks.get(index).markAsDone();
    }

    public void unmarkTask(int index) {
        tasks.get(index).unmark();
    }

    public int size() {
        return tasks.size();
    }

    public boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    public void sortTasks() {
        tasks.sort((task1, task2) -> {
            LocalDateTime date1 = null;
            LocalDateTime date2 = null;

            if (task1 instanceof DeadlineTask) {
                date1 = ((DeadlineTask) task1).getDeadline();
            } else if (task1 instanceof EventTask) {
                date1 = ((EventTask) task1).getStartDateTime(); // Assume tasks.EventTask has a startDateTime field
            }

            if (task2 instanceof DeadlineTask) {
                date2 = ((DeadlineTask) task2).getDeadline();
            } else if (task2 instanceof EventTask) {
                date2 = ((EventTask) task2).getStartDateTime();
            }

            if (date1 == null && date2 == null) {
                return 0;
            } else if (date1 == null) {
                return 1; // Null dates go to the end
            } else if (date2 == null) {
                return -1; // Null dates go to the end
            } else {
                return date1.compareTo(date2); // Compare the dates
            }
        });
    }



}
