package section22_KMP;

/**
 * @Author: duccio
 * @Date: 06, 05, 2022
 * @Description: Given two strings, str1 and str2, return the starting index of str1's substring that is equal to str2.
 * @Note:   KMP algorithm:
 *          1) For each position i in str2, compute the maximum length of substring that appears both in head and tail
 *             of str2[0..i-1], but not the entire str2[0..i-1]. E.g., str2 is"abcabck" and for i=6, the number is 3.
 *             str2 is "aaaaak" and for i=5, then the number is 4. By that rule, for any str, the number for i=0 is -1,
 *             and for i=1 is 0.
 *          2) To get the array of values in 1) quicker, use the value of position i-1 to get the value of position i.
 *             To be specific, if the array required is infoArr, and infoArr[i-1] = k, to get infoArr[i]:
 *                  a) if str2[k] == str2[i-1], then infoArr[i] = k+1.
 *                  b) else get infoArr[k] = k', and if str2[k'] == str2[i-1], then infoArr[i] = k'+1.
 *                  c) else get infoArr[k'] = k'', and if str2[k''] == str2[i-1], then infoArr[i] = k''+1.
 *                  d) ......
 *          3) When compare str2 to a substring in str1, if it fails to match the current pair of chars, fix the char
 *             of str1, and shift the char of str2 backward to its corresponding value in 1). Move the char of str1
 *             forward if str2 cannot go backward anymore.
 */
public class Code01_KMP {

    public static int kmp(String str1, String str2) {
        if (str1 == null || str2 == null || str2.length() < 1 || str2.length() > str1.length()) {
            return -1;
        }
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        // O(M), M is str2.length
        int[] infoArr = getInfoArray(chars2);
        int idx1 = 0;
        int idx2 = 0;
        // O(N), N is str1.length
        while (idx1 < chars1.length && idx2 < chars2.length) {
            if (chars1[idx1] == chars2[idx2]) {
                idx1++;
                idx2++;
            } else if (infoArr[idx2] > -1) {
                idx2 = infoArr[idx2];
            } else {
                idx1++;
            }
        }
        return idx2 == chars2.length ? idx1 - idx2 : -1;
    }

    private static int[] getInfoArray(char[] chars) {
        if (chars.length == 1) {
            return new int[]{-1};
        }
        int[] ret = new int[chars.length];
        ret[0] = -1;
        ret[1] = 0;
        int idx = 2;  // the position to process
        int cp = 0;  // the position to be compared to chars[idx-1]
        while (idx < chars.length) {
            if (chars[cp] == chars[idx - 1]) {
                ret[idx++] = ++cp;
            } else if (cp > 0) {
                cp = ret[cp];
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

    public static void main(String[] args) {
        int testTimes = 50000;
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        System.out.println("Test begin...");
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
