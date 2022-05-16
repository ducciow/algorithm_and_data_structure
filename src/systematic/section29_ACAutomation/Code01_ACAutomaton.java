package systematic.section29_ACAutomation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Author: duccio
 * @Date: 16, 05, 2022
 * @Description: Given a paragraph and an array of key words, find if the paragraph contains any given key words.
 * @Note:   1. Firstly construct a Trie Tree using all of the key words, with each node has an extra fail pointer.
 *          2. Then build the automaton by directing the fail pointer of each node using BFS. The fail pointer of
 *             root node is to null, and root's direct children's fail pointers are to root. Other nodes' fail
 *             pointers are set according to their parent node.
 *          3. When check the paragraph, for each character, traverse along the fail pointers to collect every matching
 *             key word.
 */
public class Code01_ACAutomaton {

    public static void main(String[] args) {
        ACAutomaton ac = new ACAutomaton();
        ac.insert("dhe");
        ac.insert("he");
        ac.insert("abcdheks");
        ac.build();

        List<String> contains = ac.containWords("abcdhekskdjfafhasldkflskdjhwqaeruv");

        for (String word : contains) {
            System.out.println(word);
        }
    }

    public static class Node {
        Node[] nexts;
        Node fail;
        String end;
        boolean endCheck;

        public Node() {
            nexts = new Node[26];
            fail = null;
            end = null;
            endCheck = false;
        }
    }

    public static class ACAutomaton {
        Node root;

        public ACAutomaton() {
            root = new Node();
        }

        // insert a word
        public void insert(String word) {
            char[] chars = word.toCharArray();
            Node cur = root;
            for (char cha : chars) {
                int idx = cha - 'a';
                if (cur.nexts[idx] == null) {
                    cur.nexts[idx] = new Node();
                }
                cur = cur.nexts[idx];
            }
            cur.end = word;
        }

        // build the AC automation with BFS
        public void build() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur = null;
            Node curFail = null;
            while (!queue.isEmpty()) {
                cur = queue.poll();
                for (int i = 0; i < 26; i++) {
                    if (cur.nexts[i] != null) {
                        cur.nexts[i].fail = root;
                        curFail = cur.fail;
                        while (curFail != null) {
                            if (curFail.nexts[i] != null) {
                                cur.nexts[i].fail = curFail.nexts[i];
                                break;
                            }
                            curFail = curFail.fail;
                        }
                        queue.add(cur.nexts[i]);
                    }
                }
            }
        }

        public List<String> containWords(String content) {
            char[] contentChars = content.toCharArray();
            Node cur = root;
            Node follow = null;
            List<String> res = new ArrayList<>();
            for (char contentChar : contentChars) {
                int idx = contentChar - 'a';
                while (cur.nexts[idx] == null && cur.fail != null) {
                    cur = cur.fail;
                }
                if (cur.nexts[idx] != null) {
                    cur = cur.nexts[idx];
                } else {  // cur is root, meaning no path from root for this character
                    continue;
                }
                follow = cur;
                while (follow != root) {
                    if (follow.endCheck) {
                        break;
                    }
                    if (follow.end != null) {
                        res.add(follow.end);
                        follow.endCheck = true;
                    }
                    follow = follow.fail;
                }
            }
            return res;
        }

    }

}
