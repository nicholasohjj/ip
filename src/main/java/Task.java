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
        String[] parts = data.split("\\|");

        if (parts.length < 3) return null;

        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        switch (type) {
            case "T":
                ToDoTask todo = new ToDoTask(description);
                if (isDone) todo.markAsDone();
                return todo;
            case "D":
                if (parts.length < 4) return null;
                DeadlineTask deadline = new DeadlineTask(description, parts[3]);
                if (isDone) deadline.markAsDone();
                return deadline;
            case "E":
                if (parts.length < 5) return null;
                EventTask event = new EventTask(description, parts[3], parts[4]);
                if (isDone) event.markAsDone();
                return event;
            default:
                return null;
        }
    }


    @Override
    public String toString() {
        return String.format("[%s] %s", isDone ? "X" : " ", description);
    }
}
