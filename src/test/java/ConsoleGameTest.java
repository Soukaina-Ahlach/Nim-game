
import boardgame.console.ConsoleGame;
import boardgame.model.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConsoleGameTest {

    @Test
    public void testParseMove_ValidInput() {
        Position position = ConsoleGame.parseMove("2 3");
        assertEquals(2, position.row());
        assertEquals(3, position.col());
    }

    @Test
    public void testParseMove_ValidInputWithSpaces() {
        Position position = ConsoleGame.parseMove("  1  4  ");
        assertEquals(1, position.row());
        assertEquals(4, position.col());
    }

    @Test
    public void testParseMoveSpecialCharacters() {
        String input = "3 @";
        assertNull(ConsoleGame.parseMove(input));
    }
}
