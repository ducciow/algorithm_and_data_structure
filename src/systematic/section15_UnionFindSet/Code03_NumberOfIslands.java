package systematic.section15_UnionFindSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 17, 04, 2022
 * @Description: Given an m x n 2D binary grid which represents a map of '1's (land) and '0's (water), return the number
 *      of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or
 *      vertically. You may assume all four edges of the grid are all surrounded by water.
 *      https://leetcode.com/problems/number-of-islands/
 * @Note:   Ver1. Use classic UnionFindSet. Wrap cell values into a trivial class.
 *          Ver2. Use modified UnionFindSet. Directly use 1-d array instead of map, with index converted from 2-d index.
 *          Ver3. Check up/down/left/right cells and mark them if they are union.
 *          ======
 *          All versions have the same time complexity, while with different constant operation.
 */
public class Code03_NumberOfIslands {

    public int numIslands1(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        Node[][] nodeGrid = new Node[row][col];
        List<Node> nodeList = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == '1') {
                    nodeGrid[i][j] = new Node();
                    nodeList.add(nodeGrid[i][j]);
                }
            }
        }
        UFSet1 ufSet = new UFSet1(nodeList);
        // union the first row
        for (int j = 1; j < col; j++) {
            if (grid[0][j - 1] == '1' && grid[0][j] == '1') {
                ufSet.union(nodeGrid[0][j - 1], nodeGrid[0][j]);
            }
        }
        // union the first column
        for (int i = 1; i < row; i++) {
            if (grid[i - 1][0] == '1' && grid[i][0] == '1') {
                ufSet.union(nodeGrid[i - 1][0], nodeGrid[i][0]);
            }
        }
        // union other cells
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (grid[i][j] == '1') {
                    if (grid[i - 1][j] == '1') {
                        ufSet.union(nodeGrid[i][j], nodeGrid[i - 1][j]);
                    }
                    if (grid[i][j - 1] == '1') {
                        ufSet.union(nodeGrid[i][j], nodeGrid[i][j - 1]);
                    }
                }
            }
        }
        return ufSet.numSets();
    }

    public static class Node {
    }

    public static class UFSet1 {

        HashMap<Node, Node> parentMap;
        HashMap<Node, Integer> sizeMap;

        public UFSet1(List<Node> nodes) {
            parentMap = new HashMap<>();
            sizeMap = new HashMap<>();
            for (Node node : nodes) {
                parentMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public Node findHead(Node node) {
            Stack<Node> stack = new Stack<>();
            while (parentMap.get(node) != node) {
                stack.push(node);
                node = parentMap.get(node);
            }
            while (!stack.isEmpty()) {
                parentMap.put(stack.pop(), node);
            }
            return node;
        }

        public void union(Node a, Node b) {
            Node pa = findHead(a);
            Node pb = findHead(b);
            if (pa != pb) {
                Node bigHead = sizeMap.get(pa) >= sizeMap.get(pb) ? pa : pb;
                Node smallHead = bigHead == pa ? pb : pa;
                parentMap.put(smallHead, bigHead);
                sizeMap.put(bigHead, sizeMap.get(bigHead) + sizeMap.get(smallHead));
                sizeMap.remove(smallHead);
            }
        }

        public int numSets() {
            return sizeMap.size();
        }
    }


    public static int numIslands2(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        UFSet2 ufSet = new UFSet2(grid);
        for (int j = 1; j < col; j++) {
            if (grid[0][j - 1] == '1' && grid[0][j] == '1') {
                ufSet.union(0, j - 1, 0, j);
            }
        }
        for (int i = 1; i < row; i++) {
            if (grid[i - 1][0] == '1' && grid[i][0] == '1') {
                ufSet.union(i - 1, 0, i, 0);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (grid[i][j] == '1') {
                    if (grid[i][j - 1] == '1') {
                        ufSet.union(i, j - 1, i, j);
                    }
                    if (grid[i - 1][j] == '1') {
                        ufSet.union(i - 1, j, i, j);
                    }
                }
            }
        }
        return ufSet.getNumSets();
    }

    public static class UFSet2 {

        int[] parent;
        int[] size;
        int[] path;
        int col;
        int numSets;

        public UFSet2(char[][] grid) {
            int row = grid.length;
            col = grid[0].length;
            ;
            int N = row * col;
            parent = new int[N];
            size = new int[N];
            path = new int[N];
            numSets = 0;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (grid[i][j] == '1') {
                        int idx = i * col + j;
                        parent[idx] = idx;
                        size[idx] = 1;
                        numSets++;
                    }
                }
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

        public void union(int r1, int c1, int r2, int c2) {
            int a = r1 * col + c1;
            int b = r2 * col + c2;
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


    public static int numIslands3(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int numSets = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (grid[i][j] == '1') {
                    infect(i, j, grid);
                    numSets++;
                }
            }
        }
        return numSets;
    }

    public static void infect(int i, int j, char[][] grid) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length) {
            return;
        }
        if (grid[i][j] != '1') {
            return;
        }
        grid[i][j] = 'd';
        infect(i - 1, j, grid);
        infect(i, j - 1, grid);
        infect(i + 1, j, grid);
        infect(i, j + 1, grid);
    }

}
