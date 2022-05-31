package systematic.section40_SpiralOrder;

/**
 * @Author: duccio
 * @Date: 31, 05, 2022
 * @Description: Print a given matrix zigzag.
 * @Note:   1. Have macro-view.
 *          2. Think about use multiple coordinates to represent one variate.
 *          3. Notice the change ordering of coordinates.
 */
public class Code04_ZigZagPrintMatrix {

    public static void printZigzag(int[][] matrix) {
        int r1 = 0;
        int c1 = 0;
        int r2 = 0;
        int c2 = 0;
        boolean down = false;
        while (r1 < matrix.length) {
            printDiagonal(matrix, r1, c1, r2, c2, down);
            // change row then column
            r1 = c1 == matrix[0].length - 1 ? r1 + 1 : r1;
            c1 = c1 == matrix[0].length - 1 ? c1 : c1 + 1;
            // change column then row
            c2 = r2 == matrix.length - 1 ? c2 + 1 : c2;
            r2 = r2 == matrix.length - 1 ? r2 : r2 + 1;
            down = !down;
        }
        System.out.println();
    }

    public static void printDiagonal(int[][] m, int r1, int c1, int r2, int c2, boolean down) {
        if (down) {
            for (int i = 0; i <= r2 - r1; i++) {
                System.out.print(m[r1 + i][c1 - i] + " ");
            }
        } else {
            for (int i = 0; i <= r2 - r1; i++) {
                System.out.print(m[r2 - i][c2 + i] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        printZigzag(matrix);
    }
}
