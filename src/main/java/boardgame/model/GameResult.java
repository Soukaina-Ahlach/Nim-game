package boardgame.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class GameResult {
    private LocalDateTime startTime;
    private String player1Name;
    private String player2Name;
    private int numberOfTurns;
    private String winnerName;

    public GameResult() {
    }

    public GameResult(LocalDateTime startTime, String player1Name, String player2Name, int numberOfTurns, String winnerName) {
        this.startTime = startTime;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.numberOfTurns = numberOfTurns;
        this.winnerName = winnerName;
    }

}