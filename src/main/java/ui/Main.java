package ui;

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
        assert stage != null : "Stage must not be null at the start of the application";
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            assert ap != null : "ui.MainWindow.fxml could not be loaded";

            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Nininana");
            stage.setResizable(true);
            stage.setMinHeight(600.0);
            stage.setMinWidth(400.0);

            String stylesheet = Objects.requireNonNull(getClass()
                    .getResource("/css/main.css")).toExternalForm();
            assert stylesheet != null : "ui.MainWindow.css could not be loaded";

            scene.getStylesheets().add(stylesheet);

            MainWindow controller = fxmlLoader.getController();
            assert controller != null : "ui.MainWindow.fxml could not be loaded";

            controller.showGreetingUI();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
