package systematic.section17_BruteForcingRecursion;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: duccio
 * @Date: 21, 04, 2022
 * @Description: Given a string, print all of its substrings. A subsequence of a string is a new string generated from
 *      the original string with some characters (can be none) deleted without changing the relative order of the
 *      remaining characters, eg, "ace" is a subsequence of "abcde".
 * @Note:   To get all substrings without repeat, use HashSet instead of List for storing answer.
 */
public class Code02_PrintAllSubstrings {

    public static void main(String[] args) {
        List<String> ans = substrings("abc");
        for (String str : ans) {
            System.out.println(str);
        }
    }

    public static List<String> substrings(String str) {
        List<String> ans = new ArrayList<>();
        if (str == null) {
            return ans;
        }
        char[] chars = str.toCharArray();
        process(chars, 0, "", ans);
        return ans;
    }

    public static void process(char[] chars, int idx, String path, List<String> ans) {
        if (idx == chars.length) {
            ans.add(path);
        } else {
            process(chars, idx + 1, path, ans);
            process(chars, idx + 1, path + chars[idx], ans);
        }
    }

}
