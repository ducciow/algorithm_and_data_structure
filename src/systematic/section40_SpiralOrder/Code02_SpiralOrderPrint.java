package systematic.section40_SpiralOrder;

/**
 * @Author: duccio
 * @Date: 31, 05, 2022
 * @Description: Print a matrix in spiral order.
 * @Note:   Have macro-view, from outer circle to inner.
 */
public class Code02_SpiralOrderPrint {

    public static void spiralOrderPrint(int[][] m) {
        int a = 0;
        int b = 0;
        int c = m.length - 1;
        int d = m[0].length - 1;
        while (a <= c && b <= d) {
            printCircle(m, a++, b++, c--, d--);
        }
    }

    public static void printCircle(int[][] m, int a, int b, int c, int d) {
        if (a == c) {
            for (int i = b; i <= d; i++) {
                System.out.print(m[a][i] + " ");
            }
        } else if (b == d) {
            for (int i = a; i <= c; i++) {
                System.out.print(m[i][b] + " ");
            }
        } else {
            for (int i = b; i < d; i++) {
                System.out.print(m[a][i] + " ");
            }
            for (int i = a; i < c; i++) {
                System.out.print(m[i][d] + " ");
            }
            for (int i = d; i > b; i--) {
                System.out.print(m[c][i] + " ");
            }
            for (int i = c; i > a; i--) {
                System.out.print(m[i][b] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12},
                {13, 14, 15, 16}};
        spiralOrderPrint(matrix);

    }

}
