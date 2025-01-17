public class Task {
    private String name;
    private boolean isDone;

    public Task (String name) {
        this.name = name;
        this.isDone = false;
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", isDone ? "X" : " ", name);
    }
}
