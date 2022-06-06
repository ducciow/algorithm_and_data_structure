package systematic.section43_StateCompression;

/**
 * @Author: duccio
 * @Date: 06, 06, 2022
 * @Description: Two players take turns drawing from a common pool of numbers from 1 to N without replacement until
 *      they reach a total >= K. The player who first causes the running total to reach or exceed K wins. Return true
 *      if the first player to move can force a win, otherwise, return false. Assume both players play optimally.
 *      Constraints: 1 <= N <= 20, 0 <= K <= 300.
 *      https://leetcode.com/problems/can-i-win/
 * @Note:   Ver1. brute force.
 *          Ver2. brute force using bit representation of states.
 *          Ver3. dp using memory cache based on Ver2.
 *          ======
 *          Just try every available option in each move.
 */
public class Code01_CanIWin {

    public boolean canIWin1(int maxChoose, int total) {
        if (total == 0) {  // defined by the problem description
            return true;
        }
        if (maxChoose * (maxChoose + 1) / 2 < total) {
            return false;
        }
        int[] choose = new int[maxChoose + 1];
        return process1(choose, total);
    }

    public static boolean process1(int[] choose, int rest) {
        if (rest <= 0) {
            return false;
        }
        for (int i = 1; i < choose.length; i++) {
            if (choose[i] == 0) {
                choose[i] = 1;
                boolean next = process1(choose, rest - i);
                choose[i] = 0;
                if (!next) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canIWin2(int maxChoose, int total) {
        if (total == 0) {
            return true;
        }
        if (maxChoose * (maxChoose + 1) / 2 < total) {
            return false;
        }
        return process2(maxChoose, 0, total);
    }

    private boolean process2(int maxChoose, int state, int rest) {
        if (rest <= 0) {
            return false;
        }
        for (int i = 1; i <= maxChoose; i++) {
            if (((1 << i) & state) == 0) {  // 0 means this digit has not been tried
                boolean next = process2(maxChoose, (1 << i) | state, rest - i);
                if (!next) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean canIWin3(int maxChoose, int total) {
        if (total == 0) {
            return true;
        }
        if ((maxChoose * (maxChoose + 1) / 2) < total) {
            return false;
        }
        // memory cache, 0 means have not tried, 1 means true, -1 means false
        int[] lookup = new int[1 << (maxChoose + 1)];
        return process3(maxChoose, 0, total, lookup);
    }

    private boolean process3(int maxChoose, int state, int rest, int[] lookup) {
        if (lookup[state] != 0) {
            return lookup[state] == 1;
        }
        boolean ans = false;
        if (rest > 0) {
            for (int i = 1; i <= maxChoose; i++) {
                if (((1 << i) & state) == 0) {
                    boolean next = process3(maxChoose, (1 << i) | state, rest - i, lookup);
                    if (!next) {
                        ans = true;
                        break;
                    }
                }
            }
        }
        lookup[state] = ans ? 1 : -1;
        return ans;
    }


}
