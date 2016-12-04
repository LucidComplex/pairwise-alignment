import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tan on 12/3/16.
 */
public class FastaFormatterTest {
    @Test
    public void checkFormat() {
        String validFasta = "> PROTEIN ID | TEST SEQUENCE\nACTGATCGGATCGATACA";
        assertTrue(FastaFormatter.check(FastaFormatter.NUCLEOTIDE, validFasta));
    }

    @Test
    public void invalidFormat() {
        String invalidFasta = "> PROTEIN \n\nACTGAC";
        assertFalse(FastaFormatter.check(FastaFormatter.NUCLEOTIDE, invalidFasta));
    }

    @Test
    public void checkProtein() {
        String validProtein = "> PROTEIN\nVFTELSPAKTV\nPAKTV";
        assertTrue(FastaFormatter.check(FastaFormatter.PROTEIN, validProtein));
    }

    @Test
    public void invalidProtein() {
        String validProtein = "> PROTEIN\nVFTULSPAKTV";
        assertFalse(FastaFormatter.check(FastaFormatter.PROTEIN, validProtein));
    }

    @Test
    public void format() {
        String protein = "> PROTEIN TEST\nVFTULSPAKTV";
        Fasta fasta = FastaFormatter.format(FastaFormatter.PROTEIN, protein);
    }
}