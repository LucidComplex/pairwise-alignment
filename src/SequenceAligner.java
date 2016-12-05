/**
 * Created by rl-14 on 12/5/16.
 */
public class SequenceAligner {
    public static int max(int... values) {
        int max = values[0];
        for (int i = 1; i < values.length; i++) {
            if (max < values[i]) {
                max = values[i];
            }
        }
        return max;
    }
}
