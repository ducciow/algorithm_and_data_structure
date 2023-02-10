package section23_Manacher;

/**
 * @Author: duccio
 * @Date: 09, 05, 2022
 * @Description: Given a string, append a string to it to make it a palindrome. Return the shortest appended string.
 * @Note:   1. Use Manacher to find the longest rightmost palindrome substring, i.e., first time R == N.
 *          2. Get the head substring before C's left palindrome boundary, and the appended string is the reverse of it.
 */
public class Code02_AddShortestEnd {

    public static void main(String[] args) {
        String str1 = "abcd123321";
        System.out.println(shortestEnd(str1));
    }

    public static String shortestEnd(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char[] chars = preprocess(str.toCharArray());
        int[] radius = new int[chars.length];
        int R = -1;
        int C = -1;
        for (int i = 0; i < chars.length; i++) {
            radius[i] = i < R ? Math.min(radius[2 * C - i], R - i) : 1;
            while (i + radius[i] < chars.length && i - radius[i] >= 0) {
                if (chars[i + radius[i]] == chars[i - radius[i]]) {
                    radius[i]++;
                } else {
                    break;
                }
            }
            if (i + radius[i] > R) {
                R = i + radius[i];
                C = i;
            }
            if (R == chars.length) {
                break;
            }
        }
        // radius[i]-1 is the full length of the corresponding palindrome in str
        char[] res = new char[str.length() - radius[C] + 1];
        // fill in reversely
        for (int i = 0; i < res.length; i++) {
            res[res.length - 1 - i] = chars[i * 2 + 1];
        }
        return String.valueOf(res);
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

}
