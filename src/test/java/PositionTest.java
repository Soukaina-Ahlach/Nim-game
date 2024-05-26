import boardgame.model.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void testPositionCreation() {
        Position position = new Position(1, 2);

        assertEquals(1, position.row(), "Row value should be 1");
        assertEquals(2, position.col(), "Column value should be 2");
    }



    @Test
    void testPositionEquality() {
        Position position1 = new Position(5, 6);
        Position position2 = new Position(5, 6);
        Position position3 = new Position(7, 8);

        assertEquals(position1, position2, "Positions with the same row and column should be equal");
        assertNotEquals(position1, position3, "Positions with different rows or columns should not be equal");
    }


}
