import boardgame.model.StoneGameBoard;
import boardgame.model.Position;
import game.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StoneGameBoardTest {

    private StoneGameBoard stoneGameBoard;

    @BeforeEach
    void setUp() {
        stoneGameBoard = new StoneGameBoard();
    }
    @Test
    void testInitialBoardState() {
        assertFalse(stoneGameBoard.isGameOver());
        assertEquals(State.Player.PLAYER_1, stoneGameBoard.getNextPlayer());
        assertEquals(0, stoneGameBoard.getTurnCount());
    }
    @Test
    void makeLegalMoveTest() {
        stoneGameBoard.makeMove(new Position(0, 0), new Position(0, 1));
        assertEquals(1, stoneGameBoard.getTurnCount());
        assertFalse(stoneGameBoard.getCellProperty(0, 0).get());
        assertFalse(stoneGameBoard.getCellProperty(0, 1).get());
    }

    @Test
    void makeIllegalMoveTest() {
        assertThrows(IllegalArgumentException.class, () -> stoneGameBoard.makeMove(new Position(0, 0), new Position(1, 1)));
        assertEquals(0, stoneGameBoard.getTurnCount());
    }



    @Test
    void nextPlayerTest() {
        assertEquals(State.Player.PLAYER_1, stoneGameBoard.getNextPlayer());
        stoneGameBoard.makeMove(new Position(0, 0), new Position(0, 1));
        assertEquals(State.Player.PLAYER_2, stoneGameBoard.getNextPlayer());
    }

    @Test
    void gameOverTest() {
        assertFalse(stoneGameBoard.isGameOver());

        for (int i = 0; i < StoneGameBoard.BOARD_SIZE; i++) {
            for (int j = 0; j < StoneGameBoard.BOARD_SIZE; j++) {
                stoneGameBoard.makeMove(new Position(i, j), new Position(i, j));
            }
        }
        assertTrue(stoneGameBoard.isGameOver());
    }

    @Test
    public void testGetStatus_InProgress() {
        assertEquals(State.Status.IN_PROGRESS, stoneGameBoard.getStatus());
    }


    @Test
    public void testToString() {
        String expected = "O O O O \nO O O O \nO O O O \nO O O O \n";
        assertEquals(expected, stoneGameBoard.toString());
    }


}
