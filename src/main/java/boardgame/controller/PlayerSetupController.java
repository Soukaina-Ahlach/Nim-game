package boardgame.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import org.tinylog.Logger;
import java.io.IOException;

/**
 * Controller class responsible for handling player setup interactions.
 */
public class PlayerSetupController {
    @FXML
    private TextField player1Field;
    @FXML
    private TextField player2Field;


    @FXML
    private void switchSceneToGame(ActionEvent event) throws IOException {
        String player1Name = player1Field.getText();
        String player2Name = player2Field.getText();

        if (player1Name.isEmpty() || player2Name.isEmpty()) {
            showAlert("Error", "Please fill in both player names!");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ui.fxml"));
        Parent root = loader.load();

        BoardGameController controller = loader.getController();
        controller.setPlayerNames(player1Name, player2Name);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleExit() {
        Logger.info("Exiting...");
        Platform.exit();
    }

    // Private method for showing alert
    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }


}
