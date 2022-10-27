package section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 25, 04, 2022
 * @Description: Given two strings str1 and str2, return the length of their longest common subsequence. A subsequence
 *      of a string is a new string generated from the original string with some characters (can be none) deleted
 *      without changing the relative order of the remaining characters, eg, "ace" is a subsequence of "abcde".
 *      https://leetcode.com/problems/longest-common-subsequence/
 * @Note:   Ver1. Brute force. (exceed time limit)
 *          Ver2. DP.
 *          ======
 *          - Varying arguments are idx1 and idx2 backwards, ie., both from length-1 to 0. So that the base case is
 *            when both idx1 and idx2 are 0.
 *          - At each processing step, there are three possibilities, and they are not disjoint. Maximization
 *            among them did not affect the answer.
 *          ======
 *          Sample correspondence based on respective positions.
 */
public class Code06_LongestCommonSubsequence {

    public int longestCommonSubsequence1(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() == 0 || str2.length() == 0) {
            return 0;
        }
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        return process1(chars1, chars2, chars1.length - 1, chars2.length - 1);
    }

    public static int process1(char[] chars1, char[] chars2, int idx1, int idx2) {
        if (idx1 == 0 && idx2 == 0) {
            return chars1[0] == chars2[0] ? 1 : 0;
        } else if (idx1 == 0) {
            return chars1[0] == chars2[idx2] ? 1 : process1(chars1, chars2, idx1, idx2 - 1);
        } else if (idx2 == 0) {
            return chars1[idx1] == chars2[0] ? 1 : process1(chars1, chars2, idx1 - 1, idx2);
        } else {
            int option1 = process1(chars1, chars2, idx1 - 1, idx2);
            int option2 = process1(chars1, chars2, idx1, idx2 - 1);
            int option3 = chars1[idx1] == chars2[idx2] ?
                    1 + process1(chars1, chars2, idx1 - 1, idx2 - 1) : 0;
            return Math.max(Math.max(option1, option2), option3);
        }
    }

    public int longestCommonSubsequence2(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() < 1 || str2.length() < 1) {
            return 0;
        }
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        int M = chars1.length;
        int N = chars2.length;
        int[][] dp = new int[M][N];
        if (chars1[0] == chars2[0]) {
            dp[0][0] = 1;
        }
        for (int idx2 = 1; idx2 < N; idx2++) {
            dp[0][idx2] = chars1[0] == chars2[idx2] ? 1 : dp[0][idx2 - 1];
        }
        for (int idx1 = 1; idx1 < M; idx1++) {
            dp[idx1][0] = chars1[idx1] == chars2[0] ? 1 : dp[idx1 - 1][0];
        }
        for (int idx1 = 1; idx1 < M; idx1++) {
            for (int idx2 = 1; idx2 < N; idx2++) {
                int option1 = dp[idx1 - 1][idx2];
                int option2 = dp[idx1][idx2 - 1];
                int option3 = chars1[idx1] == chars2[idx2] ? 1 + dp[idx1 - 1][idx2 - 1] : 0;
                dp[idx1][idx2] = Math.max(Math.max(option1, option2), option3);
            }
        }
        return dp[M - 1][N - 1];
    }

}
