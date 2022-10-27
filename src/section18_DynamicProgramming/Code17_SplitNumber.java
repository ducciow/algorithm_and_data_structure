package section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 29, 04, 2022
 * @Description: Given a positive integer, return the number of ways splitting it into an array of positive integers,
 *      requiring that former values in the array cannot be bigger than later values. Eg., 3 -> {1, 1, 1}, {1, 2}, {3}.
 * @Note:   Ver1. brute force.
 *          Ver2. DP with loop for a cell.
 *          Ver3. DP without loop for a cell.
 *          ======
 *          1. In this problem, the base case sufficient for brute force is not enough for dependent calculation.
 *          2. When looking for dependent cells, it might get extra observations, typically regarding boundaries that
 *             are required for dependent calculation.
 */
public class Code17_SplitNumber {

    public static void main(String[] args) {
        int test = 27;
        System.out.println(ways1(test));
        System.out.println(ways2(test));
        System.out.println(ways3(test));
    }

    public static int ways1(int num) {
        if (num == 1) {
            return 1;
        }
        return process1(1, num);
    }

    public static int process1(int floor, int num) {
        if (num == 0) {
            return 1;
        }
        int ans = 0;
        for (int cur = floor; cur <= num; cur++) {
            ans += process1(cur, num - cur);
        }
        return ans;
    }

    public static int ways2(int num) {
        if (num == 1) {
            return 1;
        }
        int[][] dp = new int[num + 1][num + 1];
        for (int i = 1; i <= num; i++) {
            dp[i][0] = 1;
        }
        for (int i = num; i > 0; i--) {
            for (int j = 1; j <= num; j++) {
                int ans = 0;
                for (int cur = i; cur <= j; cur++) {
                    ans += dp[cur][j - cur];
                }
                dp[i][j] = ans;
            }
        }
        return dp[1][num];
    }

    public static int ways3(int num) {
        if (num == 1) {
            return 1;
        }
        int[][] dp = new int[num + 1][num + 1];
        for (int i = 1; i <= num; i++) {
            dp[i][0] = 1;
        }
        dp[num][num] = 1;  // an extra fact regarding the last row, found during looking for dependent cell
        for (int i = num - 1; i > 0; i--) {
            for (int j = 1; j <= num; j++) {
                dp[i][j] = dp[i + 1][j];
                if (j - i >= 0) {
                    dp[i][j] += dp[i][j - i];
                }
            }
        }
        return dp[1][num];
    }

}
