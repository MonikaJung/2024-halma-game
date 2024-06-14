package org.example.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class Move {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private final int player;
    private boolean isSimpleMove;
    private final Set<String> jumpedPawns;

    public Move(int startX, int startY, int endX, int endY, int player) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.player = player;
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
            if (isMoveLegal(copyBoard)) {
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

    public boolean doMoveIfIsLegal(int[][] board) {
        if (isMoveLegal(board)) {
            if (isJumpMove()) {
                MidPawn midPawn = new MidPawn(board, ((startX + endX) / 2), ((startY + endY) / 2));
                jumpedPawns.add(midPawn.getKey());
            }
            return true;
        }
        return false;
    }

    private boolean isMoveLegal(int[][] board) {
        if (areFieldsOffTheBoard(board)) return false;

        int startField = board[startX][startY];
        int endField = board[endX][endY];

        if (startField != player || isEndFieldOccupied(endField)) return false;

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

    public List<Move> getMovesForPlayer(int[][] board, int player) {
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

    private boolean isEndFieldOccupied(int field) {
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
