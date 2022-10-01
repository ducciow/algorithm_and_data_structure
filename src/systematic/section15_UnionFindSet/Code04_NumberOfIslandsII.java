package systematic.section15_UnionFindSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 17, 04, 2022
 * @Description: The original grid is all '0's, and '1's are given gradually at each time step in terms of cell index.
 *      Return the sequence of results in a list.
 *      https://leetcode.com/problems/number-of-islands-ii/
 * @Note:   - Init the UnionFindSet only with grid size, i.e., no real elements are given at the very beginning.
 *          - Add one function -- connect() that is called each time '1' is fed. It then calls union() in an
 *            infection way.
 *          - When union, the size of the small head is remained as a use of mark.
 *          ======
 *          Ver1. Use 1-d array UnionFindSet.
 *          Ver2. If the grid is too big, use HashMap and store indices in forms of strings.
 */
public class Code04_NumberOfIslandsII {

    // ver. 1
    public static List<Integer> numIslands(int rows, int cols, int[][] positions) {
        UFSet ufSet = new UFSet(rows, cols);
        List<Integer> ret = new ArrayList<>();
        for (int[] position : positions) {
            ret.add(ufSet.connect(position[0], position[1]));
        }
        return ret;
    }

    public static class UFSet {
        int[] parent;
        int[] size;
        int[] path;
        int row;
        int col;
        int numSets;

        public UFSet(int r, int c) {
            row = r;
            col = c;
            numSets = 0;
            int N = r * c;
            parent = new int[N];
            size = new int[N];
            path = new int[N];
        }

        public int findHead(int e) {
            int idx = 0;
            while (parent[e] != e) {
                path[idx++] = e;
                e = parent[e];
            }
            for (idx--; idx >= 0; idx--) {
                parent[path[idx]] = e;
            }
            return e;
        }

        // check status, union, and return the current number of sets
        public int connect(int r, int c) {
            int e = r * col + c;
            if (size[e] == 0) {
                size[e] = 1;
                parent[e] = e;
                numSets++;
                union(r, c - 1, r, c);
                union(r, c + 1, r, c);
                union(r - 1, c, r, c);
                union(r + 1, c, r, c);
            }
            return numSets;
        }

        public void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r1 >= row || c1 < 0 || c1 >= col) {
                return;
            }
            if (r2 < 0 || r2 >= row || c2 < 0 || c2 >= col) {
                return;
            }
            int a = r1 * col + c1;
            int b = r2 * col + c2;
            // if either of the two given points is not in an island
            if (size[a] == 0 || size[b] == 0) {
                return;
            }
            // else they are both in their islands (is about to be or already is in one union)
            int ha = findHead(a);
            int hb = findHead(b);
            if (ha != hb) {
                if (size[ha] >= size[hb]) {
                    parent[hb] = ha;
                    size[ha] += size[hb];
                } else {
                    parent[hb] = ha;
                    size[hb] += size[ha];
                }
                numSets--;
            }
        }
    }


    // ver. 2
    public static List<Integer> numIslandsTooBig(int rows, int cols, int[][] positions) {
        UFSetForBig ufSet = new UFSetForBig();
        List<Integer> ret = new ArrayList<>();
        for (int[] position : positions) {
            ret.add(ufSet.connect(position[0], position[1]));
        }
        return ret;
    }

    public static class UFSetForBig {
        HashMap<String, String> parentMap;
        HashMap<String, Integer> sizeMap;

        public UFSetForBig() {
            parentMap = new HashMap<>();
            sizeMap = new HashMap<>();
        }

        public String findHead(String str) {
            Stack<String> stack = new Stack<>();
            while (!parentMap.get(str).equals(str)) {
                stack.push(str);
                str = parentMap.get(str);
            }
            while (!stack.isEmpty()) {
                parentMap.put(stack.pop(), str);
            }
            return str;
        }

        public int connect(int r, int c) {
            String str = String.valueOf(r) + "-" + String.valueOf(c);
            if (!parentMap.containsKey(str)) {
                parentMap.put(str, str);
                sizeMap.put(str, 1);
                String up = String.valueOf(r - 1) + "-" + String.valueOf(c);
                String down = String.valueOf(r + 1) + "-" + String.valueOf(c);
                String left = String.valueOf(r) + "-" + String.valueOf(c - 1);
                String right = String.valueOf(r) + "-" + String.valueOf(c + 1);
                union(up, str);
                union(down, str);
                union(left, str);
                union(right, str);
            }
            return sizeMap.size();
        }

        public void union(String str1, String str2) {
            // no need to check index boundaries
            // if either of the two given points is not in an island
            if (!(parentMap.containsKey(str1) && parentMap.containsKey(str2))) {
                return;
            }
            // else they are both in their islands (is about to be or already is in one union)
            else {
                String head1 = findHead(str1);
                String head2 = findHead(str2);
                if (!head1.equals(head2)) {
                    String bigHead = sizeMap.get(head1) >= sizeMap.get(head2) ? head1 : head2;
                    String smallHead = bigHead.equals(head1) ? head2 : head1;
                    parentMap.put(smallHead, bigHead);
                    sizeMap.put(bigHead, sizeMap.get(bigHead) + sizeMap.get(smallHead));
                    sizeMap.remove(smallHead);
                }
            }
        }

    }

}
