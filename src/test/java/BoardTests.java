import org.example.errors.InvalidBoardInitException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.example.model.*;

public class BoardTests {
    private Board board;

    @Test
    public void changeCurrentPlayer() throws InvalidBoardInitException {
        board = new Board(2,16, 2);
        Assertions.assertEquals(2, board.getCurrentPlayer());
        Assertions.assertTrue(board.setCurrentPlayer(1));
        Assertions.assertEquals(1, board.getCurrentPlayer());
        Assertions.assertFalse(board.setCurrentPlayer(7));
        Assertions.assertEquals(1, board.getCurrentPlayer());
    }

    @Test
    public void createBoard16P2() throws InvalidBoardInitException {
        board = new Board(2,16, 1);
        Assertions.assertNotEquals(null, board.getBoard());
        Assertions.assertInstanceOf(int[][].class, board.getBoard());
        Assertions.assertEquals(16, board.getBoard().length);
        Assertions.assertEquals(16, board.getBoard()[0].length);

        Assertions.assertEquals(2, board.getPlayerCount());
        Assertions.assertEquals(16, board.getBoardSize());
        Assertions.assertEquals(1, board.getCurrentPlayer());

        Assertions.assertEquals(1, board.getBoard()[0][0]);
        Assertions.assertEquals(1, board.getBoard()[7][0]);
        Assertions.assertEquals(1, board.getBoard()[0][7]);
        Assertions.assertEquals(1, board.getBoard()[2][2]);

        Assertions.assertEquals(2, board.getBoard()[15][15]);
        Assertions.assertEquals(2, board.getBoard()[8][15]);
        Assertions.assertEquals(2, board.getBoard()[15][8]);
        Assertions.assertEquals(2, board.getBoard()[13][13]);

        Assertions.assertEquals(0, board.getBoard()[0][15]);
        Assertions.assertEquals(0, board.getBoard()[0][8]);
        Assertions.assertEquals(0, board.getBoard()[7][15]);
        Assertions.assertEquals(0, board.getBoard()[5][13]);

        Assertions.assertEquals(0, board.getBoard()[15][0]);
        Assertions.assertEquals(0, board.getBoard()[15][7]);
        Assertions.assertEquals(0, board.getBoard()[8][0]);
        Assertions.assertEquals(0, board.getBoard()[13][2]);
    }


    @Test
    public void createBoard16P4() throws InvalidBoardInitException {
        board = new Board(4,16, 1);
        Assertions.assertNotEquals(null, board.getBoard());
        Assertions.assertInstanceOf(int[][].class, board.getBoard());
        Assertions.assertEquals(16, board.getBoard().length);
        Assertions.assertEquals(16, board.getBoard()[0].length);

        Assertions.assertEquals(4, board.getPlayerCount());
        Assertions.assertEquals(16, board.getBoardSize());
        Assertions.assertEquals(1, board.getCurrentPlayer());

        Assertions.assertEquals(1, board.getBoard()[0][0]);
        Assertions.assertEquals(1, board.getBoard()[7][0]);
        Assertions.assertEquals(1, board.getBoard()[0][7]);
        Assertions.assertEquals(1, board.getBoard()[2][2]);

        Assertions.assertEquals(2, board.getBoard()[15][15]);
        Assertions.assertEquals(2, board.getBoard()[8][15]);
        Assertions.assertEquals(2, board.getBoard()[15][8]);
        Assertions.assertEquals(2, board.getBoard()[13][13]);

        Assertions.assertEquals(3, board.getBoard()[0][15]);
        Assertions.assertEquals(3, board.getBoard()[0][8]);
        Assertions.assertEquals(3, board.getBoard()[7][15]);
        Assertions.assertEquals(3, board.getBoard()[5][13]);

        Assertions.assertEquals(4, board.getBoard()[15][0]);
        Assertions.assertEquals(4, board.getBoard()[15][7]);
        Assertions.assertEquals(4, board.getBoard()[8][0]);
        Assertions.assertEquals(4, board.getBoard()[13][2]);
    }

