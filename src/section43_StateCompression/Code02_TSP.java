package section43_StateCompression;

import java.util.ArrayList;

/**
 * @Author: duccio
 * @Date: 06, 06, 2022
 * @Description: Travelling Salesman Problem.
 * @Note:   Ver1. brute force.
 *          Ver2. brute force using bit representation of state.
 *          Ver3. dp using memory cache based on Ver2.
 *          ======
 *          1. The very first starting city does not matter.
 *          2. So assume the route starts and ends at city 0.
 *          3. At each move, try every available city from current city.
 *          4. If only one city is left, the distance is form it to city 0.
 */
public class Code02_TSP {

    public static int tsp1(int[][] matrix) {
        int N = matrix.length;
        ArrayList<Integer> state = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            state.add(i);
        }
        return process1(matrix, state, 0);
    }

    private static int process1(int[][] matrix, ArrayList<Integer> state, int start) {
        int rest = 0;
        for (int i = 0; i < state.size(); i++) {
            if (state.get(i) != null) {
                rest++;
            }
        }
        // if only the start city is left
        if (rest == 1) {
            return matrix[start][0];
        }
        // mark the start city
        state.set(start, null);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < state.size(); i++) {
            if (state.get(i) != null) {
                int next = matrix[start][i] + process1(matrix, state, i);
                min = Math.min(min, next);
            }
        }
        state.set(start, start);
        return min;
    }

    public static int tsp2(int[][] matrix) {
        int N = matrix.length;
        int state = (1 << N) - 1;  // digit 1 means available
        return process2(matrix, state, 0);
    }

    public static int process2(int[][] matrix, int state, int start) {
        // if only the start city is left
        if (state == (state & (~state + 1))) {
            return matrix[start][0];
        }
        // mark the start city
        state &= ~(1 << start);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < matrix.length; i++) {
            if ((state & (1 << i)) != 0) {  // 1 means current digit is available
                int next = matrix[start][i] + process2(matrix, state, i);
                min = Math.min(min, next);
            }
        }
        // state |= 1 << start;
        return min;
    }

    public static int tsp3(int[][] matrix) {
        int N = matrix.length;
        int state = (1 << N) - 1;
        int[][] lookup = new int[1 << N][N];
        for (int i = 0; i < (1 << N); i++) {
            for (int j = 0; j < N; j++) {
                lookup[i][j] = -1;
            }
        }
        return process3(matrix, state, 0, lookup);
    }

    private static int process3(int[][] matrix, int state, int start, int[][] lookup) {
        if (lookup[state][start] != -1) {
            return lookup[state][start];
        }
        if (state == (state & (~state + 1))) {
            lookup[state][start] = matrix[start][0];
        } else {
            state &= ~(1 << start);
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < matrix.length; i++) {
                if ((state & (1 << i)) != 0) {
                    int next = matrix[start][i] + process3(matrix, state, i, lookup);
                    min = Math.min(min, next);
                }
            }
            state |= 1 << start;
            lookup[state][start] = min;
        }
        return lookup[state][start];
    }


    // copied for test
    public static int dp(int[][] matrix) {
        int N = matrix.length; // 0...N-1
        int statusNums = 1 << N;
        int[][] dp = new int[statusNums][N];

        for (int status = 0; status < statusNums; status++) {
            for (int start = 0; start < N; start++) {
                if ((status & (1 << start)) != 0) {
                    if (status == (status & (~status + 1))) {
                        dp[status][start] = matrix[start][0];
                    } else {
                        int min = Integer.MAX_VALUE;
                        // start 城市在status里去掉之后，的状态
                        int preStatus = status & (~(1 << start));
                        // start -> i
                        for (int i = 0; i < N; i++) {
                            if ((preStatus & (1 << i)) != 0) {
                                int cur = matrix[start][i] + dp[preStatus][i];
                                min = Math.min(min, cur);
                            }
                        }
                        dp[status][start] = min;
                    }
                }
            }
        }
        return dp[statusNums - 1][0];
    }


    public static int[][] generateGraph(int maxSize, int maxValue) {
        int len = (int) (Math.random() * maxSize) + 1;
        int[][] matrix = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                matrix[i][j] = (int) (Math.random() * maxValue) + 1;
            }
        }
        for (int i = 1; i < len; i++) {
            for (int j = 0; j < i; j++) {
                matrix[i][j] = matrix[j][i];
            }
        }
        for (int i = 0; i < len; i++) {
            matrix[i][i] = 0;
        }
        return matrix;
    }

    public static void main(String[] args) {
        int numTest = 10000;
        int len = 10;
        int value = 100;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            int[][] matrix = generateGraph(len, value);
            int ans1 = tsp3(matrix);
            int ans2 = dp(matrix);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed");
    }

}
