public class EventTask extends Task {
    private String startDate;
    private String endDate;
    public EventTask(String name, String startDate, String endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " ( from: " + startDate + " to: " + endDate + ")";
    }
}
