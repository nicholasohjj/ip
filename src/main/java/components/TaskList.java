package components;

import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public class TaskList {
    
    private final List<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task removeTask(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Error: Invalid task index.");
        }
        return tasks.remove(index);
    }


    public Task getTask(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Error: Invalid task index.");
        }
        return tasks.get(index);
    }

    public void markTask(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Error: Invalid task index.");
        }
        tasks.get(index).markAsDone();
    }

    public void unmarkTask(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("Error: Invalid task index.");
        }
        tasks.get(index).unmark();
    }

    public boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    public void sortTasks() {
        tasks.sort((task1, task2) -> {
            LocalDateTime date1 = getTaskDate(task1);
            LocalDateTime date2 = getTaskDate(task2);

            if (date1 == null && date2 == null) return 0;
            if (date1 == null) return 1;
            if (date2 == null) return -1;
            return date1.compareTo(date2);
        });
    }

    private LocalDateTime getTaskDate(Task task) {
        if (task instanceof DeadlineTask) {
            return ((DeadlineTask) task).getDeadline();
        } else if (task instanceof EventTask) {
            return ((EventTask) task).getStartDateTime();
        }
        return null;
    }



}
