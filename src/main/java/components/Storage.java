package components;

import exceptions.NiniException;
import tasks.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private static final String DEFAULT_FILE_PATH = "./data/chat.txt";

    private final String fileName;

    public Storage() {
        this.fileName = DEFAULT_FILE_PATH;
    }

    public Storage(String fileName) {
        this.fileName = fileName;
    }

    private void ensureFileDirectoryExists() throws IOException {
        File directory = new File(new File(fileName).getParent());
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Failed to create the 'data' directory for saving tasks.");
        }
    }


    public List<Task> loadTasks() throws IOException, NiniException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = Task.deserialize(line);
                tasks.add(task);
            }
        }
        return tasks;
    }


    public void saveTask(Task task) throws IOException {
        ensureFileDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(task.serialize() + System.lineSeparator());
        }
    }

    public void overwriteTasks(List<Task> tasks) throws IOException {
        ensureFileDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Task task : tasks) {
                writer.write(task.serialize() + System.lineSeparator());
            }
        }
    }
}
