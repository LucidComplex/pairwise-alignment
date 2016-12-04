import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;

/**
 * Created by tan on 12/4/16.
 */
public class Fasta {
    private String sequence;

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public int length() {
        return sequence.length();
    }

    public Map<Character, Integer> getFrequencies() {
        Map<Character, Integer> frequencies = new HashMap<>();
        for (Character c : sequence.toCharArray()) {
            frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
        }
        return frequencies;
    }
}
