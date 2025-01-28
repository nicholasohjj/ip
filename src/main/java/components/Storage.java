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
    private final String fileName;

    public Storage() {
        this.fileName = "./data/chat.txt";
    }

    public Storage(String fileName) {
        this.fileName = fileName;
    }



    private void ensureFileDirectoryExists() {
        File directory = new File(new File(fileName).getParent());
        if (!directory.exists()) {
            boolean isCreated = directory.mkdirs();
            if (!isCreated) {
                Ui.printLineWithMessage("Failed to create the 'data' directory for saving tasks.");
            }
        }
    }


    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
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
        } catch (IOException e) {
            Ui.printLineWithMessage("Error loading tasks: " + e.getMessage());
        } catch (NiniException e) {
            Ui.printLineWithMessage(e.getMessage());
        }

        return tasks;
    }

    public void saveTask(Task task) throws NiniException {
        ensureFileDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(task.serialize() + System.lineSeparator());
        } catch (IOException e) {
            throw new NiniException("Error saving task: " + e.getMessage());
        }
    }

    public void overwriteTasks(ArrayList<Task> tasks) throws NiniException {
        ensureFileDirectoryExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Task task : tasks) {
                writer.write(task.serialize() + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new NiniException("Error overwriting tasks: " + e.getMessage());
        }
    }
}
