import fasta.Fasta;
import fasta.FastaFormatter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tan on 12/3/16.
 */
public class FastaFormatterTest {
    @Test
    public void checkFormat() {
        String validFasta = "> PROTEIN ID | TEST SEQUENCE\nACTGATCGGATCGATACA";
        assertTrue(FastaFormatter.check(Fasta.NUCLEOTIDE, validFasta));
    }

    @Test
    public void invalidFormat() {
        String invalidFasta = "PROTEIN \n\nACTGAC";
        assertFalse(FastaFormatter.check(Fasta.NUCLEOTIDE, invalidFasta));
    }

    @Test
    public void invalidNucleotide() {
        String invalidNuc = "> NUCLEOTIDE????\nACTAGATCV";
        assertFalse(FastaFormatter.check(Fasta.NUCLEOTIDE, invalidNuc));
    }

    @Test
    public void checkProtein() {
        String validProtein = "> PROTEIN\nVFTELSPAKTV\nPAKTV";
        assertTrue(FastaFormatter.check(Fasta.PROTEIN, validProtein));
    }

    @Test
    public void invalidProtein() {
        String validProtein = "> PROTEIN\nVFTULSPAKTV";
        assertFalse(FastaFormatter.check(Fasta.PROTEIN, validProtein));
    }

    @Test
    public void format() {
        String protein = "> PROTEIN TEST\nVFTELSPAKTV";
        Fasta fasta = FastaFormatter.format(Fasta.PROTEIN, protein);
        assertEquals("VFTELSPAKTV", fasta.getSequence());
    }

    @Test
    public void invalidFormatting() {
        String protein = "! PROTEEEEIN\nVFTELFSP";
        Fasta fasta = FastaFormatter.format(Fasta.PROTEIN, protein);
        assertNull(fasta);
    }
}