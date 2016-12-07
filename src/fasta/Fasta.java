package fasta;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tan on 12/4/16.
 */
public class Fasta {
    public static final int NUCLEOTIDE = 1;
    public static final int PROTEIN = 2;

    private String sequence;
    private int type;
    private String id;

    public Fasta(int type, String id, String sequence) {
        this.type = type;
        this.id = id;
        this.sequence = sequence;
    }

    public Fasta() {

    }

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

    public String getSequence() {
        return sequence;
    }

    public String getId() {
        return id;
    }
}
