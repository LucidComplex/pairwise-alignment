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
}