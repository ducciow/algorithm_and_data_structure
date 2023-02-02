package section21_QuickMatrixPower;

/**
 * @Author: duccio
 * @Date: 05, 05, 2022
 * @Description: A string of '0's and '1's is valid if for every '0', its left is '1'. Given an integer N, return the
 *          number of valid strings of length N.
 * @Note:   - The 1st char must be '1'. Given that, for each char, thinking the former char is '1', and there leaves
 *            two choices, '1...' or '01...' . So f(n) = f(n-1) + f(n-2), which is a Fibonacci starting from 1 and 2.
 */
public class Code03_ZeroLeftMustBeOneString {

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            System.out.println(validStr(i));
            System.out.println(naiveValidStr(i));
            System.out.println("===================");
        }
    }

    private static int validStr(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int[][] M = {{1, 1}, {1, 0}};
        // here asks for f(n+1), since it starts from 1, 2 instead of 1, 1
        int[][] res = matPow(M, n - 1);
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


    private static int naiveValidStr(int n) {
        if (n < 1) {
            return 0;
        }
        return process(1, n);
    }

    public static int process(int idx, int n) {
        if (idx == n) {
            return 1;
        }
        if (idx == n - 1) {
            return 2;
        }
        int p1 = process(idx + 1, n);
        int p2 = process(idx + 2, n);
        return p1 + p2;
    }

}
