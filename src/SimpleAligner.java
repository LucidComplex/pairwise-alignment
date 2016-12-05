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
        return 0;
    }

    public void fillMatrix(int[][] matrix, String seqA, String seqB) {
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                matrix[i][j] = SequenceAligner.max(
                        matrix[i - 1][j - 1] + score(seqA.charAt(i - 1), seqB.charAt(j - 1)),
                        matrix[i][j - 1] + gap,
                        matrix[i - 1][j] + gap);
            }
        }
    }

    private int score(char a, char b) {
        return a == b ? match : mismatch;
    }

}
