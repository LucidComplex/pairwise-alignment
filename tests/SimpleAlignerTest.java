import fasta.Fasta;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by rl-14 on 12/5/16.
 */
public class SimpleAlignerTest {
    @Test
    public void matrixCreation() {
        SimpleAligner aligner = new SimpleAligner(5, -3, -4);
        Fasta f1 = new Fasta(Fasta.NUCLEOTIDE, "PROTEIN", "GGATCGA");
        Fasta f2 = new Fasta(Fasta.NUCLEOTIDE, "PROTEIN", "GAATTCAGTTA");
        int[][] matrix = SequenceAligner.createGlobalMatrix(f1.getSequence().length(), f2.getSequence().length(), -4);
        aligner.fillMatrix(matrix, f1.getSequence(), f2.getSequence());
        assertEquals(11, matrix[7][11]);
        assertEquals(6, matrix[6][10]);
    }
}