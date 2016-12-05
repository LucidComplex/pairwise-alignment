import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by tan on 12/4/16.
 */
public class ScoringMatrixTest {
    @Test
    public void hash() {
        ScoringMatrix s = new ScoringMatrix();
        assertEquals(7, s.hash('G'));
    }

    @Test
    public void load() throws IOException {
        ScoringMatrix s = new ScoringMatrix("PAM120");
        assertEquals(1, s.matrix[6][2]);
    }

    @Test
    public void getScore() throws IOException {
        ScoringMatrix s = new ScoringMatrix("PAM120");
        assertEquals(-2, s.getScore('Q', 'S'));
    }
}