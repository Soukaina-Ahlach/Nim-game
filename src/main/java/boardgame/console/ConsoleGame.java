package boardgame.console;

import boardgame.model.StoneGameBoard;
import boardgame.model.Position;
import game.console.TwoPhaseMoveGame;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleGame {


    public static void main(String[] args) {
        var state = new StoneGameBoard();
        var game = new TwoPhaseMoveGame<Position>(state, ConsoleGame::parseMove);
        game.start();
    }

    public static Position parseMove(String input) {
        input = input.trim();

        String pattern = "\\d{1,2}\\s*\\d{1,2}";

        if (!input.matches(pattern)) {
            throw new IllegalArgumentException("Invalid input format. Please provide positions in the format: [row] [col]");
        }

        try (Scanner scanner = new Scanner(input)) {
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            return new Position(row, col);
        } catch (NumberFormatException | NoSuchElementException e) {
            throw new IllegalArgumentException("Invalid input format. Please provide positions in the format: [row] [col]");
        }
    }


}

