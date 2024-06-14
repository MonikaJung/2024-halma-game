package org.example.model;

import lombok.Getter;
import lombok.Setter;
import org.example.errors.InvalidBoardInitException;

@Getter
public class Board {
    private final int[][] board;
    private int currentPlayer;
    private final int playerCount;
    private final int boardSize;

    public Board(int playerCount, int boardSize, int currentPlayer) throws InvalidBoardInitException {
        if(!validStartParameters(playerCount, boardSize, currentPlayer))
            throw new InvalidBoardInitException("ERROR: Board size must be 16, 10 or 6 and players count must be 2 or 4.");
        this.currentPlayer = currentPlayer;
        this.playerCount = playerCount;
        this.boardSize = boardSize;
        this.board = getInitialBoardState(playerCount, boardSize);
    }

    private boolean validStartParameters(int playerCount, int boardSize, int currentPlayer) {
        return (playerCount == 4 || playerCount == 2) && (boardSize == 16 || boardSize == 10 || boardSize == 6)
                && currentPlayer > 0 && currentPlayer<= playerCount;
    }

    private int[][] getInitialBoardState(int playerCount, int boardSize) {
        switch (boardSize){
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

    public boolean setCurrentPlayer(int currentPlayer) {
        if (currentPlayer > 0 && currentPlayer <= playerCount){
            this.currentPlayer = currentPlayer;
            return true;
        }
        return false;
    }

    public boolean makeMove(Move move) {
        if (move.doMoveIfIsLegal(board, currentPlayer)) {
            board[move.getStartX()][move.getStartY()] = 0;
            board[move.getEndX()][move.getEndY()] = currentPlayer;

            if (!move.canContinueJumping(board)) {
                currentPlayer += 1;
                if (currentPlayer > playerCount)
                    currentPlayer = 1;
            }
            return true;
        }
        return false;
    }
}
