package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class AIPlayer {
    private final int playerNumber;
    private final int depth;
    private final boolean useAlphaBeta;

    public AIPlayer(int playerNumber, int depth, boolean useAlphaBeta) {
        this.playerNumber = playerNumber;
        if (depth <= 5 && depth > 0) this.depth = depth;
        else this.depth = 3;
        this.useAlphaBeta = useAlphaBeta;
    }

    public List<Move> selectMoves(Board board) {
        MoveTree moveTree = new MoveTree(playerNumber);
        if (board.isGameOver()) return null;
        populateMoveTree(moveTree.getRoot(), board, 1);
        if (this.useAlphaBeta) {
            //return selectMovesWithAlfaBeta(moveTree);
        }
        return selectMovesNoAlfaBeta(moveTree).getMoves();
    }


    private MoveTree.MoveNode selectMovesNoAlfaBeta(MoveTree moveTree) {
        long startTime = System.currentTimeMillis();
        MoveTree.MoveNode bestMoveNode = moveTree.getBestMove();
        long endTime = System.currentTimeMillis();

        //printing best path
        moveTree.printBestPath();
        System.out.println("No alfa-beta cuts time: " + (endTime - startTime) + " ms");

        return bestMoveNode;
    }

    private void populateMoveTree(MoveTree.MoveNode parentNode, Board board, int currentDepth) {
        if (currentDepth > depth) return;
        if (board.isGameOver()){
            parentNode.setWin(true);
            return;
        }

        List<Move> possibleMoves;
        int player = playerNumber;
        if (currentDepth % 2 == 1) {
            possibleMoves = board.getMovesForPlayer(player);
        } else {
            player = playerNumber + 1 > 2 ? 1 : playerNumber + 1;
            possibleMoves = board.getMovesForPlayer(player);
        }

        for (Move move : possibleMoves) {
            ArrayList<Move> moveList = new ArrayList<>();
            moveList.add(move);
            board.makeMove(move);
            int score = board.evaluateBoard(player);

            MoveTree.MoveNode childNode = new MoveTree.MoveNode(moveList, score, currentDepth);
            parentNode.addChild(childNode);

            if (board.isGameOver()) {
                childNode.setWin(true);
                board.undoMove(move);
                return;
            }
            if (move.canContinueJumping(board.getBoard())) {
                populateMoveTreeWithJumps(childNode, board, currentDepth, player);
            } else {
                populateMoveTree(childNode, board, currentDepth + 1);
            }

            board.undoMove(move);
        }
    }

    private void populateMoveTreeWithJumps(MoveTree.MoveNode currentNode, Board board, int currentDepth, int player) {
        List<Move> possibleMoves = currentNode.getMoves().getFirst().getNextJumps(board.getBoard());

        for (Move move : possibleMoves) {
            ArrayList<Move> moveList = new ArrayList<>(currentNode.getMoves());
            moveList.add(move);
            board.makeMove(move);
            int score = board.evaluateBoard(player);

            MoveTree.MoveNode childNode = new MoveTree.MoveNode(moveList, score, currentDepth);
            currentNode.getParent().addChild(childNode);

            if (board.isGameOver()) {
                childNode.setWin(true);
                board.undoMove(move);
                return;
            }

            if (move.canContinueJumping(board.getBoard())) {
                populateMoveTreeWithJumps(childNode, board, currentDepth, player);
            } else {
                populateMoveTree(childNode, board, currentDepth + 1);
            }
            board.undoMove(move);

        }
    }
}
