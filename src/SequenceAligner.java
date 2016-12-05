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

    private static int[][] createMatrix(int col, int row) {
        int[][] matrix = new int[col][row];
        return matrix;
    }

    public static int[][] createGlobalMatrix(int col, int row, int gapScore) {
        int[][] matrix = createMatrix(col, row);
        for (int i = 0; i < matrix[0].length; i++) {
            matrix[0][i] = gapScore * i;
        }
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = gapScore * i;
        }
        return matrix;
    }

    public static int[][] createLocalMatrix(int col, int row) {
        return createMatrix(col, row);
    }
}
