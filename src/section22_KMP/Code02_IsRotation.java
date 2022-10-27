package section22_KMP;

/**
 * @Author: duccio
 * @Date: 09, 05, 2022
 * @Description: Given two strings, return if they are rotated from each other. A rotation means put a head substring to
 *      the tail, eg., "abcde" and "cdeab".
 * @Note:   1. Concatenate str1 with itself, and use KMP to check if str2 is a substring of the concatenated string.
 */
public class Code02_IsRotation {

    public static void main(String[] args) {
        String str1 = "ducciow";
        String str2 = "owducci";
        System.out.println(isRotation(str1, str2));
    }

    public static boolean isRotation(String str1, String str2) {
        if (str1 == null || str2 == null || str1.length() != str2.length()) {
            return false;
        }
        char[] chars1 = str1.toCharArray();
        char[] concat = new char[chars1.length * 2];
        for (int i = 0; i < concat.length; i++) {
            concat[i] = chars1[i % chars1.length];
        }
        return kmp(String.valueOf(concat), str2) != -1;
    }

    public static int kmp(String str1, String str2) {
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        int[] nextArr = getNextArray(chars2);
        int idx1 = 0;
        int idx2 = 0;
        while (idx1 < chars1.length && idx2 < chars2.length) {
            if (chars1[idx1] == chars2[idx2]) {
                idx1++;
                idx2++;
            } else if (nextArr[idx2] > -1) {
                idx2 = nextArr[idx2];
            } else {
                idx1++;
            }
        }
        return idx2 == chars2.length ? idx1 - chars2.length : -1;
    }

    public static int[] getNextArray(char[] chars) {
        if (chars.length < 2) {
            return new int[]{-1};
        }
        int[] ret = new int[chars.length];
        ret[0] = -1;
        ret[1] = 0;
        int compare = 0;
        int idx = 2;
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


}
