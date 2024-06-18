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
        MoveNode bestMove = findBestLeaf(root);
//        while (bestMove.getParent() != null) {
//            bestMove = bestMove.getParent();
//        }
        return bestMove;
    }

    private MoveNode findBestLeaf(MoveNode parentNode) {
        if (parentNode.getChildren().isEmpty()) {
            return parentNode;
        }

        MoveNode bestChild = null;
        double bestScore = Double.POSITIVE_INFINITY;
        for (MoveNode child : parentNode.getChildren()) {
            MoveNode potentialBest = findBestLeaf(child);

            if (potentialBest.getScore() < bestScore) {
                bestScore = potentialBest.getScore();
                bestChild = potentialBest;
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
        private MoveNode parent;
        private final List<MoveNode> children;

        public MoveNode(List<Move> moves, int score, int depth) {
            this.moves = moves;
            this.score = score;
            this.depth = depth;
            this.parent = null;
            this.children = new ArrayList<>();
        }

        public void addChild(MoveNode child) {
            children.add(child);
            child.setParent(this);
        }

        @Override
        public String toString() {
            return "Node{" + "score=" + score + ", moves=" + moves + ", childrenCount=" + children.size() + '}';
        }
    }
}
