package systematic.section38_CatalanNumber;

/**
 * @Author: duccio
 * @Date: 26, 05, 2022
 * @Description: Given the number of different binary tree structures for n nodes.
 * @Note:   The first definition equation of Catalan Number: 0 left node and n-1 right nodes + 1 left node and n-2 right
 *              nodes + ... + n-1 left nodes and 0 right nodes.
 */
public class Code02_DifferentBT {

    public static void main(String[] args) {
        System.out.println(catalan(101));
    }

    public static long catalan(int n) {
        if (n < 0) {
            return 0;
        }
        if (n < 2) {
            return 1;
        }
        long[] dp = new long[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int k = 2; k <= n; k++) {
            for (int i = 0; i < k; i++) {
                dp[k] += dp[i] * dp[k - 1 - i];
            }
        }
        return dp[n];
    }

}
