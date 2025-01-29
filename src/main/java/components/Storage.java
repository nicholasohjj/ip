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

/**
 * Handles file storage operations for saving and loading tasks.
 * This class manages reading and writing task data to a file, ensuring
 * persistence of task lists between program runs.
 */
public class Storage {
    private static final String DEFAULT_FILE_PATH = "./data/chat.txt";

    private final String fileName;

    /**
     * Constructs a {@code Storage} object with the default file path {@code ./data/chat.txt}.
     */
    public Storage() {
        fileName = DEFAULT_FILE_PATH;
    }

    /**
     * Constructs a {@code Storage} object with a specified file path.
     *
     * @param fileName The path to the file where tasks will be stored.
     */
    public Storage(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Ensures that the directory for the storage file exists.
     * If the directory does not exist, it attempts to create it.
     * Displays an error message if the directory creation fails.
     */
    private void ensureFileDirectoryExists() throws IOException {
        File directory = new File(new File(fileName).getParent());
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Failed to create the 'data' directory for saving tasks.");
        }
    }

    /**
     * Loads tasks from the storage file.
     * Reads the file line by line, deserializing each line into a {@code Task} object.
     * If the file does not exist, it returns an empty list.
     *
     * @return An {@code ArrayList} of tasks loaded from the file.
     */
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

    /**
     * Saves a single task to the storage file by appending it to the existing file.
     *
     * @param task The task to be saved.
     * @throws NiniException If an error occurs while writing to the file.
     */
    public void saveTask(Task task) throws IOException {
        ensureFileDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(task.serialize() + System.lineSeparator());
        }
    }

    /**
     * Overwrites the storage file with the given list of tasks.
     * This method removes all previous data in the file and writes the new tasks.
     *
     * @param tasks The list of tasks to be saved.
     * @throws NiniException If an error occurs while writing to the file.
     */
    public void overwriteTasks(List<Task> tasks) throws IOException {
        ensureFileDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Task task : tasks) {
                writer.write(task.serialize() + System.lineSeparator());
            }
        }
    }
}
