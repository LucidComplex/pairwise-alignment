import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tan on 12/3/16.
 */
public class FastaFormatCheckerTest {
    @Test
    public void checkFormat() {
        String validFasta = "> PROTEIN ID | TEST SEQUENCE\nACTGATCGGATCGATACA";
        assertTrue(FastaFormatChecker.check(FastaFormatChecker.NUCLEOTIDE, validFasta));
    }

    @Test
    public void invalidFormat() {
        String invalidFasta = "> PROTEIN \n\nACTGAC";
        assertFalse(FastaFormatChecker.check(FastaFormatChecker.NUCLEOTIDE, invalidFasta));
    }

    @Test
    public void checkProtein() {
        String validProtein = "> PROTEIN\nVFTELSPAKTV";
        assertTrue(FastaFormatChecker.check(FastaFormatChecker.PROTEIN, validProtein));
    }

    @Test
    public void invalidProtein() {
        String validProtein = "> PROTEIN\nVFTULSPAKTV";
        assertFalse(FastaFormatChecker.check(FastaFormatChecker.PROTEIN, validProtein));
    }
}