package boardgame.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Represents the result of a game session.
 */
@Setter
@Getter
public class GameResult {
    private LocalDateTime startTime;
    private String player1Name;
    private String player2Name;
    private int numberOfTurns;
    private String winnerName;

    /**
     * Default constructor.
     */
    public GameResult() {
    }
    /**
     * Parameterized constructor to initialize the GameResult object with specified values.
     *
     * @param startTime     The start time of the game session.
     * @param player1Name   The name of the first player.
     * @param player2Name   The name of the second player.
     * @param numberOfTurns The number of turns played in the game session.
     * @param winnerName    The name of the winner of the game session.
     */
    public GameResult(LocalDateTime startTime, String player1Name, String player2Name, int numberOfTurns, String winnerName) {
        this.startTime = startTime;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.numberOfTurns = numberOfTurns;
        this.winnerName = winnerName;
    }

}