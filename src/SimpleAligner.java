import fasta.Fasta;

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
        Direction[][] directions = fillMatrix(matrix, sequenceA, sequenceB);
        int score = matrix[matrix.length - 1][matrix[0].length - 1];
        return score;
    }

    public Direction[][] fillMatrix(int[][] matrix, String seqA, String seqB) {
        Direction[][] directions = new Direction[matrix.length][matrix[0].length];
        Direction dir;
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                dir = new Direction();
                int diagonal = matrix[i - 1][j - 1] + score(seqA.charAt(i - 1), seqB.charAt(j - 1));
                int up = matrix[i][j - 1] + gap;
                int left = matrix[i - 1][j] + gap;
                int max = diagonal;
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

                directions[i][j] = dir;
                matrix[i][j] = max;
            }
        }
        return directions;
    }

    private int score(char a, char b) {
        return a == b ? match : mismatch;
    }

}

class Direction {
    boolean up, left, diagonal;
}