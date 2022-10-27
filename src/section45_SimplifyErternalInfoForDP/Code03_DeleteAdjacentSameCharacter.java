package section45_SimplifyErternalInfoForDP;

/**
 * @Author: duccio
 * @Date: 09, 06, 2022
 * @Description: Delete characters by rules: If the adjacent char is not identical, it cannot be deleted, otherwise
 *      they can be deleted together. Deleted chars leave the rest chars adjacent. Return the minimum number of chars
 *      are left in the end.
 * @Note:   Ver1. brute force.
 *          Ver2. dp with memory cache
 *          ======
 *          Key idea: for any range, provide whether the char before is identical to the first char in this range.
 *                    Then delete the head chars together, or keep the head chars to delete with some later same chars.
 */
public class Code03_DeleteAdjacentSameCharacter {

    public static int minRest1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }
        char[] chars = s.toCharArray();
        return process1(chars, 0, chars.length - 1, false);
    }

    public static int process1(char[] chars, int L, int R, boolean hasSame) {
        if (L > R) {
            return 0;
        }
        if (L == R) {
            return hasSame ? 0 : 1;
        }
        // find the first different character
        int index = L;
        int k = hasSame ? 1 : 0;
        while (index <= R && chars[index] == chars[L]) {
            k++;
            index++;
        }
        // delete the head same chars together
        int ans = (k > 1 ? 0 : 1) + process1(chars, index, R, false);
        for (int split = index; split <= R; split++) {
            if (chars[split] == chars[L] && chars[split] != chars[split - 1]) {
                if (process1(chars, index, split - 1, false) == 0) {
                    ans = Math.min(ans, process1(chars, split, R, k > 0));
                }
            }
        }
        return ans;
    }

    public static int minRest2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }
        char[] chars = s.toCharArray();
        int N = chars.length;
        int[][][] lookup = new int[N][N][2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < 2; k++) {
                    lookup[i][j][k] = -1;
                }
            }
        }
        return process2(chars, 0, N - 1, false, lookup);
    }

    private static int process2(char[] chars, int L, int R, boolean hasSame, int[][][] lookup) {
        if (L > R) {
            return 0;
        }
        int k = hasSame ? 1 : 0;
        if (lookup[L][R][k] != -1) {
            return lookup[L][R][k];
        }
        int ans = 0;
        if (L == R) {
            ans = k > 0 ? 0 : 1;
        } else {
            int index = L;
            int cnt = k;
            while (index <= R && chars[index] == chars[L]) {
                cnt++;
                index++;
            }
            ans = (cnt > 1 ? 0 : 1) + process2(chars, index, R, false, lookup);
            for (int split = index; split <= R; split++) {
                if (chars[split] == chars[L] && chars[split] != chars[split - 1]) {
                    if (process2(chars, index, split - 1, false, lookup) == 0) {
                        ans = Math.min(ans, process2(chars, split, R, cnt > 0, lookup));
                    }
                }
            }
        }
        lookup[L][R][k] = ans;
        return ans;
    }


    public static String randomString(int len, int variety) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * variety) + 'a');
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxLen = 16;
        int variety = 3;
        System.out.println("Test begin");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * maxLen);
            String str = randomString(len, variety);
            int ans1 = minRest1(str);
            int ans2 = minRest2(str);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed");
    }

}
