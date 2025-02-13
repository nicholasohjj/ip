package components;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import exceptions.NiniException;
import tasks.Task;


/**
 * Handles file storage operations for saving and loading tasks.
 * This class manages reading and writing task data to a file, ensuring
 * persistence of task lists between program runs.
 */
public class TaskStorage {
    private static final String DEFAULT_FILE_PATH = "./data/chat.txt";
    private static final String DATA_DIRECTORY = "./data";

    private final String fileName;

    /**
     * Constructs a {@code Storage} object with the default file path {@code ./data/chat.txt}.
     */
    public TaskStorage() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Constructs a {@code Storage} object with a specified file path.
     *
     * @param fileName The path to the file where tasks will be stored.
     */
    public TaskStorage(String fileName) {
        assert fileName != null && !fileName.isBlank() : "File name cannot be null or empty";
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
        File file = new File(fileName);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        List<Task> tasks = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                assert line != null : "Read line should not be null";
                tasks.add(Task.deserialize(line));
            }
        }
        return tasks;
    }

    /**
     * Saves a single task to the storage file by appending it to the existing file.
     *
     * @param task The task to be saved.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void saveTask(Task task) throws IOException {
        assert task != null : "Task cannot be null";
        appendToFile(task.serialize());
    }

    /**
     * Overwrites the storage file with the given list of tasks.
     * This method removes all previous data in the file and writes the new tasks.
     *
     * @param tasks The list of tasks to be saved.
     * @throws IOException If an error occurs while writing to the file.
     */
    public void overwriteTasks(List<Task> tasks) throws IOException {
        assert tasks != null : "Tasks list cannot be null";
        writeToFile(tasks);
    }

    /**
     * Appends a single serialized task to the storage file.
     *
     * @param taskData The serialized task string.
     * @throws IOException If an error occurs while writing to the file.
     */
    private void appendToFile(String taskData) throws IOException {
        ensureFileDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(taskData + System.lineSeparator());
        }
    }

    /**
     * Writes a list of serialized tasks to the storage file, overwriting existing content.
     *
     * @param tasks The list of tasks to be written.
     * @throws IOException If an error occurs while writing to the file.
     */
    private void writeToFile(List<Task> tasks) throws IOException {
        ensureFileDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Task task : tasks) {
                assert task != null : "Task in list cannot be null";
                writer.write(task.serialize() + System.lineSeparator());
            }
        }
    }
}