    @Test
    public void createBoard10P2() throws InvalidBoardInitException {
        board = new Board(2,10, 1);

        Assertions.assertNotEquals(null, board.getBoard());
        Assertions.assertInstanceOf(int[][].class, board.getBoard());
        Assertions.assertEquals(10, board.getBoard().length);
        Assertions.assertEquals(10, board.getBoard()[0].length);

        Assertions.assertEquals(2, board.getPlayerCount());
        Assertions.assertEquals(10, board.getBoardSize());
        Assertions.assertEquals(1, board.getCurrentPlayer());

        Assertions.assertEquals(1, board.getBoard()[0][0]);
        Assertions.assertEquals(1, board.getBoard()[4][0]);
        Assertions.assertEquals(1, board.getBoard()[0][4]);
        Assertions.assertEquals(1, board.getBoard()[2][2]);

        Assertions.assertEquals(2, board.getBoard()[9][9]);
        Assertions.assertEquals(2, board.getBoard()[5][9]);
        Assertions.assertEquals(2, board.getBoard()[9][5]);
        Assertions.assertEquals(2, board.getBoard()[7][7]);

        Assertions.assertEquals(0, board.getBoard()[0][9]);
        Assertions.assertEquals(0, board.getBoard()[0][5]);
        Assertions.assertEquals(0, board.getBoard()[4][9]);
        Assertions.assertEquals(0, board.getBoard()[5][7]);

        Assertions.assertEquals(0, board.getBoard()[9][0]);
        Assertions.assertEquals(0, board.getBoard()[9][4]);
        Assertions.assertEquals(0, board.getBoard()[5][0]);
        Assertions.assertEquals(0, board.getBoard()[7][2]);
    }

    @Test
    public void createBoard10P4() throws InvalidBoardInitException {
        board = new Board(4,10, 3);

        Assertions.assertNotEquals(null, board.getBoard());
        Assertions.assertInstanceOf(int[][].class, board.getBoard());
        Assertions.assertEquals(10, board.getBoard().length);
        Assertions.assertEquals(10, board.getBoard()[0].length);

        Assertions.assertEquals(4, board.getPlayerCount());
        Assertions.assertEquals(10, board.getBoardSize());
        Assertions.assertEquals(3, board.getCurrentPlayer());

        Assertions.assertEquals(1, board.getBoard()[0][0]);
        Assertions.assertEquals(1, board.getBoard()[4][0]);
        Assertions.assertEquals(1, board.getBoard()[0][4]);
        Assertions.assertEquals(1, board.getBoard()[2][2]);

        Assertions.assertEquals(2, board.getBoard()[9][9]);
        Assertions.assertEquals(2, board.getBoard()[5][9]);
        Assertions.assertEquals(2, board.getBoard()[9][5]);
        Assertions.assertEquals(2, board.getBoard()[7][7]);

        Assertions.assertEquals(3, board.getBoard()[0][9]);
        Assertions.assertEquals(3, board.getBoard()[0][5]);
        Assertions.assertEquals(3, board.getBoard()[4][9]);
        Assertions.assertEquals(3, board.getBoard()[2][7]);

        Assertions.assertEquals(4, board.getBoard()[9][0]);
        Assertions.assertEquals(4, board.getBoard()[9][4]);
        Assertions.assertEquals(4, board.getBoard()[5][0]);
        Assertions.assertEquals(4, board.getBoard()[7][2]);
    }

    @Test
    public void createBoard6P2() throws InvalidBoardInitException {
        board = new Board(2,6, 2);
        Assertions.assertNotEquals(null, board.getBoard());
        Assertions.assertInstanceOf(int[][].class, board.getBoard());
        Assertions.assertEquals(6, board.getBoard().length);
        Assertions.assertEquals(6, board.getBoard()[0].length);

        Assertions.assertEquals(2, board.getPlayerCount());
        Assertions.assertEquals(6, board.getBoardSize());
        Assertions.assertEquals(2, board.getCurrentPlayer());

        Assertions.assertEquals(1, board.getBoard()[0][0]);
        Assertions.assertEquals(1, board.getBoard()[2][0]);
        Assertions.assertEquals(1, board.getBoard()[0][2]);
        Assertions.assertEquals(1, board.getBoard()[1][1]);

        Assertions.assertEquals(2, board.getBoard()[5][5]);
        Assertions.assertEquals(2, board.getBoard()[3][5]);
        Assertions.assertEquals(2, board.getBoard()[5][3]);
        Assertions.assertEquals(2, board.getBoard()[4][4]);

        Assertions.assertEquals(0, board.getBoard()[0][5]);
        Assertions.assertEquals(0, board.getBoard()[0][3]);
        Assertions.assertEquals(0, board.getBoard()[2][5]);
        Assertions.assertEquals(0, board.getBoard()[1][4]);

        Assertions.assertEquals(0, board.getBoard()[5][0]);
        Assertions.assertEquals(0, board.getBoard()[5][2]);
        Assertions.assertEquals(0, board.getBoard()[3][0]);
        Assertions.assertEquals(0, board.getBoard()[4][1]);
    }

