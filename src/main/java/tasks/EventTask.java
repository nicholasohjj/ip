package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import exceptions.InvalidFormatException;
import exceptions.NiniException;

/**
 * Represents an event task with a specific start and end time.
 * This class extends {@code Task} and includes functionality to store,
 * validate, and format event times.
 */
public class EventTask extends Task {

    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm", Locale.ENGLISH);
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma", Locale.ENGLISH);
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs a new {@code EventTask} with the given description and event times.
     * The task is initially marked as not done.
     *
     * @param description The description of the event.
     * @param from        The start time of the event in the format {@code d/M/yyyy HHmm}.
     * @param to          The end time of the event in the format {@code d/M/yyyy HHmm}.
     * @throws NiniException If the provided date-time format is invalid or the start time is after the end time.
     */
    public EventTask(String description, String from, String to) throws NiniException {
        super(description);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
        validateDateOrder();
    }

    /**
     * Constructs a new {@code EventTask} with the given description, event times, and completion status.
     *
     * @param description The description of the event.
     * @param from        The start time of the event in the format {@code d/M/yyyy HHmm}.
     * @param to          The end time of the event in the format {@code d/M/yyyy HHmm}.
     * @param isDone      The completion status of the event.
     *                    {@code true} if the event is completed, {@code false} otherwise.
     * @throws NiniException If the provided date-time format is invalid or the start time is after the end time.
     */
    public EventTask(String description, String from, String to, boolean isDone) throws NiniException {
        super(description, isDone);
        try {
            this.from = LocalDateTime.parse(from.trim(), INPUT_FORMATTER);
            this.to = LocalDateTime.parse(to.trim(), INPUT_FORMATTER);
            if (this.from.isAfter(this.to)) {
                throw new InvalidFormatException("The start time must be earlier than the end time.");
            }
        } catch (DateTimeParseException e) {
            throw new InvalidFormatException("Invalid deadline format. Please use the format: "
                    + "d/M/yyyy HHmm (e.g., 25/12/2025 1800)");
        }
    }

    private LocalDateTime parseDateTime(String dateTime) throws InvalidFormatException {
        try {
            return LocalDateTime.parse(dateTime.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidFormatException("Invalid deadline format. Please use the format: "
                    + "d/M/yyyy HHmm (e.g., 25/12/2025 1800)");
        }
    }

    private void validateDateOrder() throws InvalidFormatException {
        if (this.from.isAfter(this.to)) {
            throw new InvalidFormatException("The start time must be earlier than the end time.");
        }
    }

    /**
     * Returns the start date and time of the event.
     *
     * @return The {@code LocalDateTime} representing the event's start time.
     */
    public LocalDateTime getStartDateTime() {
        return from;
    }

    /**
     * Returns the end date and time of the event.
     *
     * @return The {@code LocalDateTime} representing the event's end time.
     */
    public LocalDateTime getEndDateTime() {
        return to;
    }

    /**
     * Serializes the event task into a formatted string representation.
     * The format used is: {@code E|<status>|<description>|<start time>|<end time>}, where:
     * <ul>
     *     <li>{@code E} represents an event task.</li>
     *     <li>{@code <status>} is {@code 1} if the task is done, otherwise {@code 0}.</li>
     *     <li>{@code <description>} is the textual description of the task.</li>
     *     <li>{@code <start time>} is the formatted start date and time of the event.</li>
     *     <li>{@code <end time>} is the formatted end date and time of the event.</li>
     * </ul>
     *
     * @return A serialized string representation of the event task.
     */

    @Override
    public String serialize() {
        int isDoneValue = isDone ? 1 : 0;
        return String.format("E|%d|%s|%s|%s",
                isDoneValue,
                description,
                from.format(INPUT_FORMATTER),
                to.format(INPUT_FORMATTER)
        );
    }

    /**
     * Returns a string representation of the event task.
     * The format is {@code [E]<description> (from: <start time> to: <end time>)}, where:
     * <ul>
     *     <li>{@code [E]} signifies an event task.</li>
     *     <li>{@code <start time>} and {@code <end time>} are formatted using {@code MMM dd yyyy, h:mma}.</li>
     * </ul>
     *
     * @return A formatted string representing the event task.
     */
    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)",
                super.toString(),
                from.format(OUTPUT_FORMATTER),
                to.format(OUTPUT_FORMATTER)
        );
    }

    @Override
    public LocalDateTime getRelevantDate() {
        return from;
    }
}
