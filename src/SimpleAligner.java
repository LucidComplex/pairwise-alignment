import fasta.Fasta;
import sun.reflect.generics.tree.Tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by rl-14 on 12/5/16.
 */
public class SimpleAligner {
    private int match;
    private int mismatch;
    private int gap;

    public SimpleAligner(int match, int mismatch, int gap) {
        this.match = match;
        this.mismatch = mismatch;
        this.gap = gap;
    }

    public int align(Fasta f1, Fasta f2) {
        String sequenceA = f1.getSequence();
        String sequenceB = f2.getSequence();
        int[][] matrix = SequenceAligner.createGlobalMatrix(sequenceA.length(), sequenceB.length(), gap);
        Path[][] paths = fillMatrix(matrix, sequenceA, sequenceB);
        for (int i = 1; i < paths.length; i++) {
            for (int j = 1; j < paths[0].length; j++) {
                if (paths[i][j].diagonal) {
                    System.out.printf("d");
                }
                else {
                    System.out.printf(".");
                }
                if (paths[i][j].left) {
                    System.out.printf("l");
                }
                else {
                    System.out.printf(".");
                }
                if (paths[i][j].up) {
                    System.out.printf("u");
                }
                else {
                    System.out.printf(".");
                }
                System.out.printf(" ");
            }
            System.out.println();
        }
        TreeNode node = new TreeNode();
        traceBack(paths, paths.length - 1, paths[0].length - 1, node);
        printAlignment(node);
        int score = matrix[matrix.length - 1][matrix[0].length - 1];
        return score;
    }

    private void printAlignment(TreeNode node) {
        Stack<TreeNode> stack = new Stack<>();
        Stack<String> alignment = new Stack<>();
        Stack<TreeNode> split = new Stack<>();
        TreeNode next;
        stack.push(node);
        while (!stack.isEmpty()) {
            next = stack.pop();
            /*
            for (TreeNode n : next.children) {
                stack.push(n);
            }
            */
            if (next.children.size() > 0) {
                stack.push(next.children.get(0));
            }
            if (next.direction == null) {
                continue;
            }
            switch (next.direction) {
                case DIAGONAL:
                    alignment.push("DIAG");
                    break;
                case LEFT:
                    alignment.push("LEFT");
                    break;
                case UP:
                    alignment.push("UP");
                    break;
            }
        }
        while (!alignment.isEmpty()) {
            System.out.printf(alignment.pop() + " ");
        }
    }

    private void traceBack(Path[][] paths, int row, int col, TreeNode parent) {
        Path dir = paths[row][col];
        TreeNode n;
        if (row == 0 && col == 0) {
            return;
        }
        if (dir == null) {
            return;
        }
        if (dir.diagonal) {
            n = new TreeNode(Direction.DIAGONAL);
            n.parent = parent;
            parent.children.add(n);
            traceBack(paths, row - 1, col - 1, n);
        }
        if (dir.left) {
            n = new TreeNode(Direction.LEFT);
            n.parent = parent;
            parent.children.add(n);
            traceBack(paths, row, col - 1, n);
        }
        if (dir.up) {
            n = new TreeNode(Direction.UP);
            n.parent = parent;
            parent.children.add(n);
            traceBack(paths, row - 1, col, n);
        }
    }

    public Path[][] fillMatrix(int[][] matrix, String seqA, String seqB) {
        Path[][] paths = new Path[matrix.length][matrix[0].length];
        Path dir;
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                dir = new Path();
                int diagonal = matrix[i - 1][j - 1] + score(seqA.charAt(i - 1), seqB.charAt(j - 1));
                int up = matrix[i - 1][j] + gap;
                int left = matrix[i][j - 1] + gap;
                int max = diagonal;
                if (seqA.charAt(i - 1) != seqB.charAt(j - 1)) {
                    dir.mismatch = true;
                }
                if (max < up) {
                    max = up;
                }
                if (max < left) {
                    max = left;
                }
                if (max == diagonal) {
                    dir.diagonal = true;
                }
                if (max == left) {
                    dir.left = true;
                }
                if (max == up) {
                    dir.up = true;
                }

                paths[i][j] = dir;
                matrix[i][j] = max;
            }
        }
        return paths;
    }

    private int score(char a, char b) {
        return a == b ? match : mismatch;
    }

}

class Path {
    boolean up, left, diagonal, mismatch;
}

enum Direction {
    UP, LEFT, DIAGONAL;
}

class TreeNode {
    Direction direction;
    List<TreeNode> children;
    TreeNode parent;

    public TreeNode() {
        children = new LinkedList<>();
    }

    public TreeNode(Direction direction) {
        this();
        this.direction = direction;
    }
}