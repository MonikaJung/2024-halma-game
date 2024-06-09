package org.example.model;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Move {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private boolean isSimpleMove;
    private final Set<String> jumpedPawns;

    public Move(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.jumpedPawns = new HashSet<>();
        this.isSimpleMove = true;
    }

    @Override
    public String toString() {
        return "MOVE: "+ startX+":"+startY+" -> "+endX+":"+endY;
    }

    public boolean setNextMove(int[][] board, int endX, int endY) {
        if (canContinueJumping(board)) {
            this.startX = this.endX;
            this.startY = this.endY;
            this.endX = endX;
            this.endY = endY;
            return true;
        }
        return false;
    }

    public boolean doMoveIfIsLegal(int[][] board, int player) {
        if (areFieldsOffTheBoard(board)) return false;

        int startField = board[startX][startY];
        int endField = board[endX][endY];

        if (isPawnNotEqualCurrentPlayer(startField, player) || isFieldOccupied(endField)) return false;
        if (isMoveSimple()) return true;
        if (isJumpMove()) {
            this.isSimpleMove = false;
            return makeJumpIfJumpCorrect(board);
        }

        return false;
    }

    private boolean makeJumpIfJumpCorrect(int[][] board) {
        int jumpX = (startX + endX) / 2;
        int jumpY = (startY + endY) / 2;
        String jumpedPawnKey = jumpX + "," + jumpY;

        if (board[jumpX][jumpY] != 0 && !jumpedPawns.contains(jumpedPawnKey)) {
            jumpedPawns.add(jumpedPawnKey);
            return true;
        }
        return false;
    }

    public boolean canContinueJumping(int[][] board) {
        if (this.isSimpleMove)
            return false;

        for (int x = -2; x < 3; x += 2) {
            for (int y = -2; y < 3; y += 2) {
                int nextX = endX + x;
                int nextY = endY + y;

                if (!isXYIncorrect(nextX, board.length) && !isXYIncorrect(nextY, board[0].length)) {
                    if (!(nextY == endY && nextX == endX)) {

                        int midPawnX = (endX + nextX) / 2;
                        int midPawnY = (endY + nextY) / 2;
                        String midPawnKey = midPawnX + "," + midPawnY;

                        if (board[nextX][nextY] == 0 && board[midPawnX][midPawnY] != 0 && !jumpedPawns.contains(midPawnKey))
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

    public boolean isMoveSimple() {
        return Math.abs(endX - startX) <= 1 && Math.abs(endY - startY) <= 1;
    }

    private boolean areFieldsOffTheBoard(int[][] board) {
        return isXYIncorrect(startX, board.length) || isXYIncorrect(startY, board[startX].length) || isXYIncorrect(endX, board.length) || isXYIncorrect(endY, board[endX].length);
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
