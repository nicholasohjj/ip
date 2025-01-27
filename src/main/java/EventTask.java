public class EventTask extends Task {
    private final String from;
    private final String to;
    public EventTask(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String serialize() {
        return String.format("E|%d|%s|%s|%s", isDone ? 1 : 0, description, from, to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " ( from: " + from + " to: " + to + ")";
    }
}
