package systematic.section40_SpiralOrder;

/**
 * @Author: duccio
 * @Date: 31, 05, 2022
 * @Description: Print stars in a pre-defined pattern.
 * @Note:   Have macro-view.
 */
public class Code03_SpiralOrderPrintStar {

    public static void printPattern(int N) {
        char[][] m = new char[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                m[i][j] = ' ';
            }
        }
        int topLeft = 0;
        int bottomRight = N - 1;
        while (topLeft <= bottomRight) {
            setStar(m, topLeft, bottomRight);
            topLeft += 2;
            bottomRight -= 2;
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void setStar(char[][] m, int lower, int upper) {
        for (int col = lower; col <= upper; col++) {
            m[lower][col] = '*';
        }
        for (int row = lower + 1; row <= upper; row++) {
            m[row][upper] = '*';
        }
        for (int col = upper - 1; col > lower; col--) {
            m[upper][col] = '*';
        }
        for (int row = upper - 1; row > lower + 1; row--) {
            m[row][lower + 1] = '*';
        }
    }

    public static void main(String[] args) {
        printPattern(5);
    }

}
