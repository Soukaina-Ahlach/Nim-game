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

    @Override
    public boolean isLegalToMoveFrom(Position from) {
        return isOnBoard(from) && !isEmpty(from);
    }

    @Override
    public boolean isLegalMove(Position from, Position to) {
        return isOnBoard(from) && isOnBoard(to)
                && !isEmpty(from) && !isEmpty(to)
                && isAdjacent(from, to)
                && isValidNumberOfStonesRemoved(to);
    }

    private boolean isOnBoard(Position p) {
        return isOnBoard(p.row(), p.col());
    }

    private boolean isOnBoard(int row, int col) {
        return 0 <= row && row < BOARD_SIZE && 0 <= col && col < BOARD_SIZE;
    }

    private boolean isEmpty(Position p) {
        return !board[p.row()][p.col()].get();
    }

    private boolean isValidNumberOfStonesRemoved(Position to) {
        return board[to.row()][to.col()].get();
    }

    private boolean isAdjacent(Position from, Position to) {
        return from.row() == to.row() ||  from.col() == to.col();
    }

    @Override
    public void makeMove(Position from, Position to) {
        if (!isLegalMove(from, to)) {
            throw new IllegalArgumentException("Invalid move");
        }

        int stonesToRemove = Math.abs(from.row() - to.row()) + Math.abs(from.col() - to.col()) + 1;
        if (stonesToRemove > 4) {
            throw new IllegalArgumentException("Invalid number of stones to remove");
        }

        if (from.row() == to.row()) {
            int row = from.row();
            int startCol = Math.min(from.col(), to.col());
            for (int i = startCol; i < startCol + stonesToRemove; i++) {
                board[row][i].set(false);
            }
        } else {
            int col = from.col();
            int startRow = Math.min(from.row(), to.row());
            for (int i = startRow; i < startRow + stonesToRemove; i++) {
                board[i][col].set(false);
            }
        }

        player = player.opponent();
    }

    @Override
    public State.Player getNextPlayer() {
        return player;
    }

    @Override
    public boolean isGameOver() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j].get()) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public State.Status getStatus() {
        if (!isGameOver()) {
            return State.Status.IN_PROGRESS;
        }
        return player == State.Player.PLAYER_2 ? State.Status.PLAYER_1_WINS : State.Status.PLAYER_2_WINS;
    }

    @Override
    public boolean isWinner(State.Player player) {
        return player == this.player;
    }


    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                sb.append(board[i][j].get() ? 'O' : '_').append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }



}

