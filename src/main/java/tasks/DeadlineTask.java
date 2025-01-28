package tasks;

import exceptions.InvalidFormatException;
import exceptions.NiniException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DeadlineTask extends Task {
    private final LocalDateTime deadline;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    public DeadlineTask(String description, String deadline) throws NiniException {
        super(description);
        try {
            this.deadline = LocalDateTime.parse(deadline.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidFormatException("Invalid deadline format. Please use the format: d/M/yyyy HHmm (e.g., 25/12/2025 1800)");
        }
    }

    public DeadlineTask(String description, String deadline, boolean isDone) throws NiniException {
        super(description, isDone);
        try {
            this.deadline = LocalDateTime.parse(deadline.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidFormatException("Invalid deadline format. Please use the format: d/M/yyyy HHmm (e.g., 25/12/2025 1800)");
        }
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    @Override
    public String serialize() {
        return String.format("D|%d|%s|%s", isDone ? 1 : 0, description, deadline.format(INPUT_FORMATTER));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (deadline: " + deadline.format(OUTPUT_FORMATTER) + ")";
    }
}
