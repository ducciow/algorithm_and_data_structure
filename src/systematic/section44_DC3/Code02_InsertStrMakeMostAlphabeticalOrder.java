package systematic.section44_DC3;

/**
 * @Author: duccio
 * @Date: 08, 06, 2022
 * @Description: Given two strings str1 and str2, insert str2 at any position of str1 to make the result have the most
 *      alphabetical order. len(str1) >> len(str2).
 * @Note:   1. Concatenate str1 and str2 to feed DC3.
 *          2. Restrict the comparing area, where starts at the first position of str1 with ranking less than str2, and
 *             ends at the first smaller char afterwards.
 *          3. Find the best inserting position within the comparing area.
 *          ======
 *          O(N+M) + O(M*M)
 */
public class Code02_InsertStrMakeMostAlphabeticalOrder {

    public static String maxCombine(String str1, String str2) {
        if (str1 == null || str1.length() == 0) {
            return str2;
        }
        if (str2 == null || str2.length() == 0) {
            return str1;
        }
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        int N = chars1.length;
        int M = chars2.length;
        // transform chars1 and chars2 to fit the input of DC3
        int min = chars1[0];
        int max = chars1[0];
        for (int i = 1; i < N; i++) {
            min = Math.min(min, chars1[i]);
            max = Math.max(max, chars1[i]);
        }
        for (int i = 0; i < M; i++) {
            min = Math.min(min, chars2[i]);
            max = Math.max(max, chars2[i]);
        }
        int[] all = new int[N + M + 1];
        int idx = 0;
        for (int i = 0; i < N; i++) {
            all[idx++] = chars1[i] - min + 2;
        }
        all[idx++] = 1;
        for (int i = 0; i < M; i++) {
            all[idx++] = chars2[i] - min + 2;
        }
        // construct DC3
        Code00_DC3 dc3 = new Code00_DC3(all, max - min + 2);
        int[] rank = dc3.rank;
        int comp = N + 1;  // starting index of str2 in all
        for (int i = 0; i < N; i++) {
            if (rank[i] < rank[comp]) {
                int split = bestSplit(str1, str2, i);
                return str1.substring(0, split) + str2 + str1.substring(split);
            }
        }
        return str1 + str2;
    }

    private static int bestSplit(String str1, String str2, int start) {
        int N = str1.length();
        int M = str2.length();
        // find the end index of comparing area
        int end = N;
        for (int i = start, j = 0; i < N && j < M; i++, j++) {
            if (str1.charAt(i) < str2.charAt(j)) {
                end = i;
                break;
            }
        }
        // get the best splitting position
        String bestPrefix = str2;
        int ans = start;
        for (int i = start + 1, j = M - 1; i <= end; i++, j--) {
            String curPrefix = str1.substring(start, i) + str2.substring(0, j);
            if (curPrefix.compareTo(bestPrefix) >= 0) {
                bestPrefix = curPrefix;
                ans = i;
            }
        }
        return ans;
    }

    public static String naive(String str1, String str2) {
        if (str1 == null || str1.length() == 0) {
            return str2;
        }
        if (str2 == null || str2.length() == 0) {
            return str1;
        }
        String res = "";
        for (int i = 0; i <= str1.length(); i++) {
            String cur = str1.substring(0, i) + str2 + str1.substring(i);
            if (cur.compareTo(res) > 0) {
                res = cur;
            }
        }
        return res;
    }

    public static String randomNumberString(int len, int range) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * range) + '0');
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int testTime = 10000;
        int range = 10;
        int len = 50;
        System.out.println("Test begin");
        for (int i = 0; i < testTime; i++) {
            int s1Len = (int) (Math.random() * len);
            int s2Len = (int) (Math.random() * len);
            String s1 = randomNumberString(s1Len, range);
            String s2 = randomNumberString(s2Len, range);
            String ans1 = maxCombine(s1, s2);
            String ans2 = naive(s1, s2);
            if (!ans1.equals(ans2)) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed");
    }

}
