package tasks;

import java.time.LocalDateTime;

import exceptions.InvalidDataException;
import exceptions.NiniException;

/**
 * Represents a generic task with a description and completion status.
 * This class serves as a base class for different types of tasks such as
 * ToDoTask, DeadlineTask, and EventTask.
 */
public abstract class Task {

    private static final int TYPE_INDEX = 0;
    private static final int DONE_INDEX = 1;
    private static final int DESCRIPTION_INDEX = 2;
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new {@code Task} with the given description.
     * The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        assert description != null && !description.isBlank() : "Task description must not be null or empty.";
        this.description = description;
        isDone = false;
    }

    /**
     * Constructs a new {@code Task} with the given description and completion status.
     *
     * @param description The description of the task.
     * @param isDone      The completion status of the task.
     *                    {@code true} if the task is completed, {@code false} otherwise.
     */
    public Task(String description, boolean isDone) {
        this(description);
        this.isDone = isDone;
    }

    /**
     * Returns the description of the task.
     *
     * @return The task description.
     */
    public String getDescription() {
        assert description != null : "Task description should not be null.";
        return description;
    }

    /**
     * Returns the completion status of the task.
     *
     * @return {@code true} if the task is completed, {@code false} otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks the task as done.
     *
     * @throws IllegalStateException If the task is already marked as done.
     */
    public void markAsDone() {
        if (isDone) {
            throw new IllegalStateException("Task is already marked as done");
        } else {
            isDone = true;
        }
    }


    /**
     * Unmarks the task, setting it as not done.
     *
     * @throws IllegalStateException If the task is already unmarked.
     */
    public void unmark() {
        if (!isDone) {
            throw new IllegalStateException("Task is already unmarked.");
        } else {
            isDone = false;
        }
    }

    /**
     * Serializes the task into a string format.
     * This method is intended to be overridden by subclasses.
     *
     * @return A serialized string representation of the task.
     */
    public abstract String serialize();

    /**
     * Deserializes a given string into a {@code Task} object.
     * The string format should follow the pattern used in the {@code serialize} method.
     *
     * @param data The serialized string representation of a task.
     * @return A {@code Task} object reconstructed from the serialized data.
     * @throws NiniException If the data is invalid, incorrectly formatted, or incomplete.
     */
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

        assert !type.isBlank() : "Task type should not be null or empty.";
        assert !description.isBlank() : "Task description should not be null or empty.";

        switch (type) {
        case "T":
            return new ToDoTask(description, isDone);
        case "D":
            if (parts.length < 4) {
                throw new InvalidDataException("Missing deadline information.");
            }
            String deadlineStr = parts[3].trim();
            assert !deadlineStr.isBlank() : "Deadline should not be null or empty.";
            return new DeadlineTask(description, deadlineStr, isDone);
        case "E":
            if (parts.length < 5) {
                throw new InvalidDataException("Missing event start or end time.");
            }
            String fromStr = parts[3].trim();
            String toStr = parts[4].trim();
            assert !fromStr.isBlank() : "Event start time should not be null or empty.";
            assert !toStr.isBlank() : "Event end time should not be null or empty.";
            return new EventTask(description, fromStr, toStr, isDone);
        default:
            throw new InvalidDataException("Unknown task type.");
        }
    }

    /**
     * Returns a string representation of the task.
     * The format is {@code [<status>] <description>}, where:
     * <ul>
     *     <li>{@code X} indicates the task is completed.</li>
     *     <li>{@code " "} (space) indicates the task is not completed.</li>
     * </ul>
     *
     * @return A formatted string representing the task.
     */
    @Override
    public String toString() {
        assert description != null : "Task description should not be null when calling toString.";
        return String.format("[%s] %s", isDone ? "X" : " ", description);
    }

    public abstract LocalDateTime getRelevantDate();
}
