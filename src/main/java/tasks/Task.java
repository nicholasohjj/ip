package tasks;

import exceptions.InvalidDataException;
import exceptions.InvalidFormatException;
import exceptions.NiniException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Task {

    private static final int TYPE_INDEX = 0;
    private static final int DONE_INDEX = 1;
    private static final int DESCRIPTION_INDEX = 2;
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public Task(String description, boolean isDone) {
        this(description);
        this.isDone = isDone;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void markAsDone() {
        if (this.isDone) {
            throw new IllegalStateException("Task is already marked as done");
        } else {
            this.isDone = true;
        }
    }

    public void unmark() {
        if (!this.isDone) {
            throw new IllegalStateException("Task is already unmarked.");
        } else {
            this.isDone = false;
        }
    }

    public String serialize() {
        return "";
    }

    public static Task deserialize(String data) throws NiniException {
        if (data == null || data.isEmpty()) {
            throw new InvalidDataException("Cannot deserialize null or empty data.");
        }
        String[] parts = data.split("\\|");

        if (parts.length < 3) {
            throw new InvalidDataException("Incomplete data for task deserialization.");
        }

        if (!parts[DONE_INDEX].equals("1") && !parts[DONE_INDEX].equals("0")) {
            throw new InvalidDataException("Data has invalid values for is_done status");
        }

        String type = parts[TYPE_INDEX].trim();
        boolean isDone = parts[DONE_INDEX].trim().equals("1");
        String description = parts[DESCRIPTION_INDEX].trim();

        switch (type) {
        case "T":
            return new ToDoTask(description, isDone);
        case "D":
            if (parts.length < 4) {
                throw new InvalidDataException("Missing deadline information.");
            }
            String deadlineStr = parts[3].trim();
            return new DeadlineTask(description, deadlineStr, isDone);
        case "E":
            if (parts.length < 5) {
                throw new InvalidDataException("Missing event start or end time.");
            }
            String fromStr = parts[3].trim();
            String toStr = parts[4].trim();
            return new EventTask(description, fromStr, toStr, isDone);
        default:
            throw new InvalidDataException("Unknown task type.");
        }
    }


    @Override
    public String toString() {
        return String.format("[%s] %s", isDone ? "X" : " ", description);
    }
}
