package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Move {
    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;
    @Setter
    private Set<String> jumpedPawns;
    private boolean isSimpleMove;

    public Move(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.jumpedPawns = new HashSet<>();
        this.isSimpleMove = true;
    }

    public boolean isLegalMove(int[][] board, int player) {
        if (areFieldsOffTheBoard(board)) return false;

        int startField = board[startX][startY];
        int endField = board[endX][endY];

        if (isPawnNotEqualCurrentPlayer(startField, player) || isFieldOccupied(endField)) return false;
        if (isSimpleMove()) return true;
        if (isJumpMove()) {
            this.isSimpleMove = false;
            return isJumpCorrect(board);
        }

        return false;
    }

    private boolean isJumpCorrect(int[][] board) {
        int jumpX = (startX + endX) / 2;
        int jumpY = (startY + endY) / 2;
        String jumpedPawnKey = jumpX + "," + jumpY;

        return board[jumpX][jumpY] != 0 && !jumpedPawns.contains(jumpedPawnKey);
    }

    public boolean canContinueJumping(int[][] board) {
        if (this.isSimpleMove)
            return false;
        for (int x = -2; x <= 2; x += 4) {
            for (int y = -2; y <= 2; y += 4) {
                int nextX = endX + x;
                int nextY = endY + y;
                if (isXYIncorrect(nextX, board.length) && isXYIncorrect(nextY, board[0].length)) {
                    int midX = (endX + nextX) / 2;
                    int midY = (endY + nextY) / 2;
                    String midKey = midX + "," + midY;
                    if (board[nextX][nextY] == 0 && board[midX][midY] != 0 && !jumpedPawns.contains(midKey)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isJumpMove() {
        return Math.abs(endX - startX) == 2 || Math.abs(endY - startY) == 2;
    }

    private boolean isSimpleMove() {
        return Math.abs(endX - startX) <= 1 && Math.abs(endY - startY) <= 1;
    }

    private boolean areFieldsOffTheBoard(int[][] board) {
        return isXYIncorrect(startX, board.length) || isXYIncorrect(startY, board[startX].length)
                || isXYIncorrect(endX, board.length) || isXYIncorrect(endY, board[endX].length);
    }

    private boolean isXYIncorrect(int field, int boardLength) {
        return field < 0 || field >= boardLength;
    }

    private boolean isPawnNotEqualCurrentPlayer(int pawn, int player) {
        return pawn != player;
    }

    private boolean isFieldOccupied(int field) {
        return field != 0;
    }
}
