
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DialogBox.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        text.setText(message);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox getUserDialog(String message, Image img) {
        DialogBox db = new DialogBox(message, img);
        db.getStyleClass().add("user-dialog");
        return db;
    }

    public static DialogBox getBotDialog(String message, Image img) {
        DialogBox db = new DialogBox(message, img);
        db.flip();
        db.getStyleClass().add("bot-dialog");
        return db;
    }
}
