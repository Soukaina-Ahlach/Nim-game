package boardgame.model;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import game.TwoPhaseMoveState;
import game.State;



public class StoneGameBoard implements TwoPhaseMoveState<Position> {
    public static final int BOARD_SIZE = 4;
    public final BooleanProperty[][] board;

    private State.Player player;
    public StoneGameBoard() {
        board = new BooleanProperty[BOARD_SIZE][BOARD_SIZE];
        // Initially, all cells contain stones
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new ReadOnlyBooleanWrapper(true);
            }
        }
        player = State.Player.PLAYER_1;

    }








}

