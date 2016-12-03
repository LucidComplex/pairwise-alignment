import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by tan on 12/3/16.
 */
public class FastaFormatChecker {

    public static boolean check(String fasta) {
        String[] fastaLines = fasta.split("\n");
        if (!fastaLines[0].startsWith(">")) {
            return false;
        } else {
            for (int i = 1; i < fastaLines.length; i++) {
                if (!fastaLines[i].matches("(A|C|T|G)+")) {
                    return false;
                }
            }
        }
        return true;
    }
}
