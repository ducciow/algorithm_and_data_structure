package systematic.section37_TryAccordingToDataScale;

/**
 * @Author: duccio
 * @Date: 25, 05, 2022
 * @Description: Given two integer arrays: p[] -- power of monsters, and d[] -- money cost of absorbing monsters.
 *      Initially, your power is 0, and you can beat a monster if your power is higher, or absorb a monster's power
 *      with some cost. Return the minimum cost to beat or absorb all monsters.
 * @Note:   Ver1. One of the dp dimension is the sum of monster power, so it is not acceptable if the monster power
 *                is large.
 *          Ver2. One of the dp dimension is the sum of money cost, so it is not acceptable if the money cost is large.
 */
public class Code01_MonsterMoney {

    public static long naive1(int[] p, int[] d) {
        return process1(p, d, 0, 0);
    }

    // when you encounter the idx-th monster at your current level of power,
    // return the minimum cost it takes to conquer all monsters in the future.
    public static long process1(int[] p, int[] d, int idx, int power) {
        if (idx == p.length) {
            return 0;
        }
        if (power >= p[idx]) {
            return Math.min(process1(p, d, idx + 1, power),
                    d[idx] + process1(p, d, idx + 1, power + p[idx]));
        } else {
            return d[idx] + process1(p, d, idx + 1, power + p[idx]);
        }
    }

    public static long dp1(int[] p, int[] d) {
        int pSum = 0;
        for (int i : p) {
            pSum += i;
        }
        int N = p.length;
        long[][] dp = new long[N + 1][pSum + 1];
        for (int idx = N - 1; idx >= 0; idx--) {
            for (int power = 0; power <= pSum; power++) {
                if (power + p[idx] > pSum) {  // impossible situation
                    continue;
                }
                if (power >= p[idx]) {
                    dp[idx][power] = Math.min(dp[idx + 1][power],
                            d[idx] + dp[idx + 1][power + p[idx]]);
                } else {
                    dp[idx][power] = d[idx] + process1(p, d, idx + 1, power + p[idx]);
                }
            }
        }
        return dp[0][0];
    }


    public static long naive2(int[] p, int[] d) {
        long allMoney = 0;
        for (int i = 0; i < p.length; i++) {
            allMoney += d[i];
        }
        for (long money = 0; money < allMoney; money++) {
            if (process2(p, d, p.length - 1, money) != -1) {
                return money;
            }
        }
        return allMoney;
    }

    // When you encounter the idx-th monster already with money cost,
    // return the maximum power you can have for the current.
    public static long process2(int[] p, int[] d, int idx, long money) {
        if (idx == -1) {
            return money == 0 ? 0 : -1;
        }
        long p1 = -1;
        long power1 = process2(p, d, idx - 1, money);
        if (power1 != -1 && power1 >= p[idx]) {
            p1 = power1;
        }
        long p2 = -1;
        long power2 = process2(p, d, idx - 1, money - d[idx]);
        if (power2 != -1) {
            p2 = p[idx] + power2;
        }
        return Math.max(p1, p2);
    }

    public static long dp2(int[] p, int[] d) {
        int allMoney = 0;
        for (int i = 0; i < p.length; i++) {
            allMoney += d[i];
        }
        int N = p.length;
        long[][] dp = new long[N][allMoney + 1];
        // init all cells to be -1
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= allMoney; j++) {
                dp[i][j] = -1;
            }
        }
        dp[0][d[0]] = p[0];
        for (int idx = 1; idx < N; idx++) {
            for (int money = 0; money <= allMoney; money++) {
                long p1 = -1;
                long power1 = dp[idx - 1][money];
                if (power1 != -1 && power1 >= p[idx]) {
                    p1 = power1;
                }
                long p2 = -1;
                long power2 = money - d[idx] >= 0 ? dp[idx - 1][money - d[idx]] : -1;
                if (power2 != -1) {
                    p2 = p[idx] + power2;
                }
                dp[idx][money] = Math.max(p1, p2);
            }
        }
        for (int money = 0; money < allMoney; money++) {
            if (dp[N - 1][money] != -1) {
                return money;
            }
        }
        return allMoney;
    }


    public static int[][] generateTwoRandomArray(int len, int value) {
        int size = (int) (Math.random() * len) + 1;
        int[][] arrs = new int[2][size];
        for (int i = 0; i < size; i++) {
            arrs[0][i] = (int) (Math.random() * value) + 1;
            arrs[1][i] = (int) (Math.random() * value) + 1;
        }
        return arrs;
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int len = 10;
        int value = 20;
        for (int i = 0; i < testTimes; i++) {
            int[][] arrs = generateTwoRandomArray(len, value);
            int[] p = arrs[0];
            int[] d = arrs[1];
            long ans1 = dp1(p, d);
            long ans2 = dp2(p, d);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }


}
