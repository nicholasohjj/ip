
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
    private final Scanner scanner;

    public NiniNana(Ui ui, Storage storage, Parser parser, Scanner scanner) {
        this.ui = ui;
        this.storage = storage;
        this.parser = parser;
        this.taskList = new TaskList(storage.loadTasks());
        this.scanner = scanner;
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
