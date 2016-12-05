import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by rl-14 on 12/5/16.
 */
public class SequenceAlignerTest {
    @Test
    public void max() {
        int[] vals = {10, 25, 3, 8, 32, 91, 31};
        assertEquals(91, SequenceAligner.max(vals));
    }

    @Test
    public void createMatrix() {
        int[][] matrix = SequenceAligner.createGlobalMatrix(5, 3, -3);
        for (int i = 0; i < matrix.length; i++) {
            assertEquals(-3 * i, matrix[i][0]);
        }
    }

    @Test
    public void createLocalMatrix() {
        int[][] matrix = SequenceAligner.createLocalMatrix(5, 3);
        for (int i = 0; i < matrix.length; i++) {
            assertEquals(0, matrix[i][0]);
        }
    }

}