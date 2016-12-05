package fasta;

/**
 * Created by tan on 12/3/16.
 */
public class FastaFormatter {

    public static boolean check(int type, String fasta) { // TODO: 12/5/16  make private
        String[] fastaLines = fasta.split("\n");
        if (!fastaLines[0].startsWith(">")) {
            return false;
        } else {
            for (int i = 1; i < fastaLines.length; i++) {
                if (type == Fasta.PROTEIN) {
                    if (!fastaLines[i].matches("[ARNDCEQGHILKMFPSTWYV]+")) {
                        return false;
                    }
                } else if (type == Fasta.NUCLEOTIDE) {
                    if (!fastaLines[i].matches("[ACTG]+")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static Fasta format(int type, String fasta) {
        if (check(type, fasta)) {
            String[] fastaLines = fasta.split("\n");
            String id = fastaLines[0];
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < fastaLines.length; i++) {
                builder.append(fastaLines[i]);
            }
            return new Fasta(type, id, builder.toString());
        }
        return null;
    }
}
