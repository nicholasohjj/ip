package tasks;

import exceptions.InvalidFormatException;
import exceptions.NiniException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DeadlineTask extends Task {

    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    private final LocalDateTime deadline;

    public DeadlineTask(String description, String deadline) throws NiniException {
        super(description);
        this.deadline = parseDeadline(deadline);
    }

    public DeadlineTask(String description, String deadline, boolean isDone) throws NiniException {
        super(description, isDone);
        this.deadline = parseDeadline(deadline);
    }

    private LocalDateTime parseDeadline(String deadline) throws InvalidFormatException {
        try {
            return LocalDateTime.parse(deadline.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidFormatException("Invalid deadline format. Please use the format: d/M/yyyy HHmm (e.g., 25/12/2025 1800)");
        }
    }

    public LocalDateTime getDeadline() {
        return this.deadline;
    }

    @Override
    public String serialize() {
        int isDoneValue = isDone ? 1 : 0;
        return String.format("D|%d|%s|%s",
                isDoneValue,
                description,
                deadline.format(INPUT_FORMATTER)
        );
    }

    @Override
    public String toString() {
        return String.format("[D]%s (deadline: %s)",
                super.toString(),
                deadline.format(OUTPUT_FORMATTER)
        );
    }

}
