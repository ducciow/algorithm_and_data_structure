package systematic.section14_Greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * @Author: duccio
 * @Date: 13, 04, 2022
 * @Description: Given an array of strings, concatenate them together in any order. Return the lowest lexicography.
 * @Note:   Sol.1. Greedy with sorting strategy: str1 + str2 < str2 + str1.
 *          Sol.2. Permutation.
 */
public class Code01_LowestLexicography {

    // Sol. 1
    public static String lowestStringGreedy(String[] strs) {
        if(strs == null || strs.length == 0) {
            return "";
        }
        Arrays.sort(strs, new MyComparator());
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            str.append(strs[i]);
        }
        return String.valueOf(str);
    }

    public static class MyComparator implements Comparator<String> {

        @Override
        public int compare(String a, String b) {
            return (a + b).compareTo(b + a);
        }
    }

    // Sol. 2
    public static String lowestStringPermutation(String[] strs) {
        if(strs == null || strs.length == 0) {
            return "";
        }
        return processPermutation(strs).first();
    }

    public static TreeSet<String> processPermutation(String[] strs) {
        TreeSet<String> set = new TreeSet<>();
        if(strs.length == 0) {
            set.add("");
        } else {
            for (int i = 0; i < strs.length; i++) {
                String[] remains = copyExcept(strs, i);
                TreeSet<String> remainPermutation = processPermutation(remains);
                for (String oneOrdering : remainPermutation) {
                    set.add(strs[i] + oneOrdering);
                }
            }
        }
        return set;
    }

    public static String[] copyExcept(String[] strs, int idx) {
        String[] ret = new String[strs.length - 1];
        int j = 0;
        for (int i = 0; i < strs.length; i++) {
            if(i != idx) {
                ret[j++] = strs[i];
            }
        }
        return ret;
    }

    // test
    public static String genRandStr(int maxL) {
        char[] chars = new char[(int)(Math.random() * maxL + 1)];
        for (int i = 0; i < chars.length; i++) {
            int value = (int) (Math.random() * 5);
            chars[i] = (Math.random() <= 0.5) ? (char) (65 + value) : (char) (97 + value);
        }
        return String.valueOf(chars);
    }

    public static String[] genRandStrArr(int arrLen, int strLen) {
        String[] strs = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < strs.length; i++) {
            strs[i] = genRandStr(strLen);
        }
        return strs;
    }

    public static void main(String[] args) {
        int numTest = 10000;
        int arrLen = 6;
        int strLen = 5;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            String[] arr1 = genRandStrArr(arrLen, strLen);
            String[] arr2 = Arrays.copyOf(arr1, arr1.length);
            String str1 = lowestStringGreedy(arr1);
            String str2 = lowestStringPermutation(arr2);
            if (!str1.equals(str2)) {
                System.out.println("Failed on case:");
                for (String str : arr1) {
                    System.out.print(str + ",");
                }
                System.out.println();
                System.out.println(str1);
                System.out.println(str2);
                return;
            }
        }
        System.out.println("Test passed!");
    }
}
