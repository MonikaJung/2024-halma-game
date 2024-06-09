import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.example.model.*;

public class BoardTests {
    private Board board;

    @Test
    public void createBoard() {
        board = new Board(1);
        Assertions.assertNotEquals(null, board.getBoard());
        Assertions.assertInstanceOf(int[][].class, board.getBoard());
        Assertions.assertEquals(16, board.getBoard().length);
        Assertions.assertEquals(16, board.getBoard()[0].length);
        Assertions.assertEquals(1, board.getCurrentPlayer());
    }
    @Test
    public void changeCurrentPlayer() {
        board = new Board(2);
        Assertions.assertEquals(2, board.getCurrentPlayer());
        board.setCurrentPlayer(1);
        Assertions.assertEquals(1, board.getCurrentPlayer());
    }
}