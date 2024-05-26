package boardgame.view;

import boardgame.controller.BoardGameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class BoardGameApplication extends Application {
    private static Stage primaryStage;
    private static String player1Name;
    private static String player2Name;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        showPlayerSetup();
    }

    public static void showPlayerSetup() throws IOException {
        Parent root = FXMLLoader.load(BoardGameApplication.class.getResource("/fxml/PlayerSetupUI.fxml"));
        primaryStage.setTitle("Player Setup");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

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

    public static void setPlayerNames(String player1, String player2) {
        player1Name = player1;
        player2Name = player2;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
