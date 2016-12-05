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
        fillMatrix(matrix, sequenceA, sequenceB);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%d ", matrix[i][j]);
            }
            System.out.println();
        }
        int score = matrix[matrix.length - 1][matrix[0].length - 1];
        return score;
    }

    public Direction[][] fillMatrix(int[][] matrix, String seqA, String seqB) {
        Direction[][] directionMatrix = new Direction[matrix.length][matrix[0].length];
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                int diagonal = matrix[i - 1][j - 1] + score(seqA.charAt(i - 1), seqB.charAt(j - 1));
                int up = matrix[i][j - 1] + gap;
                int left = matrix[i - 1][j] + gap;
                int max = diagonal;
                Direction direction = Direction.DIAGONAL;
                if (max < up) {
                    max = up;
                    direction = Direction.UP;
                }
                if (max < left) {
                    max = left;
                    direction = Direction.LEFT;
                }

                matrix[i][j] = max;
                directionMatrix[i][j] = direction;
            }
        }
        return directionMatrix;
    }

    private int score(char a, char b) {
        return a == b ? match : mismatch;
    }

}

enum Direction{
    DIAGONAL, UP, LEFT;
}