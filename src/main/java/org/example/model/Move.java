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
        return "MOVE: " + startX + ":" + startY + " -> " + endX + ":" + endY;
    }

    public boolean setNextMove(int[][] board, int nextEndX, int nextEndY) {
        int oldStartX = startX;
        int oldStartY = startY;
        int oldEndX = endX;
        int oldEndY = endY;

        if (canContinueJumping(board)) {
            this.startX = this.endX;
            this.startY = this.endY;
            this.endX = nextEndX;
            this.endY = nextEndY;
            MidPawn playersJumpingPawn = new MidPawn(board, startX, startY);

            int[][] copyBoard = board.clone();
            if (isMoveLegal(copyBoard, playersJumpingPawn.getPlayer())) {
                return true;
            } else {
                this.startX = oldStartX;
                this.startY = oldStartY;
                this.endX = oldEndX;
                this.endY = oldEndY;
                return false;
            }
        }
        return false;
    }

    public boolean doMoveIfIsLegal(int[][] board, int player) {
        if (isMoveLegal(board, player)) {
            if (isJumpMove()) {
                MidPawn midPawn = new MidPawn(board, ((startX + endX) / 2), ((startY + endY) / 2));
                jumpedPawns.add(midPawn.getKey());
            }
            return true;
        }
        return false;
    }

    private boolean isMoveLegal(int[][] board, int player) {
        if (areFieldsOffTheBoard(board)) return false;

        int startField = board[startX][startY];
        int endField = board[endX][endY];
        if (isPawnNotEqualCurrentPlayer(startField, player) || isFieldOccupied(endField)) return false;

        if (isMoveSimple()) return true;
        if (isJumpMove()) {
            this.isSimpleMove = false;
            MidPawn midPawn = new MidPawn(board, ((startX + endX) / 2), ((startY + endY) / 2));
            return isJumpCorrect(midPawn);
        }
        return false;
    }


    private boolean isJumpCorrect(MidPawn midPawn) {
        return midPawn.getPlayer() != 0 && !jumpedPawns.contains(midPawn.getKey());
    }

    public boolean canContinueJumping(int[][] board) {
        if (this.isSimpleMove) return false;

        for (int x = -2; x < 3; x += 2) {
            for (int y = -2; y < 3; y += 2) {
                int nextX = endX + x;
                int nextY = endY + y;
                if (!isXYIncorrect(nextX, board.length) && !isXYIncorrect(nextY, board[0].length)) {
                    if (!(nextY == endY && nextX == endX)) {
                        MidPawn midPawn = new MidPawn(board, ((endX + nextX) / 2), ((endY + nextY) / 2));
                        if (board[nextX][nextY] == 0 && isJumpCorrect(midPawn))
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

    @Getter
    private static class MidPawn {
        private final int x;
        private final int y;
        private final int player;
        private final String key;

        public MidPawn(int[][] board, int x, int y) {
            this.x = x;
            this.y = y;
            this.player = board[x][y];
            this.key = x + "," + y;
        }

        @Override
        public String toString() {
            return "PAWN: " + x + ";" + y + " p" + player;
        }
    }
}