    @Test
    public void createBoard6P4() throws InvalidBoardInitException {
        board = new Board(4,6, 4);
        Assertions.assertNotEquals(null, board.getBoard());
        Assertions.assertInstanceOf(int[][].class, board.getBoard());
        Assertions.assertEquals(6, board.getBoard().length);
        Assertions.assertEquals(6, board.getBoard()[0].length);

        Assertions.assertEquals(4, board.getPlayerCount());
        Assertions.assertEquals(6, board.getBoardSize());
        Assertions.assertEquals(4, board.getCurrentPlayer());

        Assertions.assertEquals(1, board.getBoard()[0][0]);
        Assertions.assertEquals(1, board.getBoard()[2][0]);
        Assertions.assertEquals(1, board.getBoard()[0][2]);
        Assertions.assertEquals(1, board.getBoard()[1][1]);

        Assertions.assertEquals(2, board.getBoard()[5][5]);
        Assertions.assertEquals(2, board.getBoard()[3][5]);
        Assertions.assertEquals(2, board.getBoard()[5][3]);
        Assertions.assertEquals(2, board.getBoard()[4][4]);

        Assertions.assertEquals(3, board.getBoard()[0][5]);
        Assertions.assertEquals(3, board.getBoard()[0][3]);
        Assertions.assertEquals(3, board.getBoard()[2][5]);
        Assertions.assertEquals(3, board.getBoard()[1][4]);

        Assertions.assertEquals(4, board.getBoard()[5][0]);
        Assertions.assertEquals(4, board.getBoard()[5][2]);
        Assertions.assertEquals(4, board.getBoard()[3][0]);
        Assertions.assertEquals(4, board.getBoard()[4][1]);
    }

    @Test
    public  void  evaluateBoardFinal6P2() throws InvalidBoardInitException {
        board = new Board(2, 6, 1, true);
        Assertions.assertEquals(9, board.evaluateBoard(1));
        Assertions.assertEquals(9, board.evaluateBoard(2));
        Move move = new Move(3,4,3,3,1);
        Assertions.assertTrue(board.makeMove(move));
        Assertions.assertEquals(10, board.evaluateBoard(1));
        move = new Move(0,3,0,2,2);
        Assertions.assertTrue(board.makeMove(move));
        Assertions.assertEquals(8, board.evaluateBoard(2));
    }

    @Test
    public  void  evaluateBoard6P2() throws InvalidBoardInitException {
        board = new Board(2, 6, 1);
        Assertions.assertEquals(52, board.evaluateBoard(1));
        Assertions.assertEquals(52, board.evaluateBoard(2));
        Move move = new Move(0,1,0,3,1);
        Assertions.assertTrue(board.makeMove(move));
        Assertions.assertEquals(50, board.evaluateBoard(1));
        move = new Move(5,5,3,3,2);
        Assertions.assertTrue(board.makeMove(move));
        Assertions.assertEquals(48, board.evaluateBoard(2));
    }

    @Test
    public  void  evaluateBoard16P2() throws InvalidBoardInitException {
        board = new Board(2, 16, 1);
        Assertions.assertEquals(912, board.evaluateBoard(1));
        Assertions.assertEquals(912, board.evaluateBoard(2));
        Move move = new Move(0,6,0,8,1);
        Assertions.assertTrue(board.makeMove(move));
        Assertions.assertEquals(910, board.evaluateBoard(1));
        move = new Move(13,13,11,11,2);
        Assertions.assertTrue(board.makeMove(move));
        Assertions.assertEquals(908, board.evaluateBoard(2));
    }

    @Test
    public  void  evaluateBoardFinal16P2() throws InvalidBoardInitException {
        board = new Board(2, 16, 1, true);
        Assertions.assertEquals(170, board.evaluateBoard(1));
        Assertions.assertEquals(169, board.evaluateBoard(2));
        Move move = new Move(7,14,8,15,1);
        Assertions.assertTrue(board.makeMove(move));
        Assertions.assertEquals(168, board.evaluateBoard(1));
        move = new Move(3,5,3,4,2);
        Assertions.assertTrue(board.makeMove(move));
        Assertions.assertEquals(168, board.evaluateBoard(2));
    }
}