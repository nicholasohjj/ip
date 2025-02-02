import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The main class for the main.NiniNana task management application.
 * It handles user input, processes commands, and manages tasks.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Nininana");
            stage.setResizable(true);
            stage.setMinHeight(600.0);
            stage.setMinWidth(400.0);

            scene.getStylesheets().add(Objects.requireNonNull(getClass()
                    .getResource("/css/main.css")).toExternalForm());

            MainWindow controller = fxmlLoader.getController();
            controller.showGreetingUI();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
