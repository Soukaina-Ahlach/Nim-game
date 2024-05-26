package boardgame.model;

import boardgame.model.GameResult;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import game.TwoPhaseMoveState;
import game.State;

import java.time.LocalDateTime;
import java.util.function.BiConsumer;

/**
 * Represents the game board for a the game.
 * Implements the TwoPhaseMoveState interface.
 */

public class StoneGameBoard implements TwoPhaseMoveState<Position> {
    public static final int BOARD_SIZE = 4;
    public final BooleanProperty[][] board;

    private State.Player player;
    private LocalDateTime startTime;
    private int turnCount;

    /**
     * Constructs a new StoneGameBoard instance.
     * Initializes the game board with stones, sets the current player,
     * and records the start time of the game session.
     */
    public StoneGameBoard() {
        board = new BooleanProperty[BOARD_SIZE][BOARD_SIZE];
        // Initially, all cells contain stones
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new ReadOnlyBooleanWrapper(true);
            }
        }
        player = State.Player.PLAYER_1;
        startTime = LocalDateTime.now();
        turnCount = 0;
    }


    /**
     * Gets the BooleanProperty associated with the specified cell on the game board.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return The BooleanProperty representing the state of the cell.
     */
    public BooleanProperty getCellProperty(int row, int col) {
        return board[row][col];
    }
    /**
     * Gets the start time of the game session.
     *
     * @return The start time of the game session.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Gets the number of turns played in the game session.
     *
     * @return The number of turns played.
     */
    public int getTurnCount() {
        return turnCount;
    }
    /**
     * Registers a BiConsumer to observe changes to the game board.
     *
     * @param observer The BiConsumer to be notified of changes.
     */
    public void observeBoard(BiConsumer<Integer, Integer> observer) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                final int row = i;
                final int col = j;
                board[i][j].addListener((observable, oldValue, newValue) -> observer.accept(row, col));
            }
        }
    }

    /**
     * Checks if it is legal to move stones from the specified position.
     *
     * @param from The position from which stones are to be removed.
     * @return True if it is legal to move stones from the specified position, false otherwise.
     */
    @Override
    public boolean isLegalToMoveFrom(Position from) {
        return isOnBoard(from) && !isEmpty(from);
    }

    /**
     * Checks if the move from the specified position to another position is legal.
     *
     * @param from The position from which stones are moved.
     * @param to   The position to which stones are moved.
     * @return True if the move is legal, false otherwise.
     */
    @Override
    public boolean isLegalMove(Position from, Position to) {
        return isOnBoard(from) && isOnBoard(to)
                && !isEmpty(from) && !isEmpty(to)
                && isAdjacent(from, to)
                && isValidNumberOfStonesRemoved(to)
                && positionsBetweenNotEmpty(from, to);
    }

    // Checks if all positions between the 'from' and 'to' positions are not empty.

    private boolean positionsBetweenNotEmpty(Position from, Position to) {
        int startRow = Math.min(from.row(), to.row());
        int endRow = Math.max(from.row(), to.row());
        int startCol = Math.min(from.col(), to.col());
        int endCol = Math.max(from.col(), to.col());

        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                if (isEmpty(new Position(row, col))) {
                    return false;
                }
            }
        }

        return true;
    }


    private boolean isOnBoard(Position p) {
        return isOnBoard(p.row(), p.col());
    }

    private boolean isOnBoard(int row, int col) {
        return 0 <= row && row < BOARD_SIZE && 0 <= col && col < BOARD_SIZE;
    }

    /**
     * Checks if the specified position on the game board is empty.
     *
     * @param p The position to check.
     * @return True if the position is empty, false otherwise.
     */
    public boolean isEmpty(Position p) {
        return !board[p.row()][p.col()].get();
    }

    private boolean isValidNumberOfStonesRemoved(Position to) {
        return board[to.row()][to.col()].get();
    }

    private boolean isAdjacent(Position from, Position to) {
        return from.row() == to.row() ||  from.col() == to.col();
    }

    /**
     * Makes a move by removing stones from the specified position to another position.
     *
     * @param from The position from which stones are moved.
     * @param to   The position to which stones are moved.
     */
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
        turnCount++;

        player = player.opponent();
    }

    /**
     * Gets the next player who should make a move.
     *
     * @return The next player who should make a move.
     */
    @Override
    public State.Player getNextPlayer() {
        return player;
    }

    /**
     * Checks if the game is over.
     *
     * @return True if the game is over, false otherwise.
     */
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

    /**
     * Gets the status of the game.
     *
     * @return The status of the game.
     */
    @Override
    public State.Status getStatus() {
        if (!isGameOver()) {
            return State.Status.IN_PROGRESS;
        }
        return player == State.Player.PLAYER_2 ? State.Status.PLAYER_1_WINS : State.Status.PLAYER_2_WINS;
    }

    /**
     * Checks if the specified player is the winner of the game.
     *
     * @param player The player to check for winning status.
     * @return True if the player is the winner, false otherwise.
     */
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

