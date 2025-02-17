package ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a chat bubble for user and bot responses.
 */
public class DialogBox extends HBox {

    @FXML
    private Label text;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String message, Image img) {
        assert message != null && !message.isBlank() : "Message cannot be null or empty";
        assert img != null : "Image cannot be null";

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DialogBox.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load ui.DialogBox.fxml", e);
        }

        assert text != null : "FXML 'text' label is not loaded";
        assert displayPicture != null : "FXML 'displayPicture' ImageView is not loaded";

        text.setText(message);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        assert !tmp.isEmpty() : "Dialog box must contain elements before flipping";

        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String message, Image img) {
        assert message != null && !message.isBlank() : "User message cannot be null or empty";
        assert img != null : "User image cannot be null";

        DialogBox db = new DialogBox(message, img);
        db.getStyleClass().add("user-dialog");
        return db;
    }

    public static DialogBox getBotDialog(String message, Image img) {
        assert message != null && !message.isBlank() : "Bot message cannot be null or empty";
        assert img != null : "Bot image cannot be null";

        DialogBox db = new DialogBox(message, img);
        db.flip();
        db.getStyleClass().add("bot-dialog");
        return db;
    }

    /**
     * Creates an error message dialog box.
     *
     * @param message The error message text.
     * @param img The bot's profile image.
     * @return A styled DialogBox for errors.
     */
    public static DialogBox getErrorDialog(String message, Image img) {
        assert message != null && !message.isBlank() : "Error message cannot be null or empty";
        assert img != null : "Bot image for error messages cannot be null";

        DialogBox db = new DialogBox(message, img);
        db.flip();
        db.getStyleClass().add("error-dialog");
        return db;
    }
}
