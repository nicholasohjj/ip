package ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import commands.Command;
import components.Contact;
import components.ContactList;
import components.ContactStorage;
import components.Parser;
import components.TaskList;
import components.TaskStorage;
import exceptions.NiniException;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import tasks.Task;

/**
 * Controller for the main window of the application.
 * Handles user input, manages UI elements, and interacts with core application logic.
 */
public class MainWindow extends AnchorPane {

    private static final String GREETING_MESSAGE = "Hello! I'm NiniNana\nWhat can I do for you?";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private TaskStorage taskStorage;
    private Parser parser;
    private TaskList taskList;
    private ContactList contactList;
    private ContactStorage contactStorage;

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

        taskStorage = new TaskStorage();
        contactStorage = new ContactStorage();
        parser = new Parser();

        userImage = loadImage("/images/user_image.jpg", "User image");
        botImage = loadImage("/images/bot_image.jpg", "Bot image");

        setupTaskList();
        setupContactList();
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
     * Loads tasks from taskStorage and initializes the task list.
     * If loading fails, an empty task list is created instead.
     */
    private void setupTaskList() {
        List<Task> tasks;
        try {
            tasks = taskStorage.loadTasks();
        } catch (IOException | NiniException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
            tasks = new ArrayList<>(); // Provide an empty list if loading fails
            Platform.runLater(() -> showErrorUI("Failed to load tasks. Starting with an empty list."));
        }

        assert tasks != null : "Tasks should be properly initialized";
        this.taskList = new TaskList(tasks);
    }

    /**
     * Loads tasks from contactStorage and initializes the task list.
     * If loading fails, an empty contact list is created instead.
     */
    private void setupContactList() {
        List<Contact> contacts;
        try {
            contacts = contactStorage.loadContacts();
        } catch (IOException | NiniException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
            contacts = new ArrayList<>(); // Provide an empty list if loading fails
            Platform.runLater(() -> showErrorUI("Failed to load contacts. Starting with an empty list."));
        }

        assert contacts != null : "Contacts should be properly initialized";
        this.contactList = new ContactList(contacts);
    }


    /**
     * Displays an error message in the UI.
     *
     * @param message The error message to display.
     */
    private void showErrorUI(String message) {
        Platform.runLater(() -> dialogContainer.getChildren().add(
                DialogBox.getErrorDialog(message, botImage)
        ));

        // Shake animation for invalid input
        animateErrorInput();
    }

    /**
     * Displays the greeting message in the UI when the application starts.
     */
    public void showGreetingUI() {
        dialogContainer.getChildren().add(DialogBox.getBotDialog(GREETING_MESSAGE, botImage));
    }

    /**
     * Handles user input when the send button is pressed.
     * Parses the input, executes the command, and updates the UI with responses.
     */
    @FXML
    private void handleUserInput() {
        String userText = userInput.getText().trim();


        if (userText.isEmpty()) {
            animateErrorInput();
            return;
        }

        String responseText;
        try {
            Command command = parser.parseCommand(userText);
            assert command != null : "Command should be properly initialized";

            responseText = command.execute(taskList, contactList, taskStorage, contactStorage);
            assert responseText != null : "Response text should be properly initialized";

            if (command.isExit()) {
                System.exit(0);
                return;
            }
        } catch (NiniException e) {
            responseText = e.getMessage();
            assert responseText != null : "Exception message should not be null";
            showErrorUI(responseText);
            return;
        }

        updateUI(userText, responseText);
        userInput.clear();
    }

    /**
     * Animates the input field when an invalid command is entered.
     */
    private void animateErrorInput() {
        userInput.setStyle("-fx-border-color: red; -fx-background-color: #ffcccc;");

        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(event -> userInput.setStyle(""));
        pause.play();
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
