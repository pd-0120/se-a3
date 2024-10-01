package cqu.assignmenttwo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 *
 * @author AndresPinilla 12243141
 *
 * This is the main class of the application, it is used to load the fxml files.
 * Extends the JavaFX Application class.
 *
 */
public class App extends Application {

    private static Scene scene;

    /**
     * This section is to set the primary.fxml as the initial screen.
     * Additionally, it sets the default size of all fxml files.
     *
     * @param stage The primary stage for the application.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 1000, 765);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This section is to create a method to load the fxml files. It is used in
     * all the controllers to load the fxml files based on the particular cases.
     *
     * @param fxml The name of the FXML file.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads the FXML file and returns the root Parent.
     *
     * @param fxml The name of the FXML file.
     * @return The root Parent loaded from the FXML file.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * The main method that launches the JavaFX application.
     *
     */
    public static void main(String[] args) {
        launch();
    }
}
