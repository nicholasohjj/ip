import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import commands.Command;
import components.Parser;
import components.Storage;
import components.TaskList;
import components.Ui;

import exceptions.NiniException;
import tasks.Task;

public class NiniNana {

    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;
    private final Parser parser;
    private final Scanner scanner;

    public NiniNana(Ui ui, Storage storage, Parser parser, Scanner scanner) {
        this.ui = ui;
        this.storage = storage;
        this.parser = parser;
        this.scanner = scanner;

        List<Task> tasks;
        try {
            tasks = storage.loadTasks();
        } catch (IOException | NiniException e) {
            ui.showError("Error loading tasks: " + e.getMessage());
            tasks = new ArrayList<>(); // Provide an empty list if loading fails
        }

        this.taskList = new TaskList(tasks);
    }

    public NiniNana() {
        this(new Ui(), new Storage(), new Parser(), new Scanner(System.in));
    }

    public void run() {
        ui.showGreeting();

        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    Command command = parser.parseCommand(input);
                    command.execute(taskList, ui, storage);
                    if (command.isExit()) {
                        break;
                    }
                }
            } catch (NiniException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new NiniNana().run();
    }
}
