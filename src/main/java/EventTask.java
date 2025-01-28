import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EventTask extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");

    public EventTask(String description, String from, String to) {
        super(description);
        try {
            this.from = LocalDateTime.parse(from.trim(), INPUT_FORMATTER);
            this.to = LocalDateTime.parse(to.trim(), INPUT_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use: d/M/yyyy HHmm (e.g., 2/12/2019 1800)");
        }
    }

    public LocalDateTime getStartDateTime() {
        return this.from;
    }

    @Override
    public String serialize() {
        return String.format("E|%d|%s|%s|%s", isDone ? 1 : 0, description, from.format(INPUT_FORMATTER), to.format(INPUT_FORMATTER));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " ( from: " + from.format(OUTPUT_FORMATTER) + " to: " + to.format(OUTPUT_FORMATTER) + ")";
    }
}
