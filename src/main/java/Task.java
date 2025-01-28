import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task (String description) {
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public abstract String serialize();

    public static Task deserialize(String data) {
        if (data == null || data.isEmpty()) {
            System.err.println("Error: Cannot deserialize null or empty data.");
            return null;
        }
        String[] parts = data.split("\\|");
        final int TYPE_INDEX = 0;
        final int DONE_INDEX = 1;
        final int DESCRIPTION_INDEX = 2;
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

        if (parts.length < 3) {
            System.err.println("Error: Incomplete data for task deserialization.");
            return null;
        }

        String type = parts[TYPE_INDEX].trim();
        boolean isDone = parts[DONE_INDEX].trim().equals("1");
        String description = parts[DESCRIPTION_INDEX].trim();

        try {
            switch (type) {
                case "T":
                    ToDoTask todo = new ToDoTask(description);
                    if (isDone) todo.markAsDone();
                    return todo;
                case "D":
                    if (parts.length < 4) {
                        System.err.println("Error: Missing deadline information.");
                        return null;
                    }
                    String deadlineStr = parts[3].trim();
                    LocalDateTime deadline = LocalDateTime.parse(deadlineStr, inputFormatter);
                    DeadlineTask deadlineTask = new DeadlineTask(description, deadlineStr);
                    if (isDone) deadlineTask.markAsDone();
                    return deadlineTask;
                case "E":
                    if (parts.length < 5) {
                        System.err.println("Error: Missing event start or end time.");
                        return null;
                    }
                    String fromStr = parts[3].trim();
                    String toStr = parts[4].trim();
                    LocalDateTime from = LocalDateTime.parse(fromStr, inputFormatter);
                    LocalDateTime to = LocalDateTime.parse(toStr, inputFormatter);
                    EventTask event = new EventTask(description, fromStr, toStr);
                    if (isDone) event.markAsDone();
                    return event;
                default:
                    System.err.println("Error: Unknown task type.");
                    return null;
            }
        } catch (DateTimeParseException e) {
            System.err.println("Error: Invalid date/time format during deserialization. " + e.getMessage());
            return null;
        }
    }


    @Override
    public String toString() {
        return String.format("[%s] %s", isDone ? "X" : " ", description);
    }
}
