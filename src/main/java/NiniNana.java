import java.util.Scanner;

import commands.Command;
import components.Parser;
import components.Storage;
import components.TaskList;
import components.Ui;
import exceptions.NiniException;
import exceptions.InvalidFormatException;
import exceptions.InvalidTaskNumberException;
import tasks.DeadlineTask;
import tasks.EventTask;
import tasks.Task;
import tasks.ToDoTask;

public class NiniNana {
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;
    private final Parser parser;

    private static final String FILENAME = "./data/chat.txt";

    public NiniNana() {
        ui = new Ui();
        storage = new Storage();
        parser = new Parser();
        taskList = new TaskList(storage.loadTasks());
    }

    public void run() {
        ui.showGreeting();
        Scanner scanner = new Scanner(System.in);

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
