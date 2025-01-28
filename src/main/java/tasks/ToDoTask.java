package tasks;

import exceptions.NiniException;

public class ToDoTask extends Task {

    public ToDoTask(String description) {
        super(description);
    }

    public ToDoTask(String description, boolean isDone) {
        super(description,isDone);
    }

    @Override
    public String serialize() {
        return String.format("T|%d|%s", isDone ? 1 : 0, description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
