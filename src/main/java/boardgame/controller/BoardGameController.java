package boardgame.controller;

import boardgame.model.GameResult;
import boardgame.model.Position;
import boardgame.model.StoneGameBoard;

import game.State;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.tinylog.Logger;
import util.JacksonHelper;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the board game application.
 * Handles user interactions and updates the game state accordingly.
 */
public class BoardGameController {
    @FXML
    private GridPane board;
    @FXML
    private TextField currentPlayerField;

    private String player1Name;
    private String player2Name;
    private StoneGameBoard model = new StoneGameBoard();
    private Position fromPosition = null;
    private Position toPosition = null;
    private int numberOfTurns;
    private List<GameResult> gameResults = new ArrayList<>();


    @FXML
    private void initialize() {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
        updateCurrentTurn();
    }

    /**
     * Sets the names of the players.
     *
     * @param player1 Name of player 1.
     * @param player2 Name of player 2.
     */
    public void setPlayerNames(String player1, String player2) {
        this.player1Name = player1;
        this.player2Name = player2;
        updateCurrentTurn();
    }

    private StackPane createSquare(int row, int col) {
        StackPane square = new StackPane();
        square.getStyleClass().add("square");

        Circle piece = new Circle(50);
        piece.fillProperty().bind(
                Bindings.when(model.getCellProperty(row, col))
                        .then(Color.BLACK)
                        .otherwise(Color.TRANSPARENT)
        );

        square.getChildren().add(piece);
        square.setOnMouseClicked(event -> handleSquareClick(row, col));

        return square;
    }

    // Handles the click event on a square in the game board.
    private void handleSquareClick(int row, int col) {
        Position clickedPosition = new Position(row, col);

        if (fromPosition == null) {
            if (model.isLegalToMoveFrom(clickedPosition)) {
                fromPosition = clickedPosition;
                Logger.info("Selected from position: " + fromPosition);
            } else {
                Logger.warn("Attempted to select an illegal from position: " + clickedPosition);
            }
        } else {
            toPosition = clickedPosition;
            Logger.info("Selected to position: " + toPosition);

            if (model.isLegalMove(fromPosition, toPosition)) {
                model.makeMove(fromPosition, toPosition);
                Logger.info("Move made from " + fromPosition + " to " + toPosition);
                fromPosition = null;
                toPosition = null;
                updateCurrentTurn();
                numberOfTurns++;


                if (model.isGameOver()) {
                    State.Player winner = model.getNextPlayer().opponent();
                    showAlert("Game Over", "Player " + (winner == State.Player.PLAYER_1 ? player1Name : player2Name) + " wins!");
                    handleGameOver();

                }
            } else {
                showAlert("Invalid Move", "The selected move is not valid.");
                fromPosition = null;
                toPosition = null;
            }
        }
    }

    private void updateCurrentTurn() {
        State.Player currentPlayer = model.getNextPlayer();
        currentPlayerField.setText(currentPlayer == State.Player.PLAYER_1 ? player1Name : player2Name);
    }

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    /**
     * Constructs a new BoardGameController instance and loads existing game results from JSON.
     */
    public BoardGameController() {
        loadGameResultsFromJson();
    }

    private void loadGameResultsFromJson() {
        try (InputStream in = new FileInputStream("game_results.json")) {
            gameResults = JacksonHelper.readList(in, GameResult.class);
            Logger.info("Loaded existing game results from game_results.json");
        } catch (IOException e) {
            gameResults = new ArrayList<>();
            Logger.error("Error loading game results from file: " + e.getMessage());
        }
    }

    private void handleGameOver() {
        State.Player winner = model.getNextPlayer().opponent();
        String winnerName = (winner == State.Player.PLAYER_1) ? player1Name : player2Name;
        GameResult result = new GameResult(LocalDateTime.now(), player1Name, player2Name, numberOfTurns, winnerName);
        gameResults.add(result);

        showAlert("Game Over", "Player " + winnerName + " wins!");

        saveGameResultsToJson();
    }

    private void saveGameResultsToJson() {
        try (OutputStream out = new FileOutputStream("game_results.json")) {
            JacksonHelper.writeList(out, gameResults);
            Logger.info("Game results saved to game_results.json");
        } catch (IOException e) {
            Logger.error("Error saving game results to file: " + e.getMessage());
        }
    }


}