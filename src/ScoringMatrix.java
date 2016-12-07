import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by tan on 12/4/16.
 */
public class ScoringMatrix {
    protected int[][] matrix;
    protected final String bank = "ARNDCQEGHILKMFPSTWYVBZX*";
    public String name;

    public ScoringMatrix() {

    }

    public ScoringMatrix(String fileName) throws IOException {
        BufferedReader bReader = new BufferedReader(new FileReader(new File(fileName)));
        name = fileName;
        String line;
        line = bReader.readLine();
        String[] labels = line.trim().split("  ");
        int[] values = new int[labels.length];
        matrix = new int[labels.length][labels.length];
        int i = 0;
        int h;
        int colIndex;
        while ((line = bReader.readLine()) != null) {
            String[] tempValues = line.substring(2).split(" ");
            h = 0;
            for (String s : tempValues) {
                if (s.length() > 0) {
                    values[h++] = Integer.valueOf(s);
                }
            }
            colIndex = hash(labels[i].charAt(0));
            for (int j = 0; j < values.length; j++) {
                matrix[colIndex][hash(labels[j].charAt(0))] = values[j];
            }
            i++;
        }
    }

    public int getScore(char col, char row) {
        return matrix[hash(col)][hash(row)];
    }

    int hash(char c) {
        return bank.indexOf(c);
    }
}
