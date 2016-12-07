/**
 * Created by tan on 12/8/16.
 */
public class Alignment {
    String sequenceA;
    String sequenceB;
    String middle;

    public Alignment() {
        middle = "";
    }

    public Alignment(String sequenceA, String sequenceB) {
        this();
        this.sequenceA = sequenceA;
        this.sequenceB = sequenceB;
    }

    public String toString() {
        return sequenceA + "\n" + middle + "\n" + sequenceB;
    }
}
