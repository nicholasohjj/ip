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
import javafx.application.Platform;
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
        assert scrollPane != null : "ScrollPane should be properly initialized";
        assert dialogContainer != null : "DialogContainer should be properly initialized";
        assert userInput != null : "UserInput should be properly initialized";
        assert sendButton != null : "SendButton should be properly initialized";
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.prefWidthProperty().bind(scrollPane.widthProperty());

        ui = new Ui();
        storage = new Storage();
        parser = new Parser();

        userImage = loadImage("/images/user_image.jpg", "User image");
        botImage = loadImage("/images/bot_image.jpg", "Bot image");

        setupTaskList();
    }

    /**
     * Safely loads an image resource.
     *
     * @param path The path to the image resource.
     * @param description The description for logging purposes.
     * @return The loaded image or a default placeholder if loading fails.
     */
    private Image loadImage(String path, String description) {
        try {
            return new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(path)));
        } catch (NullPointerException e) {
            System.err.println("Error: " + description + " not found at " + path);
            return new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/default.png")));
        }
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
            System.err.println("Error loading tasks: " + e.getMessage());
            tasks = new ArrayList<>(); // Provide an empty list if loading fails
            Platform.runLater(() -> showErrorUI("Failed to load tasks. Starting with an empty list."));
        }

        assert tasks != null : "Tasks should be properly initialized";
        this.taskList = new TaskList(tasks);
    }


    /**
     * Displays an error message in the UI.
     *
     * @param message The error message to display.
     */
    private void showErrorUI(String message) {
        dialogContainer.getChildren().add(DialogBox.getBotDialog(message, botImage));
    }


    /**
     * Displays the greeting message in the UI when the application starts.
     */
    public void showGreetingUI() {
        String welcomeMessage = ui.showGreeting();
        assert welcomeMessage != null : "Welcome message should be properly initialized";

        dialogContainer.getChildren().add(DialogBox.getBotDialog(welcomeMessage, botImage));
    }

    /**
     * Handles user input when the send button is pressed.
     * Parses the input, executes the command, and updates the UI with responses.
     */
    @FXML
    private void handleUserInput() {
        String userText = userInput.getText().trim();
        assert userText != null : "UserInput should be properly initialized";

        if (userText.isEmpty()) {
            return;
        }

        String responseText;
        try {
            Command command = parser.parseCommand(userText);
            assert command != null : "Command should be properly initialized";

            responseText = command.execute(taskList, ui, storage);
            assert responseText != null : "Response text should be properly initialized";

            if (command.isExit()) {
                System.exit(0);
            }
        } catch (NiniException e) {
            responseText = e.getMessage();
            assert responseText != null : "Exception message should not be null";
        }

        updateUI(userText, responseText);
        userInput.clear();
    }

    /**
     * Updates the UI with the user input and the bot's response.
     *
     * @param userText The user's input text.
     * @param responseText The bot's response text.
     */
    private void updateUI(String userText, String responseText) {
        Platform.runLater(() -> dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(userText, userImage),
                DialogBox.getBotDialog(responseText, botImage)
        ));
    }
}
