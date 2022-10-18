package systematic.section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 25, 04, 2022
 * @Description: Knapsack problem, of which all items have non-negative weights and values.
 * @Note:   Ver1. Brute force.
 *                - base case 1: capacity < 0.
 *                - base case 2: no item is left.
 *          Ver2. DP.
 *                - base case 1 is equivalent to boundary checking when fill in cells.
 *                - although table row's range is in [0, N-1], the actual table has N+1 rows that involves base case 2.
 *          ======
 *          Attempt from left to right, ie. discard or bring the current item.
 */
public class Code03_Knapsack {

    public static void main(String[] args) {
        int[] weights = {3, 2, 4, 7, 3, 1, 7};
        int[] values = {5, 6, 3, 19, 12, 4, 2};
        int capacity = 15;
        System.out.println(knapsack1(weights, values, capacity));
        System.out.println(knapsack2(weights, values, capacity));
    }

    public static int knapsack1(int[] weights, int[] values, int capacity) {
        if (weights == null || values == null || weights.length != values.length) {
            return 0;
        }
        return process1(weights, values, 0, capacity);
    }

    public static int process1(int[] weights, int[] values, int idx, int capacity) {
        if (capacity < 0) {
            return -1;
        }
        if (idx == weights.length) {
            return 0;
        }
        int option1 = process1(weights, values, idx + 1, capacity);
        int option2 = 0;
        int next = process1(weights, values, idx + 1, capacity - weights[idx]);
        if (next != -1) {
            option2 = values[idx] + next;
        }
        return Math.max(option1, option2);
    }

    public static int knapsack2(int[] weights, int[] values, int capacity) {
        if (weights == null || values == null || weights.length != values.length) {
            return 0;
        }
        int[][] dp = new int[weights.length + 1][capacity + 1];
        for (int idx = weights.length - 1; idx >= 0; idx--) {
            for (int cap = 0; cap <= capacity; cap++) {
                int option1 = dp[idx + 1][cap];
                int option2 = cap - weights[idx] >= 0 ? values[idx] + dp[idx + 1][cap - weights[idx]] : 0;
                dp[idx][cap] = Math.max(option1, option2);
            }
        }
        return dp[0][capacity];
    }

}
