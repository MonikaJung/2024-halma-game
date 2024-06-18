package org.example;

import org.example.errors.InvalidBoardInitException;
import org.example.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InvalidBoardInitException {
        //Game game = new Game();
        //game.testStart();
        //game.start(2, 16, 1);
        //testMoveTree();
        //testGetPlayerMoves();\
        testAIPlayer();
    }

    public static void testAIPlayer() throws InvalidBoardInitException {
        AIPlayer player1 = new AIPlayer(1, 3, false);
        AIPlayer player2 = new AIPlayer(2, 1, false);
        Game game = new Game();
        game.setBoard(new Board(2, 6, 1, true));
        MoveTree.MoveNode bestMoves1 = player1.selectMoves(game.getBoard());
        System.out.println(bestMoves1);
        System.out.println(bestMoves1.getParent());
        System.out.println(bestMoves1.getParent().getParent());
    }
    public static void testGetPlayerMoves() {
        Game game = new Game();
        game.setBoard(new Board());
        Board b = game.getBoard();
        Printer.printBoard(b.getBoard());
        System.out.print("PLAYER 1: ");
        System.out.println(b.getMovesForPlayer(1));
        System.out.print("\nPLAYER 2: ");
        System.out.println(b.getMovesForPlayer(2));
    }

    public static void testMoveTree() {
        MoveTree tree = new MoveTree(1);

        // level 1 - P1 ------------------------------------
        ArrayList<Move> moves = new ArrayList<>();
        Move move = new Move(1,2,2,1,1);
        moves.add(move);
        MoveTree.MoveNode parentNode = new MoveTree.MoveNode(moves, 30, 1);
        tree.addNode(tree.getRoot(), parentNode);

        //level 2 - P2
        moves = new ArrayList<>();
        move = new Move(2,4,3,4,2);
        moves.add(move);
        MoveTree.MoveNode childNode = new MoveTree.MoveNode(moves, 10, 2);
        tree.addNode(parentNode, childNode);

        //level 3 - P1
        moves = new ArrayList<>();
        move = new Move(3,1,1,1,1);
        moves.add(move);
        MoveTree.MoveNode child2Node = new MoveTree.MoveNode(moves, 6, 3);
        tree.addNode(childNode, child2Node);

        //level 2 - P2
        moves = new ArrayList<>();
        move = new Move(2,4,3,4,2);
        moves.add(move);
        childNode = new MoveTree.MoveNode(moves, 15, 2);
        tree.addNode(parentNode, childNode);

        //level 3 - P1
        moves = new ArrayList<>();
        move = new Move(3,1,1,1,1);
        moves.add(move);
        child2Node = new MoveTree.MoveNode(moves, 8, 3);
        tree.addNode(childNode, child2Node);

        // level 1 - P1 ------------------------------------
        moves = new ArrayList<>();
        move = new Move(1,2,2,1,1);
        moves.add(move);
        parentNode = new MoveTree.MoveNode(moves, 28, 1);
        tree.addNode(tree.getRoot(), parentNode);

        //level 2 - P2
        moves = new ArrayList<>();
        move = new Move(2,4,3,4,2);
        moves.add(move);
        childNode = new MoveTree.MoveNode(moves, 7, 2);
        tree.addNode(parentNode, childNode);

        //level 3 - P1
        moves = new ArrayList<>();
        move = new Move(3,1,1,1,1);
        moves.add(move);
        child2Node = new MoveTree.MoveNode(moves, 10, 3);
        tree.addNode(childNode, child2Node);

        // level 1 - P1 ------------------------------------
        moves = new ArrayList<>();
        move = new Move(1,2,2,1,1);
        moves.add(move);
        parentNode = new MoveTree.MoveNode(moves, 30, 1);
        tree.addNode(tree.getRoot(), parentNode);

        //level 2 - P2
        moves = new ArrayList<>();
        move = new Move(2,4,3,4,2);
        moves.add(move);
        childNode = new MoveTree.MoveNode(moves, 13, 2);
        tree.addNode(parentNode, childNode);

        //level 3 - P1
        moves = new ArrayList<>();
        move = new Move(3,1,1,1,1);
        moves.add(move);
        child2Node = new MoveTree.MoveNode(moves, 5, 3);
        tree.addNode(childNode, child2Node);

        //level 2 - P2
        moves = new ArrayList<>();
        move = new Move(2,4,3,4,2);
        moves.add(move);
        childNode = new MoveTree.MoveNode(moves, 8, 2);
        tree.addNode(parentNode, childNode);

        //level 3 - P1
        moves = new ArrayList<>();
        move = new Move(3,1,1,1,1);
        moves.add(move);
        child2Node = new MoveTree.MoveNode(moves, 5, 3);
        tree.addNode(childNode, child2Node);


        tree.printTree();
        System.out.println();
        System.out.println();
        MoveTree.MoveNode bestMove = tree.getBestMove();
        System.out.println(bestMove);
        System.out.println(bestMove.getParent());
        System.out.println(bestMove.getParent().getParent());
        System.out.println();
        System.out.println();
    }
}