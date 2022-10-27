package section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 11, 06, 2022
 * @Description: There is a strange printer with the following two special properties: The printer can only print a
 *      sequence of the same character each time. At each turn, the printer can print new characters starting from and
 *      ending at any place and will cover the original existing characters. Given a string s, return the minimum
 *      number of turns the printer needed to print it.
 *      https://leetcode.com/problems/strange-printer/
 * @Note:   Key idea: 1. the first char costs one turn anyway.
 *                    2. there exists one position, where any turn does not cross it.
 */
public class LaterCode01_StrangePrinter {

    public static int printer1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        return process1(chars, 0, chars.length - 1);
    }

    public static int process1(char[] chars, int L, int R) {
        if (L == R) {
            return 1;
        }
        int ans = R - L + 1;
        for (int split = L + 1; split <= R; split++) {
            int next = process1(chars, L, split - 1) + process1(chars, split, R)
                    - (chars[L] == chars[split] ? 1 : 0);  // important
            ans = Math.min(ans, next);
        }
        return ans;
    }

    public static int printer2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int N = chars.length;
        int[][] lookup = new int[N][N];
        return process2(chars, 0, N - 1, lookup);
    }

    private static int process2(char[] chars, int L, int R, int[][] lookup) {
        if (lookup[L][R] != 0) {
            return lookup[L][R];
        }
        int ans = R - L + 1;
        if (L == R) {
            ans = 1;
        } else {
            for (int split = L + 1; split <= R; split++) {
                int next = process2(chars, L, split - 1, lookup) + process2(chars, split, R, lookup)
                        - (chars[L] == chars[split] ? 1 : 0);
                ans = Math.min(ans, next);
            }
        }
        lookup[L][R] = ans;
        return ans;
    }

    public static int printer3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] chars = s.toCharArray();
        int N = chars.length;
        int[][] dp = new int[N][N];
        for (int i = 0; i < N; i++) {
            dp[i][i] = 1;
        }
        for (int L = N - 2; L >= 0; L--) {
            for (int R = L + 1; R < N; R++) {
                dp[L][R] = R - L + 1;
                for (int split = L + 1; split <= R; split++) {
                    int next = dp[L][split - 1] + dp[split][R] - (chars[L] == chars[split] ? 1 : 0);
                    dp[L][R] = Math.min(dp[L][R], next);
                }
            }
        }
        return dp[0][N - 1];
    }

}
