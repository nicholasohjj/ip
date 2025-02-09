package tasks;

import java.time.LocalDateTime;

/**
 * Represents a to-do task that does not have any specific date or time.
 * This class extends the {@code Task} class and provides implementation
 * for serialization and string representation specific to to-do tasks.
 */
public class ToDoTask extends Task {

    /**
     * Constructs a new {@code ToDoTask} with the given description.
     * The task is initially marked as not done.
     *
     * @param description The description of the to-do task.
     */
    public ToDoTask(String description) {
        super(description);
        assert description != null && !description.isBlank() : "Description must not be null or empty.";
    }

    /**
     * Constructs a new {@code ToDoTask} with the given description and status.
     *
     * @param description The description of the to-do task.
     * @param isDone      The completion status of the task.
     *                    {@code true} if the task is completed, {@code false} otherwise.
     */
    public ToDoTask(String description, boolean isDone) {
        super(description, isDone);
        assert description != null && !description.isBlank() : "Description must not be null or empty.";
    }

    /**
     * Serializes the to-do task into a formatted string representation.
     * The format used is: {@code T|<status>|<description>}, where:
     * <ul>
     *     <li>{@code T} represents a to-do task.</li>
     *     <li>{@code <status>} is {@code 1} if the task is done, otherwise {@code 0}.</li>
     *     <li>{@code <description>} is the textual description of the task.</li>
     * </ul>
     *
     * @return A serialized string representation of the to-do task.
     */
    @Override
    public String serialize() {
        assert description != null : "Description must not be null";
        return String.format("T|%d|%s", isDone ? 1 : 0, description);
    }

    /**
     * Returns a string representation of the to-do task.
     * The format is {@code [T]<description>}, where {@code [T]} signifies a to-do task.
     *
     * @return A formatted string representing the to-do task.
     */
    @Override
    public String toString() {
        assert description != null : "Description should not be null when generating string representation.";
        return "[T]" + super.toString();
    }

    @Override
    public LocalDateTime getRelevantDate() {
        return null;
    }
}
