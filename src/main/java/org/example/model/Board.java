package org.example.model;

import lombok.Getter;
import org.example.errors.InvalidBoardInitException;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Board {
    private final int[][] board;
    private int currentPlayer;
    private final int playerCount;
    private final int boardSize;

    public Board(int playerCount, int boardSize, int currentPlayer) throws InvalidBoardInitException {
        if (!validStartParameters(playerCount, boardSize, currentPlayer))
            throw new InvalidBoardInitException("ERROR: Board size must be 16, 10 or 6 and " + "players count must " + "be" + " 2 or 4.");
        this.currentPlayer = currentPlayer;
        this.playerCount = playerCount;
        this.boardSize = boardSize;
        this.board = getInitialBoardState(playerCount, boardSize);
    }

    public Board(Board board)  {
        this.currentPlayer = board.getCurrentPlayer();
        this.playerCount = board.getPlayerCount();
        this.boardSize = board.getBoardSize();
        this.board = board.getBoard().clone();
    }

    //todo - usun testowy konstruktor
    public Board() throws InvalidBoardInitException {
        this.currentPlayer = 1;
        this.playerCount = 4;
        this.boardSize = 6;
        this.board = BoardInitStates.finalBoardState6P4();
    }

    public int getPrevPlayer() {
        int prevPlayer = currentPlayer - 1;
        return prevPlayer > 0 ? prevPlayer : playerCount;
    }

    private boolean validStartParameters(int playerCount, int boardSize, int currentPlayer) {
        return (playerCount == 4 || playerCount == 2) && (boardSize == 16 || boardSize == 10 || boardSize == 6) && currentPlayer > 0 && currentPlayer <= playerCount;
    }

    private int[][] getInitialBoardState(int playerCount, int boardSize) {
        switch (boardSize) {
            case 16:
                if (playerCount == 2) return BoardInitStates.initialBoardState16P2();
                else return BoardInitStates.initialBoardState16P4();
            case 10:
                if (playerCount == 2) return BoardInitStates.initialBoardState10P2();
                else return BoardInitStates.initialBoardState10P4();
            case 6:
                if (playerCount == 4) return BoardInitStates.initialBoardState6P4();
            default:
                return BoardInitStates.initialBoardState6P2();
        }
    }

    public List<Move> getMovesForPlayer(int player) {
        List<Move> legalMoves = new ArrayList<>();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (board[x][y] == player) {
                    for (int dx = -2; dx <= 2; dx++) {
                        for (int dy = -2; dy <= 2; dy++) {
                            if (Math.abs(dx) + Math.abs(dy) == 1 || Math.abs(dx) == 2 && Math.abs(dy) == 2) {
                                Move potentialMove = new Move(x, y, x + dx, y + dy, player);
                                if (potentialMove.doMoveIfIsLegal(board)) {
                                    legalMoves.add(potentialMove);
                                }
                            }
                        }
                    }
                }
            }
        }
        return legalMoves;
    }

    public boolean setCurrentPlayer(int currentPlayer) {
        if (currentPlayer > 0 && currentPlayer <= playerCount) {
            this.currentPlayer = currentPlayer;
            return true;
        }
        return false;
    }

    public boolean makeMove(Move move) {
        if (move.doMoveIfIsLegal(board)) {
            board[move.getStartX()][move.getStartY()] = 0;
            board[move.getEndX()][move.getEndY()] = currentPlayer;

            if (!move.canContinueJumping(board)) {
                currentPlayer += 1;
                if (currentPlayer > playerCount) currentPlayer = 1;
            }
            return true;
        }
        return false;
    }

    public boolean isGameOver() {
        return isGameOver(getPrevPlayer());
    }

    public boolean isGameOver(int player) {
        int size = board.length;

        for (int x = 0; x < size / 2; x++) {
            for (int y = 0; y <= size / 2; y++) {
                if (x + y < size / 2) switch (player) {
                    case 1:
                        if (board[size - 1 - x][size - 1 - y] != player) return false;
                        break;
                    case 2:
                        if (board[x][y] != player) return false;
                        break;
                    case 3:
                        if (board[size - 1 - x][y] != player) return false;
                        break;
                    case 4:
                        if (board[x][size - 1 - y] != player) return false;
                }
            }
        }
        return true;
    }
}
