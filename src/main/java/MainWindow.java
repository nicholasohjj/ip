import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import commands.Command;
import components.Parser;
import components.Storage;
import components.TaskList;
import components.Ui;
import exceptions.NiniException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tasks.Task;

/**
 * Controller for the main window of the application.
 * Handles user input, manages UI elements, and interacts with core application logic.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Ui ui;
    private Storage storage;
    private Parser parser;
    private TaskList taskList;

    private Image userImage;
    private Image botImage;

    /**
     * Initializes the main window.
     * Sets up UI bindings and initializes components.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.prefWidthProperty().bind(scrollPane.widthProperty());

        ui = new Ui();
        storage = new Storage();
        parser = new Parser();

        userImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/user_image.jpg")));
        botImage = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/user_image.jpg")));

        setupTaskList();
    }

    /**
     * Loads tasks from storage and initializes the task list.
     * If loading fails, an empty task list is created instead.
     */
    private void setupTaskList() {
        List<Task> tasks;
        try {
            tasks = storage.loadTasks();
        } catch (IOException | NiniException e) {
            ui.showError("Error loading tasks: " + e.getMessage());
            tasks = new ArrayList<>(); // Provide an empty list if loading fails
        }

        this.taskList = new TaskList(tasks);
    }

    /**
     * Displays the greeting message in the UI when the application starts.
     */
    public void showGreetingUI() {
        String welcomeMessage = ui.showGreeting();
        dialogContainer.getChildren().add(DialogBox.getBotDialog(welcomeMessage, botImage));
    }

    /**
     * Handles user input when the send button is pressed.
     * Parses the input, executes the command, and updates the UI with responses.
     */
    @FXML
    private void handleUserInput() {
        String userText = userInput.getText();
        if (userText.isEmpty()) {
            return;
        }

        String responseText;
        try {
            Command command = parser.parseCommand(userText);
            responseText = command.execute(taskList, ui, storage);
            if (command.isExit()) {
                System.exit(0);
            }
        } catch (NiniException e) {
            responseText = e.getMessage();
        }

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getBotDialog(responseText, botImage)
        );

        userInput.clear();
    }
}
