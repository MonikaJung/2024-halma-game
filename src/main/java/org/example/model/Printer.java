package org.example.model;

public class Printer {

    public static void displayMoveAndState(Move move, int currentPlayer, int prevPlayer, int[][] board) {
        if (move != null) {
            System.out.println("Last move: player " + prevPlayer + " (" + move.getStartX() + "," + move.getStartY() + ") " + "->" + " (" + move.getEndX() + "," + move.getEndY() + ")");
            if (move.canContinueJumping(board)) {
                System.out.println("Turn: player " + prevPlayer + " (can continue jumping)");
            } else {
                System.out.println("Turn: player " + currentPlayer);
            }
        } else {
            System.out.println("---------NEW GAME---------");
            System.out.println("Turn: player " + currentPlayer);
        }
        printBoard(board);
    }

    public static void printBoard(int[][] board) {
        int size = board.length;

        System.out.print("   (Y):");
        for (int j = 0; j < size; j++) {
            System.out.printf("%2d ", j);
        }
        System.out.println();
        System.out.print("(X)    ");
        for (int j = 0; j < size; j++) {
            System.out.print("___");
        }
        System.out.println("_     (X)");

        for (int i = 0; i < size; i++) {
            System.out.printf("%2d    | ", i);
            for (int j = 0; j < size; j++) {
                System.out.printf("%d  ", board[i][j]);
            }
            System.out.printf("|    %2d\n", i);
        }

        System.out.print("       ");
        for (int j = 0; j < size; j++) {
            System.out.print("___");
        }
        System.out.println("_");

        System.out.print("   (Y):");
        for (int j = 0; j < size; j++) {
            System.out.printf("%2d ", j);
        }
        System.out.println();
    }

}
