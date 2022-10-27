package section09_TrieTree;

import java.util.HashMap;

/**
 * @Author: duccio
 * @Date: 06, 04, 2022
 * @Description: Trie Tree (prefix tree).
 * @Note:   - Each Node has three attributes: int pass, int end, Node[] nexts.
 *          - Here uses an array of size 26 for indexing nexts. Another option is using a HashMap.
 */
public class Code01_TrieTree {

    public static class Node {
        int pass;
        int end;
        Node[] nexts;

        public Node() {
            pass = 0;
            end = 0;
            nexts = new Node[26];
        }
    }

    public static class TrieTree {

        Node root;

        public TrieTree() {
            root = new Node();
        }

        public void add(String str) {
            if (str == null || str.equals("")) {
                return;
            }
            char[] chars = str.toCharArray();
            Node cur = root;
            cur.pass++;
            for (char c : chars) {
                int goTo = c - 'a';
                if (cur.nexts[goTo] == null) {
                    cur.nexts[goTo] = new Node();
                }
                cur.nexts[goTo].pass++;
                cur = cur.nexts[goTo];
            }
            cur.end++;
        }

        public int search(String str) {
            if (str == null || str.equals("")) {
                return 0;
            }
            char[] chars = str.toCharArray();
            Node cur = root;
            for (char c : chars) {
                int goTo = c - 'a';
                if (cur.nexts[goTo] == null) {
                    return 0;
                }
                cur = cur.nexts[goTo];
            }
            return cur.end;
        }

        public int searchPrefix(String str) {
            if (str == null || str.equals("")) {
                return 0;
            }
            char[] chars = str.toCharArray();
            Node cur = root;
            for (char c : chars) {
                int goTo = c - 'a';
                if (cur.nexts[goTo] == null) {
                    return 0;
                }
                cur = cur.nexts[goTo];
            }
            return cur.pass;
        }

        public void remove(String str) {
            if (search(str) <= 0) {
                return;
            }
            char[] chars = str.toCharArray();
            Node cur = root;
            cur.pass--;
            for (char c : chars) {
                int goTo = c - 'a';
                cur.nexts[goTo].pass--;
                if (cur.nexts[goTo].pass == 0) {
                    cur.nexts[goTo] = null;
                    return;
                }
                cur = cur.nexts[goTo];
            }
            cur.end--;
        }
    }

    public static class TrivialTester {
        HashMap<String, Integer> map;

        public TrivialTester() {
            map = new HashMap<>();
        }

        public void add(String str) {
            if (str == null || str.equals("")) {
                return;
            }
            map.put(str, map.getOrDefault(str, 0) + 1);
        }

        public int search(String str) {
            return map.getOrDefault(str, 0);
        }

        public int searchPrefix(String str) {
            int ans = 0;
            for (String s : map.keySet()) {
                if (s.startsWith(str)) {
                    ans++;
                }
            }
            return ans;
        }

        public void remove(String str) {
            if (map.containsKey(str)) {
                map.put(str, map.get(str) - 1);
                if (map.get(str) == 0) {
                    map.remove(str);
                }
            }
        }
    }

    public static String[] generateStrArray(int maxLen, int maxStrLen) {
        int N = (int) (Math.random() * maxLen) + 1;
        String[] strings = new String[N];
        for (int i = 0; i < N; i++) {
            int M = (int) (Math.random() * maxStrLen) + 1;
            char[] str = new char[M];
            for (int j = 0; j < M; j++) {
                int v = (int) (Math.random() * 26);
                str[j] = (char) (v + 97);
            }
            strings[i] = String.valueOf(str);
        }
        return strings;
    }

    public static void main(String[] args) {
        int numTest = 10000;
        int maxLen = 100;
        int maxStrLen = 100;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            String[] arr = generateStrArray(maxLen, maxStrLen);
            TrieTree tt1 = new TrieTree();
            TrivialTester tt2 = new TrivialTester();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    tt1.add(arr[j]);
                    tt2.add(arr[j]);
                } else if (decide < 0.5) {
                    tt1.remove(arr[j]);
                    tt2.remove(arr[j]);
                } else if (decide < 0.75) {
                    if (tt1.search(arr[j]) != tt2.search(arr[j])) {
                        System.out.println("Failed on search()");
                        return;
                    }
                } else {
                    if (tt1.searchPrefix(arr[j]) != tt2.searchPrefix(arr[j])) {
                        System.out.println("Failed on searchPrefix()");
                        return;
                    }
                }
            }
        }
        System.out.println("Test passed!");
    }

}
