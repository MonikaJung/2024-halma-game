package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MoveTree {
    private final MoveNode root;
    private final int player;

    public MoveTree(int player) {
        this.root = new MoveNode(new ArrayList<>(), 0, 0);
        this.player = player;
    }

    public void addNode(MoveNode parent, MoveNode child) {
        parent.addChild(child);
    }

    public MoveNode getBestMove() {
        MoveNode bestMove = findBestLeaf(root, 1, false);
        while (bestMove != null && bestMove.getParent() != root) {
            bestMove = bestMove.getParent();
        }
        return bestMove;
    }

    public void printBestPath() {
        MoveNode bestMove = findBestLeaf(root, 1, false);
        System.out.println("Best path: ");
        if (bestMove != null) {
            StringBuilder path = new StringBuilder(bestMove.toString());
            while (bestMove.getParent() != root) {
                bestMove = bestMove.getParent();
                path.insert(0, bestMove + " -> \n");
            }
            System.out.println(path);
        } else System.out.println(bestMove);
    }

    private MoveNode findBestLeaf(MoveNode parentNode, int currentDepth, boolean findMaxScore) {
        if (parentNode.getChildren().isEmpty()) {
            return parentNode;
        }

        MoveNode bestChild = null;
        double bestScore = findMaxScore ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        for (MoveNode child : parentNode.getChildren()) {
            MoveNode potentialBest = findBestLeaf(child, currentDepth + 1, !findMaxScore);
            if (findMaxScore && potentialBest.getScore() - currentDepth > bestScore) {
                bestScore = potentialBest.getScore() - currentDepth;
                bestChild = potentialBest;
            } else if (!findMaxScore && potentialBest.getScore() + currentDepth < bestScore) {
                bestScore = potentialBest.getScore() + currentDepth;
                bestChild = potentialBest;
            }
        }
        return bestChild;
    }

    public MoveNode getBestMoveAlfaBeta() {
        double alpha = Double.NEGATIVE_INFINITY;
        double beta = Double.POSITIVE_INFINITY;
        MoveNode bestMove = findBestLeafAlfaBeta(root, 1, false, alpha, beta);
        while (bestMove != null && bestMove.getParent() != root) {
            bestMove = bestMove.getParent();
        }
        return bestMove;
    }

    private MoveNode findBestLeafAlfaBeta(MoveNode parentNode, int currentDepth, boolean biggestScore, double alpha, double beta) {
        if (parentNode.getChildren().isEmpty()) {
            return parentNode;
        }

        MoveNode bestChild = null;
        double bestScore = biggestScore ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;

        for (MoveNode child : parentNode.getChildren()) {
            MoveNode potentialBest = findBestLeafAlfaBeta(child, currentDepth + 1, !biggestScore, alpha, beta);
            if (biggestScore) {
                if (potentialBest.getScore() - currentDepth > bestScore) {
                    bestScore = potentialBest.getScore() - currentDepth;
                    bestChild = potentialBest;
                    alpha = Math.max(alpha, bestScore);
                    if (beta <= alpha) {
                        break; // beta-cut
                    }
                }
            } else {
                if (potentialBest.getScore() + currentDepth < bestScore) {
                    bestScore = potentialBest.getScore() + currentDepth;
                    bestChild = potentialBest;
                    beta = Math.min(beta, bestScore);
                    if (beta <= alpha) {
                        break; // alfa-cut
                    }
                }
            }
        }
        return bestChild;
    }


    public void printTree() {
        printNodeWithChildren(root, "");
    }

    private void printNodeWithChildren(MoveNode node, String indent) {
        System.out.println(indent + node);
        for (MoveNode child : node.getChildren()) {
            printNodeWithChildren(child, indent + "  ");
        }
    }

    @Getter
    public static class MoveNode {
        private final List<Move> moves;
        private final int score;
        private final int depth;
        @Setter
        private boolean isWin;
        @Setter
        private MoveNode parent;
        private final List<MoveNode> children;

        public MoveNode(List<Move> moves, int score, int depth) {
            this.moves = moves;
            this.score = score;
            this.depth = depth;
            this.parent = null;
            this.children = new ArrayList<>();
            this.isWin = false;
        }

        public void addChild(MoveNode child) {
            children.add(child);
            child.setParent(this);
        }

        @Override
        public String toString() {
            return "Node{" + "score=" + score + ", moves=" + moves + ", childrenCount=" + children.size() + ", isWin" +
                    "=" + isWin + "}";
        }
    }
}
