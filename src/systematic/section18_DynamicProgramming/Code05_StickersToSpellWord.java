package systematic.section18_DynamicProgramming;

import java.util.HashMap;

/**
 * @Author: duccio
 * @Date: 25, 04, 2022
 * @Description: Given n different types of stickers. Each sticker has a lowercase English word on it. Spell out a
 *      given target string by cutting individual letters from your collection of stickers and rearranging them.
 *      Each sticker can be used more than once. Return the minimum number of stickers needed to spell out target.
 *      https://leetcode.com/problems/stickers-to-spell-word
 * @Note:   Ver1. Brute force. (exceed time limit of leetcode test)
 *          Ver2. Brute force with pruning, where stickers are converted to integer count array. (exceed time limit)
 *          Ver3. Use HashMap for lookup.
 *          ======
 *          Cannot be optimized to DP.
 */
public class Code05_StickersToSpellWord {

    public int minStickers1(String[] stickers, String target) {
        int ans = process1(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int process1(String[] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (String sticker : stickers) {
            String next = minus(target, sticker);
            if (next.length() != target.length()) {
                min = Math.min(min, process1(stickers, next));
            }
        }
        return min == Integer.MAX_VALUE ? min : min + 1;
    }

    public static String minus(String str1, String str2) {
        int[] charCount = new int[26];
        for (char c : str1.toCharArray()) {
            charCount[c - 'a']++;
        }
        for (char c : str2.toCharArray()) {
            charCount[c - 'a']--;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (charCount[i] > 0) {
                for (int j = 0; j < charCount[i]; j++) {
                    sb.append((char) ('a' + i));
                }
            }
        }
        return sb.toString();
    }

    public int minStickers2(String[] stickers, String target) {
        int[][] stickerTable = new int[stickers.length][26];
        for (int i = 0; i < stickers.length; i++) {
            for (char c : stickers[i].toCharArray()) {
                stickerTable[i][c - 'a']++;
            }
        }
        int ans = process2(stickerTable, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int process2(int[][] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        int[] targetCount = new int[26];
        for (char c : target.toCharArray()) {
            targetCount[c - 'a']++;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < stickers.length; i++) {
            int[] sticker = stickers[i];
            // pruning
            if (sticker[target.toCharArray()[0] - 'a'] > 0) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (targetCount[j] > 0) {
                        for (int k = 0; k < targetCount[j] - sticker[j]; k++) {
                            sb.append((char) ('a' + j));
                        }
                    }
                }
                min = Math.min(min, process2(stickers, sb.toString()));
            }
        }
        return min == Integer.MAX_VALUE ? min : min + 1;
    }

    public static int minStickers3(String[] stickers, String target) {
        int[][] stickerTable = new int[stickers.length][26];
        for (int i = 0; i < stickers.length; i++) {
            for (char c : stickers[i].toCharArray()) {
                stickerTable[i][c - 'a']++;
            }
        }
        HashMap<String, Integer> lookup = new HashMap<>();
        int ans = process3(stickerTable, target, lookup);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int process3(int[][] stickers, String target, HashMap<String, Integer> lookup) {
        if (lookup.containsKey(target)) {
            return lookup.get(target);
        }
        if (target.length() == 0) {
            lookup.put(target, 0);
            return 0;
        }
        int[] targetCount = new int[26];
        for (char c : target.toCharArray()) {
            targetCount[c - 'a']++;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < stickers.length; i++) {
            int[] sticker = stickers[i];
            // pruning
            if (sticker[target.toCharArray()[0] - 'a'] > 0) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (targetCount[j] > 0) {
                        for (int k = 0; k < targetCount[j] - sticker[j]; k++) {
                            sb.append((char) ('a' + j));
                        }
                    }
                }
                min = Math.min(min, process3(stickers, sb.toString(), lookup));
            }
        }
        int ans = min == Integer.MAX_VALUE ? min : min + 1;
        lookup.put(target, ans);
        return ans;
    }

}
