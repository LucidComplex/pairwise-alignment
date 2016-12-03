import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tan on 12/3/16.
 */
public class FastaFormatCheckerTest {
    @Test
    public void checkFormat() {
        String validFasta = "> PROTEIN ID | TEST SEQUENCE\nACTGATCGGATCGATACA";
        assertTrue(FastaFormatChecker.check(validFasta));
    }

    @Test
    public void invalidFormat() {
        String invalidFasta = "> PROTEIN \n\nACTGAC";
        assertFalse(FastaFormatChecker.check(invalidFasta));
    }
}