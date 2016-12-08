import fasta.Fasta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tan on 12/8/16.
 */
public class ProteinAligner {
    private ScoringMatrix scoringMatrix;
    private int gap;

    public ProteinAligner(ScoringMatrix m) {
        scoringMatrix = m;
    }
    public Result align(Fasta f1, Fasta f2) {
        String sequenceA = f1.getSequence();
        String sequenceB = f2.getSequence();
        int[][] matrix;
        if (scoringMatrix.name == "PAM120") {
            gap = -8;
            matrix = SequenceAligner.createGlobalMatrix(sequenceA.length(), sequenceB.length(), gap);

        } else if (scoringMatrix.name == "BLOSUM62"){
            gap = -4;
            matrix = SequenceAligner.createLocalMatrix(sequenceA.length(), sequenceB.length());
        } else {
            return null;
        }
        if (scoringMatrix.name == "BLOSUM62") {
            Path[][] path = fillLocalMatrix(matrix, sequenceA, sequenceB);
                  for (int i = 1; i < path.length; i++) {
            for (int j = 1; j < path[0].length; j++) {
                if (path[i][j].diagonal) {
                    System.out.printf("↖");
                }
                else {
                    System.out.printf(" ");
                }
                if (path[i][j].left) {
                    System.out.printf("←");
                }
                else {
                    System.out.printf(" ");
                }
                if (path[i][j].up) {
                    System.out.printf("↑");
                }
                else {
                    System.out.printf(" ");
                }
                System.out.printf(" | ");
            }
            System.out.println();
        }
            int max = 0;
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (max < matrix[i][j]) {
                        max = matrix[i][j];
                    }
                }
            }
            List<Coordinate> coordinates = new ArrayList<>();
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (matrix[i][j] == max) {
                        coordinates.add(new Coordinate(i, j));
                    }
                }
            }
            List<TreeNode> heads = new LinkedList<>();
            TreeNode head;
            for (Coordinate coordinate : coordinates) {
                head = new TreeNode();
                head.col = coordinate.col;
                head.row = coordinate.row;
                traceBack(path, coordinate.col, coordinate.row, head);
                heads.add(head);
            }
            List<List<String>> alignments = new LinkedList<>();
            for (int i = 0; i < heads.size(); i++) {
                alignments.add(new LinkedList<>());
                getAlignment(heads.get(i), "", alignments.get(i));
            }
            List<Alignment> outAlign = new LinkedList<>();
            for (List<String> alignmentList : alignments) {
                int headIndex = 0;
                for (String s : alignmentList) {
                    System.out.println(s);
                    int[] coord = new int[2];
                    coord[0] = Integer.parseInt(s.substring(s.indexOf('{') + 1, s.indexOf(',')));
                    coord[1] = Integer.parseInt(s.substring(s.indexOf(',') + 1, s.indexOf('}')));
                    s = s.substring(0, s.indexOf('{'));
                    Alignment a = new Alignment(sequenceA.substring(coord[0]), sequenceB.substring(coord[1]));
                    int i = 0;
                    for (char c : s.toCharArray()) {
                        switch (c) {
                            case 'D':
                                if (a.sequenceA.charAt(i) == a.sequenceB.charAt(i)) {
                                    a.middle += "*";
                                } else {
                                    a.middle += ".";
                                }
                                break;
                            case 'L':
                                a.sequenceA = a.sequenceA.substring(0, i) + "-" + a.sequenceA.substring(i);
                                a.middle += " ";
                                break;
                            case 'U':
                                a.sequenceB = a.sequenceB.substring(0, i) + "-" + a.sequenceB.substring(i);
                                a.middle += " ";
                                break;
                        }
                        i++;
                    }
                    outAlign.add(a);
                }
                headIndex++;
            }
            int score = max;
            Result r = new Result();
            r.score = score;
            r.alignments = outAlign;
            r.fastaA = f1;
            r.fastaB = f2;
            return r;
        } else {
            Path[][] paths = fillMatrix(matrix, sequenceA, sequenceB);
            TreeNode node = new TreeNode();
            traceBack(paths, paths.length - 1, paths[0].length - 1, node);
            List<String> alignments = new LinkedList<>();
            getAlignment(node, "", alignments);
            List<Alignment> outAlign = new LinkedList<>();
            for (String s : alignments) {
                Alignment a = new Alignment(sequenceA, sequenceB);
                int i = 0;
                for (char c : s.toCharArray()) {
                    switch (c) {
                        case 'D':
                            if (a.sequenceA.charAt(i) == a.sequenceB.charAt(i)) {
                                a.middle += "*";
                            } else {
                                a.middle += ".";
                            }
                            break;
                        case 'L':
                            a.sequenceA = a.sequenceA.substring(0, i) + "-" + a.sequenceA.substring(i);
                            a.middle += " ";
                            break;
                        case 'U':
                            a.sequenceB = a.sequenceB.substring(0, i) + "-" + a.sequenceB.substring(i);
                            a.middle += " ";
                            break;
                    }
                    i++;
                }
                outAlign.add(a);
            }
            int score = matrix[matrix.length - 1][matrix[0].length - 1];
            Result r = new Result();
            r.score = score;
            r.alignments = outAlign;
            r.fastaA = f1;
            r.fastaB = f2;
            return r;
        }
    }

    private String getAlignment(TreeNode node, String alignment, List<String> alignments) {
        if (node.direction != null) {
            switch (node.direction) {
                case DIAGONAL:
                    alignment = "D" + alignment;
                    break;
                case LEFT:
                    alignment = "L" + alignment;
                    break;
                case UP:
                    alignment = "U" + alignment;
                    break;
            }
        }
        if (node.children.size() == 0) {
            alignments.add(alignment + "{" + node.col + "," + node.row + "}");
        } else {
            for (TreeNode n : node.children) {
                getAlignment(n, alignment, alignments);
            }
        }
        return alignment;
    }

    private void traceBack(Path[][] paths, int col, int row, TreeNode parent) {
        Path dir = paths[col][row];
        System.out.println(col + " " + row);
        TreeNode n;
        if (dir == null) {
            return;
        }
        if (dir.diagonal) {
            n = new TreeNode(Direction.DIAGONAL, col, row);
            parent.children.add(n);
            traceBack(paths, col - 1, row - 1, n);
        }
        if (dir.left) {
            n = new TreeNode(Direction.LEFT, col, row);
            parent.children.add(n);
            traceBack(paths, col - 1, row, n);
        }
        if (dir.up) {
            n = new TreeNode(Direction.UP, col, row);
            parent.children.add(n);
            traceBack(paths, col, row - 1, n);
        }
    }

    public Path[][] fillLocalMatrix(int[][] matrix, String seqA, String seqB) {
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
                if (max >= 0) {
                    if (max == diagonal) {
                        dir.diagonal = true;
                    }
                    if (max == left) {
                        dir.left = true;
                    }
                    if (max == up) {
                        dir.up = true;
                    }
                }

                paths[i][j] = dir;
                matrix[i][j] = max < 0 ? 0 : max;
            }
        }
        return paths;
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
        return scoringMatrix.getScore(a, b);
    }

}

class Coordinate {
    int row;
    int col;

    public Coordinate(int col, int row) {
        this.row = row;
        this.col = col;
    }
}