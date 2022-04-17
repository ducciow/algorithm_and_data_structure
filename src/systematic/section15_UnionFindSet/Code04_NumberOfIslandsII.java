package systematic.section15_UnionFindSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 17, 04, 2022
 * @Description: The original grid is all '0's, and '1's come gradually at each time step by cell index. Return the
 *      sequence of results in terms of a list.
 *      https://leetcode.com/problems/number-of-islands-ii/
 * @Note:   Add one function -- connect() that is called every step '1' is fed. It then calls union in a infection way.
 *          ======
 *          Ver1. Use 2-d array UnionFindSet.
 *          Ver2. When grid is too big, use HashMap and store indices in forms of strings.
 */
public class Code04_NumberOfIslandsII {

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

        public int findHead(int i) {
            int pi = 0;
            while (parent[i] != i) {
                path[pi++] = i;
                i = parent[i];
            }
            for (pi--; pi >= 0; pi--) {
                parent[path[pi]] = i;
            }
            return i;
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
            if (size[a] == 0 || size[b] == 0) {
                return;
            }
            int pa = findHead(a);
            int pb = findHead(b);
            if (pa != pb) {
                if (size[pa] >= size[pb]) {
                    parent[pb] = pa;
                    size[pa] += size[pb];
                } else {
                    parent[pb] = pa;
                    size[pb] += size[pa];
                }
                numSets--;
            }
        }

        public int connect(int r, int c) {
            int idx = r * col + c;
            if (size[idx] == 0) {
                size[idx] = 1;
                parent[idx] = idx;
                numSets++;
                union(r, c - 1, r, c);
                union(r, c + 1, r, c);
                union(r - 1, c, r, c);
                union(r + 1, c, r, c);
            }
            return numSets;
        }
    }


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

        public void union(String str1, String str2) {
            if (parentMap.containsKey(str1) && parentMap.containsKey(str2)) {  // no need to check index boundaries
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

        public int connect(int r, int c) {
            String str = String.valueOf(r) + "-" + String.valueOf(c);
            if (!parentMap.containsKey(str)) {
                parentMap.put(str, str);
                sizeMap.put(str, 1);
                String up = String.valueOf(r - 1) + "_" + String.valueOf(c);
                String down = String.valueOf(r + 1) + "_" + String.valueOf(c);
                String left = String.valueOf(r) + "_" + String.valueOf(c - 1);
                String right = String.valueOf(r) + "_" + String.valueOf(c + 1);
                union(up, str);
                union(down, str);
                union(left, str);
                union(right, str);
            }
            return sizeMap.size();
        }

    }

}
