import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tan on 12/4/16.
 */
public class ScoringMatrixTest {
    @Test
    public void hash() {
        ScoringMatrix s = new ScoringMatrix() {
        };
        assertEquals(7, s.hash('G'));
    }
}