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
        Move move = null;
        do {
            Printer.displayMoveAndState(move, board.getCurrentPlayer(), board.getPrevPlayer(), board.getBoard());
            if (move != null && move.canContinueJumping(board.getBoard())) {
                System.out.println("Czy chcesz dalej skakać? (t/tak/nie/n)");
                String answer = scanner.nextLine().toLowerCase();
                if (answer.equals("n") || answer.equals("nie")) {
                    board.setCurrentPlayer(board.getCurrentPlayer() + 1);
                    continue;
                }
            }
            move = getUserMove();
        } while (!board.isGameOver(board.getPrevPlayer()));

        System.out.println("Game over! Winner: Player " + board.getPrevPlayer());
    }


    //todo usun testowy start
    public void testStart() {
        board = new Board();

        Move move = null;
        do {
            Printer.displayMoveAndState(move, board.getCurrentPlayer(), board.getPrevPlayer(), board.getBoard());
            if (move != null && move.canContinueJumping(board.getBoard())) {
                System.out.println("Czy chcesz dalej skakać? (t/tak/nie/n)");
                String answer = scanner.next();
                answer = answer.toLowerCase();
                if (answer.equals("n") || answer.equals("nie")) {
                    board.setCurrentPlayer(board.getCurrentPlayer() + 1);
                    System.out.println("Next move skipped.");
                    continue;
                }
            }
            move = getUserMove();
        } while (!board.isGameOver(board.getPrevPlayer()));

        int winner = board.getPrevPlayer();
        Printer.printBoard(board.getBoard());
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
