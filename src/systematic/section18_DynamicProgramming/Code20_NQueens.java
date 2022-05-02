package systematic.section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 02, 05, 2022
 * @Description: N-Queens problem
 * @Note:   Ver1. naive brute force.
 *          Ver2. use bit representation reducing constant operation time.
 *          ======
 *          N-Queens cannot be converted to DP.
 */
public class Code20_NQueens {

    public static void main(String[] args) {
        int n = 13;

        long start = System.currentTimeMillis();
        System.out.println(ways1(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(ways2(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");
    }

    public static int ways1(int N) {
        if (N < 1) {
            return 0;
        }
        int[] pos = new int[N];
        return process1(N, 0, pos);
    }

    public static int process1(int N, int idx, int[] pos) {
        if (idx == N) {
            return 1;
        }
        int ans = 0;
        for (int j = 0; j < N; j++) {
            if (isValid(pos, idx, j)) {
                pos[idx] = j;
                ans += process1(N, idx + 1, pos);
            }
        }
        return ans;
    }

    private static boolean isValid(int[] pos, int idx, int j) {
        for (int i = 0; i < idx; i++) {
            if (j == pos[i] || Math.abs(idx - i) == Math.abs(j - pos[i])) {
                return false;
            }
        }
        return true;
    }


    public static int ways2(int N) {
        if (N < 1 || N > 32) {
            return 0;
        }
        int limit = N == 32 ? -1 : (1 << N) - 1;
        return process2(limit, 0, 0, 0);
    }

    /**
     * @param limit      eg., 00...011111 means 5-Queens problem.
     * @param colX       column restriction from previous rows
     * @param leftDiagX  left diagonal restriction from previous rows
     * @param rightDiagX right diagonal restriction from previous rows
     * @return number of ways
     */
    public static int process2(int limit, int colX, int leftDiagX, int rightDiagX) {
        if (colX == limit) {
            return 1;
        }
        int ans = 0;
        // digit 1 in pos means valid
        int pos = limit & (~(colX | leftDiagX | rightDiagX));
        int mostRightOne = 0;
        while (pos != 0) {
            mostRightOne = pos & (~pos + 1);
            pos -= mostRightOne;
            int colX_ = colX | mostRightOne;
            int leftDiagX_ = (leftDiagX | mostRightOne) << 1;
            int rightDiagX_ = (rightDiagX | mostRightOne) >>> 1;
            ans += process2(limit, colX_, leftDiagX_, rightDiagX_);
        }
        return ans;
    }

}
