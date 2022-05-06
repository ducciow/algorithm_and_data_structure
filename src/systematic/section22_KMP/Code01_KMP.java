package systematic.section22_KMP;

/**
 * @Author: duccio
 * @Date: 06, 05, 2022
 * @Description: Given two strings, str1 and str2, return the starting index of str1's substring that is equal to str2.
 * @Note:   KMP algorithm:
 *          1) For each element str2[i], compute the maximum length of substring that appears both in head and tail of
 *             str2[0..i-1], requiring the substring does not cover the entire str2[0..i-1].
 *          2) When compare str2 from the start to str1, if the current pair of chars are different, fix the char of
 *             str1, and shift the char of str2 backward to its corresponding value computed by 1). Move the char of
 *             str1 forward if str2 cannot backward anymore.
 */
public class Code01_KMP {

    public static void main(String[] args) {
        validate();
    }

    public static int kmp(String str1, String str2) {
        if (str1 == null || str2 == null || str2.length() < 1 || str2.length() > str1.length()) {
            return -1;
        }
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        // O(M)
        int[] substrInfo = getSubstrInfo(chars2);
        int idx1 = 0;
        int idx2 = 0;
        // O(N)
        while (idx1 < chars1.length && idx2 < chars2.length) {
            if (chars1[idx1] == chars2[idx2]) {
                idx1++;
                idx2++;
            } else if (substrInfo[idx2] > -1) {
                idx2 = substrInfo[idx2];
            } else {
                idx1++;
            }
        }
        return idx2 == chars2.length ? idx1 - idx2 : -1;
    }

    private static int[] getSubstrInfo(char[] chars) {
        if (chars == null || chars.length < 2) {
            return new int[]{-1};
        }
        int[] ret = new int[chars.length];
        ret[0] = -1;
        ret[1] = 0;
        int idx = 2;
        int compare = ret[1];
        while (idx < chars.length) {
            if (chars[idx - 1] == chars[compare]) {
                ret[idx++] = ++compare;
            } else if (compare > 0) {
                compare = ret[compare];
            } else {
                ret[idx++] = 0;
            }
        }
        return ret;
    }


    public static String genRandomString(int possibilities, int size) {
        char[] chars = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(chars);
    }

    public static void validate() {
        int testTimes = 50000;
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        for (int i = 0; i < testTimes; i++) {
            String str1 = genRandomString(possibilities, strSize);
            String str2 = genRandomString(possibilities, matchSize);
            if (kmp(str1, str2) != str1.indexOf(str2)) {
                System.out.println("Failed");
            }
        }
        System.out.println("Test passed!");
    }

}
