package section23_Manacher;

/**
 * @Author: duccio
 * @Date: 09, 05, 2022
 * @Description: Given a string, find the longest substring that is palindrome, return the length.
 * @Note:   1. Preprocess char array by interspersing special chars, eg., "abc" -> "#a#b#c#". This special char can be
 *             any type because it does not affect the original answer.
 *          2. Initiate a radius array for storing the radius of palindromes with each element being the center.
 *             Initiate integer R = -1, meaning the rightmost (exclusive) boundary of all palindromes, and C = -1,
 *             meaning the center element corresponding to R.
 *          3. Discuss based on current index i and R:
 *             1) i >= R (meaning the current item is out of R):
 *                cannot be accelerated, so perform naive checking.
 *             2) i < R (meaning the current item is covered by R):
 *                get the symmetric index i' of i with respect to C, symmetric boundary L of R, and then get the
 *                radius of i' from the radius array.
 *                a) if the radius of i' falls within (L, R), then the radius of i is the same value to i'.
 *                b) else if the radius of i' is out of (L, R), then the radius of i is R - i.
 *                c) else, the radius of i' is on L, perform checking starting from R.
 */
public class Code01_Manacher {

    // O(N)
    public static int manacher(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = preprocess(str.toCharArray());
        int[] radius = new int[chars.length];
        int R = -1;
        int C = -1;
        int maxRadius = Integer.MIN_VALUE;
        for (int i = 0; i < chars.length; i++) {
            // the minimum radius of i that does not need checking
            // i' = 2 * C - i
            radius[i] = i < R ? Math.min(radius[2 * C - i], R - i) : 1;
            // case a) and b) will directly go to break
            while (i + radius[i] < chars.length && i - radius[i] >= 0) {
                if (chars[i + radius[i]] == chars[i - radius[i]]) {
                    radius[i]++;
                } else {
                    break;
                }
            }
            // update R and C
            if (i + radius[i] > R) {
                R = i + radius[i];
                C = i;
            }
            maxRadius = Math.max(maxRadius, radius[i]);
        }
        return maxRadius - 1;
    }

    public static char[] preprocess(char[] chars) {
        char[] res = new char[chars.length * 2 + 1];
        for (int i = 0; i < res.length; i++) {
            if ((i & 1) == 0) {
                res[i] = '#';
            } else {
                res[i] = chars[i / 2];
            }
        }
        return res;
    }


    // O(N**2)
    public static int naive(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = preprocess(str.toCharArray());
        int len = 0;
        for (int i = 0; i < chars.length; i++) {
            int L = i - 1;
            int R = i + 1;
            while (L >= 0 && R < chars.length && chars[L] == chars[R]) {
                L--;
                R++;
            }
            len = Math.max(len, R - L - 1);
        }
        return len / 2;
    }

    public static String genRandStr(int maxL, int maxV) {
        char[] chars = new char[(int) (Math.random() * maxL) + 1];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) ((int) (Math.random() * maxV) + 'a');
        }
        return String.valueOf(chars);
    }

    public static void main(String[] args) {
        int numTest = 100000;
        int maxL = 20;
        int maxV = 5;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            String str = genRandStr(maxL, maxV);
            int ans1 = manacher(str);
            int ans2 = naive(str);
            if (ans1 != ans2) {
                System.out.println("Failed");
                System.out.println(str);
                System.out.println(ans1);
                System.out.println(ans2);
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
