package boardgame.view;

import boardgame.controller.BoardGameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Main application class for the board game.
 * Initializes and manages the JavaFX application lifecycle.
 * Handles transitioning between player setup and the game view.
 */
public class BoardGameApplication extends Application {
    private static Stage primaryStage;
    private static String player1Name;
    private static String player2Name;

    /**
     * Starts the JavaFX application by showing the player setup view.
     *
     * @param stage The primary stage of the application.
     * @throws IOException If there is an error loading the player setup view.
     */
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        showPlayerSetup();
    }
    /**
     * Shows the player setup view.
     *
     * @throws IOException If there is an error loading the player setup view.
     */
    public static void showPlayerSetup() throws IOException {
        Parent root = FXMLLoader.load(BoardGameApplication.class.getResource("/fxml/PlayerSetupUI.fxml"));
        primaryStage.setTitle("Player Setup");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    /**
     * Starts the game by switching to the game view.
     */
    public static void startGame() {
        try {
            FXMLLoader loader = new FXMLLoader(BoardGameApplication.class.getResource("/fxml/ui.fxml"));
            Parent root = loader.load();

            // Pass player names to the game controller
            BoardGameController controller = loader.getController();
            controller.setPlayerNames(player1Name, player2Name);

            primaryStage.setTitle("Stone Board Game");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the names of the players.
     *
     * @param player1 Name of player 1.
     * @param player2 Name of player 2.
     */
    public static void setPlayerNames(String player1, String player2) {
        player1Name = player1;
        player2Name = player2;
    }

    /**
     * Entry point for the JavaFX application.
     * Initializes and starts the JavaFX runtime.
     *
     * @param args Command-line arguments (not used in JavaFX applications)
     */
    public static void main(String[] args) {
        launch(args);
    }
}
