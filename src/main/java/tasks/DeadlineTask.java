package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import exceptions.InvalidFormatException;
import exceptions.NiniException;

/**
 * Represents a deadline task with a specific due date and time.
 * This class extends {@code Task} and includes functionality to store,
 * validate, and format the deadline.
 */
public class DeadlineTask extends Task {

    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm", Locale.ENGLISH);
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma", Locale.ENGLISH);
    private final LocalDateTime deadline;

    /**
     * Constructs a new {@code DeadlineTask} with the given description and deadline.
     * The task is initially marked as not done.
     *
     * @param description The description of the deadline task.
     * @param deadline    The deadline of the task in the format {@code d/M/yyyy HHmm}.
     * @throws NiniException If the provided date-time format is invalid.
     */
    public DeadlineTask(String description, String deadline) throws NiniException {
        super(description);
        this.deadline = parseDeadline(deadline);
    }

    /**
     * Constructs a new {@code DeadlineTask} with the given description, deadline, and completion status.
     *
     * @param description The description of the deadline task.
     * @param deadline    The deadline of the task in the format {@code d/M/yyyy HHmm}.
     * @param isDone      The completion status of the task.
     *                    {@code true} if the task is completed, {@code false} otherwise.
     * @throws NiniException If the provided date-time format is invalid.
     */
    public DeadlineTask(String description, String deadline, boolean isDone) throws NiniException {
        super(description, isDone);
        this.deadline = parseDeadline(deadline);
    }

    private LocalDateTime parseDeadline(String deadline) throws InvalidFormatException {
        try {
            return LocalDateTime.parse(deadline.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidFormatException("Invalid deadline format. Please use the format: "
                    + "d/M/yyyy HHmm (e.g., 25/12/2025 1800)");
        }
    }

    /**
     * Returns the deadline date and time of the task.
     *
     * @return The {@code LocalDateTime} representing the task's deadline.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Serializes the deadline task into a formatted string representation.
     * The format used is: {@code D|<status>|<description>|<deadline>}, where:
     * <ul>
     *     <li>{@code D} represents a deadline task.</li>
     *     <li>{@code <status>} is {@code 1} if the task is done, otherwise {@code 0}.</li>
     *     <li>{@code <description>} is the textual description of the task.</li>
     *     <li>{@code <deadline>} is the formatted deadline date and time.</li>
     * </ul>
     *
     * @return A serialized string representation of the deadline task.
     */
    @Override
    public String serialize() {
        int isDoneValue = isDone ? 1 : 0;
        return String.format("D|%d|%s|%s",
                isDoneValue,
                description,
                deadline.format(INPUT_FORMATTER)
        );
    }

    /**
     * Returns a string representation of the deadline task.
     * The format is {@code [D]<description> (deadline: <deadline time>)}, where:
     * <ul>
     *     <li>{@code [D]} signifies a deadline task.</li>
     *     <li>{@code <deadline time>} is formatted using {@code MMM dd yyyy, h:mma}.</li>
     * </ul>
     *
     * @return A formatted string representing the deadline task.
     */
    @Override
    public String toString() {
        return String.format("[D]%s (deadline: %s)",
                super.toString(),
                deadline.format(OUTPUT_FORMATTER)
        );
    }

    @Override
    public LocalDateTime getRelevantDate() {
        return deadline;
    }

}
