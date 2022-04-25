package systematic.section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 25, 04, 2022
 * @Description: Given a string of non-negative integers, convert it to a string of letters according to the rule that
 *      1 -> a, 2-> b, ..., 26 -> z. Return the number of converting ways. Eg., "111" -> "aaa", "qa", "aq" giving 3.
 * @Note:   Ver1. Brute force.
 *          Ver2. DP.
 *          ======
 *          1. Base case where index == str.length returns 1 meaning there is one valid conversion.
 *          2. Check if current character is 0, which cannot be converted.
 *          3. Check the combination of characters at index and index+1 is less than 27.
 */
public class Code04_ConvertToLetterString {

    public static void main(String[] args) {
        validate();
    }

    public static int ways1(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        return process1(chars, 0);
    }

    public static int process1(char[] chars, int idx) {
        if (idx == chars.length) {
            return 1;
        }
        if (chars[idx] == '0') {
            return 0;
        }
        int option1 = process1(chars, idx + 1);
        int option2 = idx + 1 < chars.length && (chars[idx] - '0') * 10 + (chars[idx + 1] - '0') < 27 ?
                process1(chars, idx + 2) : 0;
        return option1 + option2;
    }

    public static int ways2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        int[] dp = new int[chars.length + 1];
        dp[chars.length] = 1;
        for (int idx = chars.length - 1; idx >= 0; idx--) {
            if (chars[idx] == '0') {
                continue;
            }
            int option1 = dp[idx + 1];
            int option2 = idx + 1 < chars.length && (chars[idx] - '0') * 10 + (chars[idx + 1] - '0') < 27 ?
                    dp[idx + 2] : 0;
            dp[idx] = option1 + option2;
        }
        return dp[0];
    }


    public static String genRandStr(int maxLen) {
        char[] chars = new char[(int) (Math.random() * (maxLen + 1))];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (int) (Math.random() * 10);
        }
        return String.valueOf(chars);
    }

    public static void validate() {
        int numTest = 10000;
        int maxLen = 30;
        for (int i = 0; i < numTest; i++) {
            String str = genRandStr(maxLen);
            int ans1 = ways1(str);
            int ans2 = ways2(str);
            if (ans1 != ans2) {
                System.out.println("Failed on case: " + str);
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
