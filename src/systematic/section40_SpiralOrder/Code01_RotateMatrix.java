package systematic.section40_SpiralOrder;

/**
 * @Author: duccio
 * @Date: 31, 05, 2022
 * @Description: Given a square matrix, rotate every element by 90 degrees clockwise.
 * @Note:   Have macro-view.
 */
public class Code01_RotateMatrix {

    public static void rotate(int[][] m) {
        int r1 = 0;
        int c1 = 0;
        int r2 = m.length - 1;
        int c2 = m[0].length - 1;
        while (r1 < r2) {
            rotateCircle(m, r1++, c1++, r2--, c2--);
        }
    }

    public static void rotateCircle(int[][] m, int a, int b, int c, int d) {
        for (int i = 0; i < c - a; i++) {
            int tmp = m[a][b + i];
            m[a][b + i] = m[c - i][b];
            m[c - i][b] = m[c][d - i];
            m[c][d - i] = m[a + i][d];
            m[a + i][d] = tmp;
        }
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}};
        printMatrix(matrix);
        System.out.println("=========");
        rotate(matrix);
        printMatrix(matrix);

    }

}
