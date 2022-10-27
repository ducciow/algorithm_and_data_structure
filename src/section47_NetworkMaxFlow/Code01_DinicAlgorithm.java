package section47_NetworkMaxFlow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @Author: duccio
 * @Date: 14, 06, 2022
 * @Description: https://lightoj.com/problem/internet-bandwidth
 * @Note:
 */
public class Code01_DinicAlgorithm {

    public static class Edge {
        public int from;
        public int to;
        public int cap;

        public Edge(int a, int b, int c) {
            from = a;
            to = b;
            cap = c;
        }
    }

    public static class Dinic {

        private int N;
        private ArrayList<Edge> edges;
        private ArrayList<ArrayList<Integer>> nexts;
        private int[] depth;
        private int[] outs;

        public Dinic(int nums) {
            N = nums + 1;
            edges = new ArrayList<>();
            nexts = new ArrayList<>();
            for (int i = 0; i < N; i++) {
                nexts.add(new ArrayList<>());
            }
            depth = new int[N];
            outs = new int[N];
        }

        public void addEdge(int from, int to, int cap) {
            int m = edges.size();
            // add this edge
            edges.add(new Edge(from, to, cap));
            nexts.get(from).add(m);
            // add the reverse edge
            edges.add(new Edge(to, from, 0));
            nexts.get(to).add(m + 1);
        }

        public int maxFlow(int start, int dest) {
            int flow = 0;
            while (bfs(start, dest)) {
                Arrays.fill(outs, 0);
                flow += dfs(start, dest, Integer.MAX_VALUE);
                Arrays.fill(depth, 0);
            }
            return flow;
        }


        // check if dest can be reached from start, in bfs order
        public boolean bfs(int start, int dest) {
            LinkedList<Integer> queue = new LinkedList<>();
            boolean[] visited = new boolean[N];
            queue.addFirst(start);
            visited[start] = true;
            while (!queue.isEmpty()) {
                int u = queue.pollLast();
                ArrayList<Integer> out = nexts.get(u);
                for (int i = 0; i < out.size(); i++) {
                    Edge e = edges.get(out.get(i));
                    int v = e.to;
                    if (!visited[v] && e.cap > 0) {  // if v is not visited and the edge to it has positive capacity
                        visited[v] = true;
                        depth[v] = depth[u] + 1;
                        if (v == dest) {
                            break;
                        }
                        queue.addFirst(v);
                    }
                }
            }
            return visited[dest];
        }

        // calculate the flow from current to dest with assigned load
        public int dfs(int cur, int dest, int load) {
            if (cur == dest || load == 0) {
                return load;
            }
            int f = 0;
            int flow = 0;
            ArrayList<Integer> out = nexts.get(cur);
            for (; outs[cur] < out.size(); outs[cur]++) {  // try the next out node from cur node globally
                int ei = out.get(outs[cur]);
                Edge e = edges.get(ei);
                Edge eBack = edges.get(ei ^ 1);
                if (depth[e.to] == depth[cur] + 1 && (f = dfs(e.to, dest, Math.min(e.cap, load))) != 0) {
                    e.cap -= f;
                    eBack.cap += f;
                    flow += f;
                    load -= f;
                    if (load <= 0) {
                        break;
                    }
                }
            }
            return flow;
        }
    }


    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int cases = cin.nextInt();
        for (int i = 1; i <= cases; i++) {
            int n = cin.nextInt();
            int s = cin.nextInt();
            int t = cin.nextInt();
            int m = cin.nextInt();
            Dinic dinic = new Dinic(n);
            for (int j = 0; j < m; j++) {
                int from = cin.nextInt();
                int to = cin.nextInt();
                int weight = cin.nextInt();
                dinic.addEdge(from, to, weight);
                dinic.addEdge(to, from, weight);
            }
            int ans = dinic.maxFlow(s, t);
            System.out.println("Case " + i + ": " + ans);
        }
        cin.close();
    }

}
