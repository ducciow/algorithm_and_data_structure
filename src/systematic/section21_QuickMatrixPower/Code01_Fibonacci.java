package systematic.section21_QuickMatrixPower;

/**
 * @Author: duccio
 * @Date: 05, 05, 2022
 * @Description: Solve Fibonacci problem in O(logN)
 * @Note:   Fibonacci pattern appears in many scenarios, like rabbit birth.
 */
public class Code01_Fibonacci {

    public static void main(String[] args) {
        System.out.println(fib(19));
        System.out.println(naiveFib(19));
    }

    public static int fib(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        int[][] M = {
                {1, 1},
                {1, 0}}
        ;
        int[][] res = matPow(M, n - 2);
        return res[0][0] + res[1][0];
    }

    public static int[][] matPow(int[][] M, int n) {
        int[][] ret = new int[M.length][M[0].length];
        for (int i = 0; i < M.length; i++) {
            ret[i][i] = 1;
        }
        int[][] Tmp = M;
        while (n > 0) {
            if ((n & 1) != 0) {
                ret = matMult(ret, Tmp);
            }
            Tmp = matMult(Tmp, Tmp);
            n >>= 1;
        }
        return ret;
    }

    public static int[][] matMult(int[][] M1, int[][] M2) {
        if (M1[0].length != M2.length) {
            return null;
        }
        int row = M1.length;
        int col = M2[0].length;
        int[][] ret = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                for (int k = 0; k < M1[0].length; k++) {
                    ret[i][j] += M1[i][k] * M2[k][j];
                }
            }
        }
        return ret;
    }

    public static int naiveFib(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        int fpp = 1;
        int fp = 1;
        int ans = 0;
        for (int i = 2; i < n; i++) {
            ans = fpp + fp;
            fpp = fp;
            fp = ans;
        }
        return ans;
    }

}
