package systematic.section16_Graph;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author: duccio
 * @Date: 18, 04, 2022
 * @Description: Topology search for a given graph representation where in-degree is not given.
 *      https://www.lintcode.com/problem/topological-sorting
 * @Note:   Sol1. If the longest path from node A is deeper than from B, then A has smaller topological order
 *                (not implemented here, similar to sol 2).
 *          Sol2. If the number of following nodes after node A is bigger than B, then A has smaller topological order.
 *                - Use an Info-ish class for wrapping number of following nodes.
 *                - Use a HashMap for storing info.
 *                - For collecting info of a node, recursively processing all following nodes.
 *                - Use long instead of int if number is potentially large. In this case, the self-defined comparator
 *                  needs modification because Comparator returns int.
 */
public class Code05_TopologySearchII {

    // a graph node representation
    // a graph is given by a list of such nodes
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<>();
        }
    }

    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        if (graph == null) {
            return null;
        }
        // collect info in a HashMap
        HashMap<DirectedGraphNode, Info> infoMap = new HashMap<>();
        for (DirectedGraphNode node : graph) {
            process(node, infoMap);
        }
        // add all info to a list, and sort by number of following nodes
        ArrayList<Info> infoList = new ArrayList<>(infoMap.values());
        infoList.sort((r1, r2) -> (r1.nodes >= r2.nodes ? -1 : 1));

        ArrayList<DirectedGraphNode> ret = new ArrayList<>();
        for (Info info : infoList) {
            ret.add(info.node);
        }
        return ret;
    }

    public static class Info {
        DirectedGraphNode node;
        long nodes;

        public Info(DirectedGraphNode n, long d) {
            node = n;
            nodes = d;
        }
    }

    public static Info process(DirectedGraphNode node, HashMap<DirectedGraphNode, Info> infoMap) {
        if (infoMap.containsKey(node)) {
            return infoMap.get(node);
        }
        long nodes = 0;
        for (DirectedGraphNode next : node.neighbors) {
            nodes += process(next, infoMap).nodes;
            // for sol 1, maxDepth = Math.max(maxDepth, process(next, infoMap).depth)
        }
        Info info = new Info(node, nodes + 1);
        infoMap.put(node, info);
        return info;
    }

}
