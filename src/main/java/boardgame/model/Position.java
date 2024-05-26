package boardgame.model;

/**
 * Represents a position on the game board grid.
 * A position is defined by its row and column indices.
 */

/**
 * Constructs a new Position with the specified row and column indices.
 *
 * @param row The row index.
 * @param col The column index.
 */
public record Position(int row, int col) {

 /**
  * Returns a string representation of the position.
  *
  * @return A string representation of the position in the format "(row,col)".
  */
    @Override
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

}