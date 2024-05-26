package boardgame.console;

import boardgame.model.Position;
import game.console.TwoPhaseMoveGame;
import game.TwoPhaseMoveState;
import org.tinylog.Logger;
import boardgame.model.StoneGameBoard;
import java.util.Scanner;
import java.util.function.Function;

public class ConsoleGame extends TwoPhaseMoveGame<Position> {
    private final StoneGameBoard model = new StoneGameBoard();
    private Position fromPosition;
    private final Scanner scanner;

    public ConsoleGame(TwoPhaseMoveState<Position> state, Function<String, Position> parser) {
        super(state, parser);
        this.scanner = new Scanner(System.in);
    }

    @Override
    protected void makeMoveIfPossible(Position move) {
        if (fromPosition == null) {
            fromPosition = move;
            if (model.isEmpty(fromPosition)) {
                Logger.error("Invalid move! The starting position is empty.");
                resetFromPosition();
                printPrompt();
                return;
            }
            Logger.info("Selected from position: {}", fromPosition);
            printPromptForToPosition();
        } else {
            Position toPosition = move;
            if (model.isLegalMove(fromPosition, toPosition)) {
                model.makeMove(fromPosition, toPosition);
                resetFromPosition();
                printState();
                if (model.isGameOver()) {
                    Logger.info("Game Over!");
                    Logger.info("Result: {}", model.getStatus());
                } else {
                    printPrompt();
                }
            } else {
                Logger.error("Invalid move! Please try again.");
                printPromptForToPosition();
            }
        }
    }

    private void resetFromPosition() {
        fromPosition = null;
    }

    @Override
    protected void printPrompt() {
        Logger.info(fromPosition == null ? "Player {}, enter your move from position :" : "Player {}, enter your move to position :", model.getNextPlayer());
    }
    protected void printPromptForToPosition() {
        Logger.info("Player {}, enter your move to position :", model.getNextPlayer());
    }
    @Override
    protected void printState() {
        Logger.info(model.toString());
    }

    @Override
    public void start() {
        printState();
        printPrompt();
        while (!model.isGameOver()) {
            String input = scanner.nextLine();
            Position move = parseMove(input);
            if (move != null) {
                makeMoveIfPossible(move);
            } else {
                Logger.error("Invalid input. Please enter a valid position (row, col).");
                printPrompt();
            }
        }
        scanner.close();
    }

    public static Position parseMove(String input) {
        input = input.trim();
        String pattern = "\\d{1,2}\\s+\\d{1,2}";
        if (!input.matches(pattern)) {
            Logger.error("Invalid input format. Please provide positions in the format: [row] [col]");
            return null;
        }
        String[] parts = input.split("\\s+");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);
        return new Position(row, col);
    }

    public static void main(String[] args) {
        TwoPhaseMoveState<Position> initialState = new StoneGameBoard();
        Function<String, Position> parser = ConsoleGame::parseMove;
        ConsoleGame game = new ConsoleGame(initialState, parser);
        game.start();
    }
}
