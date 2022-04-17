package systematic.section15_UnionFindSet;

import java.util.HashMap;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 17, 04, 2022
 * @Description: Given a matrix M of 0s and 1s, where M[i][j] = 1 indicates i and j are friends. Find the number of
 *      friend circles, within which evey person directly or indirectly knows each other.
 *      https://leetcode.com/problems/number-of-provinces/
 * @Note:   Ver1. Use classic UnionFindSet.
 *          Ver2. Use modified UnionFindSet. Directly use 1-d array instead of map.
 */
public class Code02_FriendCircles {

    public int findCircleNum1(int[][] M) {
        if (M == null || M.length == 0) {
            return 0;
        }
        int N = M.length;
        UFSet1 ufSet = new UFSet1(N);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (M[i][j] == 1) {
                    ufSet.union(i, j);
                }
            }
        }
        return ufSet.numSets();
    }

    public static class UFSet1 {

        HashMap<Integer, Integer> parentMap;
        HashMap<Integer, Integer> sizeMap;

        public UFSet1(int N) {
            parentMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (int i = 0; i < N; i++) {
                parentMap.put(i, i);
                sizeMap.put(i, 1);
            }
        }

        public Integer findHead(Integer i) {
            Stack<Integer> stack = new Stack<>();
            while (parentMap.get(i) != i) {
                stack.push(i);
                i = parentMap.get(i);
            }
            while (!stack.isEmpty()) {
                parentMap.put(stack.pop(), i);
            }
            return i;
        }


        public void union(int a, int b) {
            Integer pa = findHead(a);
            Integer pb = findHead(b);
            if (pa != pb) {
                if (sizeMap.get(pa) >= sizeMap.get(pb)) {
                    parentMap.put(pb, pa);
                    sizeMap.put(pa, sizeMap.get(pa) + sizeMap.get(pb));
                    sizeMap.remove(pb);
                } else {
                    parentMap.put(pa, pb);
                    sizeMap.put(pb, sizeMap.get(pb) + sizeMap.get(pa));
                    sizeMap.remove(pa);
                }
            }
        }

        public int numSets() {
            return sizeMap.size();
        }
    }


    public int findCircleNum2(int[][] M) {
        if (M == null || M.length == 0) {
            return 0;
        }
        int N = M.length;
        UFSet2 ufSet = new UFSet2(N);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (M[i][j] == 1) {
                    ufSet.union(i, j);
                }
            }
        }
        return ufSet.getNumSets();
    }

    public static class UFSet2 {

        int[] parent;
        int[] size;
        int[] path;
        int numSets;

        public UFSet2(int N) {
            parent = new int[N];
            size = new int[N];
            path = new int[N];
            numSets = 0;
            for (int i = 0; i < N; i++) {
                parent[i] = i;
                size[i] = 1;
                numSets++;
            }
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

        public void union(int a, int b) {
            int pa = findHead(a);
            int pb = findHead(b);
            if (pa != pb) {
                if (size[pa] >= size[pb]) {
                    parent[pb] = pa;
                    size[pa] += size[pb];
                    size[pb] = 0;
                } else {
                    parent[pa] = pb;
                    size[pb] += size[pa];
                    size[pa] = 0;
                }
                numSets--;
            }
        }

        public int getNumSets() {
            return numSets;
        }

    }

}
