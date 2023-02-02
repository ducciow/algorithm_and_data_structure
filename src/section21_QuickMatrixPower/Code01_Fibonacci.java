package section21_QuickMatrixPower;

/**
 * @Author: duccio
 * @Date: 05, 05, 2022
 * @Description: Solve Fibonacci problem in O(logN).
 * @Note:   Fibonacci pattern appears in many scenarios, like rabbit birth.
 */
public class Code01_Fibonacci {

    public static void main(String[] args) {
        System.out.println(fib(23));
        System.out.println(naiveFib(23));
    }

    public static int fib(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        int[][] M = {
                {1, 1},
                {1, 0}}
        ;
        // [f(n), f(n-1)] = [f_2, f_1] * M**(n-2)
        //                = [1, 1] * res
        int[][] res = matPow(M, n - 2);
        return res[0][0] + res[1][0];
    }

    public static int[][] matPow(int[][] M, int n) {
        int[][] ans = new int[M.length][M[0].length];
        // identity matrix
        for (int i = 0; i < M.length; i++) {
            ans[i][i] = 1;
        }
        int[][] Tmp = M;
        while (n > 0) {
            if ((n & 1) != 0) {
                ans = matMult(ans, Tmp);  // ans only updates when this(last) digit is 1
            }
            Tmp = matMult(Tmp, Tmp);  // Tmp updates in every iteration
            n >>= 1;
        }
        return ans;
    }

    public static int[][] matMult(int[][] M1, int[][] M2) {
        if (M1[0].length != M2.length) {
            return null;
        }
        int row = M1.length;
        int col = M2[0].length;
        int[][] ans = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                for (int k = 0; k < M1[0].length; k++) {
                    ans[i][j] += M1[i][k] * M2[k][j];
                }
            }
        }
        return ans;
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
