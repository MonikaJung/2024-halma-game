import org.example.errors.InvalidBoardInitException;
import org.example.model.Board;
import org.example.model.Move;
import org.junit.jupiter.api.*;

public class MoveTests {


    @Nested
    class SimpleMoveTests {
        private Board board;

        @BeforeEach
        void setup() throws InvalidBoardInitException {
            this.board = new Board(2, 16, 1);
        }
        @Test
        public void makeSimpleMove() {
            Move move = new Move(0, 7, 0, 8, 1);
            Assertions.assertTrue(board.makeMove(move));
            move = new Move(13, 10, 13, 9, 2);
            Assertions.assertTrue(board.makeMove(move));
            move = new Move(3, 4, 3, 5, 1);
            Assertions.assertTrue(board.makeMove(move));
        }

        @Test
        public void makeSimpleMoveWithWrongXY() {
            Move move = new Move(0, 7, -1, 7, 1);
            Assertions.assertFalse(board.makeMove(move));
            move = new Move(15, 8, 16, 8, 1);
            Assertions.assertFalse(board.makeMove(move));
        }

        @Test
        public void makeSimpleMoveTooFar() {
            Move move = new Move(0, 7, 0, 10, 1);
            Assertions.assertFalse(board.makeMove(move));
        }

        @Test
        public void makeSimpleMoveToOccupiedField() {
            Move move = new Move(0, 7, 0, 6, 1);
            Assertions.assertFalse(board.makeMove(move));
        }

        @Test
        public void makeSimpleMoveWithNotYourPawn() {
            Move move = new Move(8, 15, 7, 15, 1);
            Assertions.assertFalse(board.makeMove(move));
            board.setCurrentPlayer(2);
            move = new Move(7, 0, 7, 1, 2);
            Assertions.assertFalse(board.makeMove(move));
            move = new Move(8, 15, 7, 15, 2);
            Assertions.assertTrue(board.makeMove(move));
        }

        @Test
        public void makeSimpleMoveFromEmptyField() {
            Move move = new Move(8, 8, 8, 9, 1);
            Assertions.assertFalse(board.makeMove(move));
        }
    }


    @Nested
    class JumpMovesTests {
        private Board board;

        @BeforeEach
        void setup() throws InvalidBoardInitException {
            this.board = new Board(2, 16, 1);

        }

        @Test
        public void makeJumpMove() {
            Move move = new Move(6, 0, 8, 0, 1);
            Assertions.assertTrue(board.makeMove(move));
            Assertions.assertFalse(move.canContinueJumping(board.getBoard()));
        }

        @Test
        public void makeJumpMoveTooFar() {
            Move move = new Move(6, 0, 9, 0, 1);
            Assertions.assertFalse(board.makeMove(move));
        }

        @Test
        public void makeJumpMoveToOccupiedField() {
            Move move = new Move(6, 0, 4, 0, 1);
            Assertions.assertFalse(board.makeMove(move));
        }

        @Test
        public void makeJumpMoveWithNotYourPawn() {
            Move move = new Move(8, 15, 7, 15, 1);
            Assertions.assertFalse(board.makeMove(move));
            board.setCurrentPlayer(2);
            move = new Move(8, 15, 7, 15, 2);
            Assertions.assertTrue(board.makeMove(move));
        }

        @Test
        public void makeMultiJumpMove() {
            prepareBoardForMultipleJump();
            Assertions.assertEquals(0, board.getBoard()[0][6]);
            Assertions.assertEquals(0, board.getBoard()[0][8]);
            Move move = new Move(0, 4, 0, 6, 1);
            Assertions.assertTrue(board.makeMove(move));
            Assertions.assertFalse(move.isSimpleMove());
            Assertions.assertTrue(move.setNextMove(board.getBoard(), 0, 8));
            Assertions.assertTrue(board.makeMove(move));
            Assertions.assertTrue(move.setNextMove(board.getBoard(), 0, 10));
            Assertions.assertTrue(board.makeMove(move));
            Assertions.assertFalse(move.canContinueJumping(board.getBoard()));
        }
        @Test
        public void makeMultiJumpAndTryDoubleJumpPawn() {
            prepareBoardForMultipleJump();
            Assertions.assertEquals(0, board.getBoard()[0][6]);
            Assertions.assertEquals(0, board.getBoard()[0][8]);
            Move move = new Move(0, 4, 0, 6, 1);
            Assertions.assertTrue(board.makeMove(move));
            Assertions.assertFalse(move.setNextMove(board.getBoard(), 0, 4));
            Assertions.assertTrue(move.setNextMove(board.getBoard(), 0, 8));
            Assertions.assertTrue(board.makeMove(move));
        }

        private void prepareBoardForMultipleJump() {
            // Prepare for moves y4->y6, y6->y8, y8->y10 with x=0, player=1
            Move move = new Move(0, 6, 0, 8, 1);
            Assertions.assertTrue(board.makeMove(move));
            board.setCurrentPlayer(1);
            move = new Move(0, 8, 0, 9, 1);
            Assertions.assertTrue(board.makeMove(move));
            board.setCurrentPlayer(1);
        }
    }
}