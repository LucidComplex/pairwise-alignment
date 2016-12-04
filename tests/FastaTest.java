import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by tan on 12/4/16.
 */
public class FastaTest {
    @Test
    public void sequenceLength() {
        Fasta fasta = new Fasta();
        fasta.setSequence("AVFL");
        assertEquals(4, fasta.length());
    }

    @Test
    public void frequency() {
        Fasta fasta = new Fasta();
        fasta.setSequence("AVFLLCFACTVA");
        Map<Character, Integer> frequencyMap = fasta.getFrequencies();
        assertEquals(3, (int) frequencyMap.get('A'));
    }
}