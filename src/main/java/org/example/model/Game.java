package org.example.model;

import org.example.errors.InvalidBoardInitException;

import java.util.Scanner;

public class Game {
    private Board board;
    private final Scanner scanner;

    public Game() {
        scanner = new Scanner(System.in);
        board = null;
    }

    public void start(int playerCount, int boardSize, int startingPlayer) {
        try {
            board = new Board(playerCount, boardSize, startingPlayer);
        } catch (InvalidBoardInitException e) {
            System.out.println(e.getMessage());
            return;
        }
        do {
            board.displayMoveAndState(getUserMove());
        } while (board.isGameOver(board.getPrevPlayer()));

        System.out.println("Game over!");
    }

    //todo usun testowy start
    public void testStart() throws InvalidBoardInitException {
        board = new Board();

        Move move = null;
        if (!board.isGameOver(board.getCurrentPlayer()))
            do {
                board.displayMoveAndState(move);
                move = getUserMove();
            } while (!board.isGameOver());

        int winner = board.getPrevPlayer();
        board.printBoard();
        System.out.println("Game over! Player " + winner + " won!");
    }

    private Move getUserMove() {
        System.out.println("Enter your move (format: startX startY endX endY): ");
        int startX = scanner.nextInt();
        int startY = scanner.nextInt();
        int endX = scanner.nextInt();
        int endY = scanner.nextInt();

        Move move = new Move(startX, startY, endX, endY, board.getCurrentPlayer());
        if (board.makeMove(move)) {
            System.out.println("Move accepted.");
        } else {
            System.out.println("Invalid move, try again.");
            System.out.println(startX + ";" + startY + ", " + endX + ";" + endY);
            getUserMove();
        }
        return move;
    }

}
