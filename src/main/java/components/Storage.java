package components;

import exceptions.NiniException;
import tasks.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

import java.util.ArrayList;
import java.util.Scanner;
public class Storage {
    private static final String FILENAME = "./data/chat.txt";

    private static void ensureFileDirectoryExists() {
        File directory = new File("./data");
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            if (!isCreated) {
                Ui.printLineWithMessage("Failed to create the 'data' directory for saving tasks.");
            }
        }
    }

    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(FILENAME);

        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = Task.deserialize(line);
                tasks.add(task);
            }
        } catch (IOException e) {
            Ui.printLineWithMessage("Error loading tasks: " + e.getMessage());
        } catch (NiniException e) {
            Ui.printLineWithMessage(e.getMessage());
        }

        return tasks;
    }

    public void saveTask(Task task) throws NiniException {
        ensureFileDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME, true))) {
            writer.write(task.serialize() + System.lineSeparator());
        } catch (IOException e) {
            throw new NiniException("Error saving task: " + e.getMessage());
        }
    }

    public void overwriteTasks(ArrayList<Task> tasks) throws NiniException {
        ensureFileDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (Task task : tasks) {
                writer.write(task.serialize() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new NiniException("Error overwriting tasks: " + e.getMessage());
        }
    }
}
