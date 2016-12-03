import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by tan on 12/3/16.
 */
public class FastaFormatChecker {
    public static final int PROTEIN = 1;
    public static final int NUCLEOTIDE = 2;

    public static boolean check(int type, String fasta) {
        String[] fastaLines = fasta.split("\n");
        if (!fastaLines[0].startsWith(">")) {
            return false;
        } else {
            for (int i = 1; i < fastaLines.length; i++) {
                if (type == PROTEIN) {
                    if (!fastaLines[i].matches("[ARNDCEQGHILKMFPSTWYV]+")) {
                        return false;
                    }
                } else if (type == NUCLEOTIDE) {
                    if (!fastaLines[i].matches("[ACTG]+")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
