import java.util.HashMap;

/**
 * Created by tan on 12/4/16.
 */
public abstract class ScoringMatrix {
    protected int[][] matrix;
    protected final String bank = "ARNDCQEGHILKMFPSTWYVBZX*";

    public int getScore(char col, char row) {
        return 0;
    }

    int hash(char c) {
        return bank.indexOf(c);
    }
}
