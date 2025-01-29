package tasks;

import exceptions.InvalidFormatException;
import exceptions.NiniException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EventTask extends Task {

    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    private final LocalDateTime from;
    private final LocalDateTime to;

    public EventTask(String description, String from, String to) throws NiniException {
        super(description);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
        validateDateOrder();
    }

    public EventTask(String description, String from, String to, boolean isDone) throws NiniException {
        super(description, isDone);
        this.from = parseDateTime(from);
        this.to = parseDateTime(to);
        validateDateOrder();
    }

    private LocalDateTime parseDateTime(String dateTime) throws InvalidFormatException {
        try {
            return LocalDateTime.parse(dateTime.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidFormatException("Invalid deadline format. Please use the format: d/M/yyyy HHmm (e.g., 25/12/2025 1800)");
        }
    }

    private void validateDateOrder() throws InvalidFormatException {
        if (this.from.isAfter(this.to)) {
            throw new InvalidFormatException("The start time must be earlier than the end time.");
        }
    }

    public LocalDateTime getStartDateTime() {
        return this.from;
    }

    public LocalDateTime getEndDateTime() {
        return this.to;
    }

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

    @Override
    public String toString() {
        return String.format("[E]%s (from: %s to: %s)",
                super.toString(),
                from.format(OUTPUT_FORMATTER),
                to.format(OUTPUT_FORMATTER)
        );
    }
}
