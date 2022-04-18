package systematic.section16_Graph;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @Author: duccio
 * @Date: 18, 04, 2022
 * @Description: Topology search for a given graph representation, and inDegree is not needed.
 *      https://www.lintcode.com/problem/topological-sorting
 * @Note:   Ver1. If the longest path from A is deeper than from B, then A has smaller topological order.
 *          Ver2. If the number of following nodes after A is bigger than B, then A has smaller topological order. <---
 *          ======
 *          1. Use an Info-ish class for wrapping depth/nodes.
 *          2. Use long instead of int if number is potentially large. In this case, the self-defined comparator needs
 *             modification because Comparator returns int.
 */
public class Code05_TopologySearchII {

    // given a graph representation
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
        HashMap<DirectedGraphNode, Record> recMap = new HashMap<>();
        for (DirectedGraphNode node : graph) {
            process(node, recMap);
        }
        ArrayList<Record> recArr = new ArrayList<>(recMap.values());
        recArr.sort((r1, r2) -> (r1.nodes >= r2.nodes ? -1 : 1));
        ArrayList<DirectedGraphNode> ret = new ArrayList<>();
        for (Record record : recArr) {
            ret.add(record.node);
        }
        return ret;
    }

    public static class Record {
        DirectedGraphNode node;
        long nodes;

        public Record(DirectedGraphNode n, long d) {
            node = n;
            nodes = d;
        }
    }

    public static Record process(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> recMap) {
        if (recMap.containsKey(cur)) {
            return recMap.get(cur);
        }
        long nodes = 0;
        for (DirectedGraphNode next : cur.neighbors) {
            nodes += process(next, recMap).nodes;
            // for ver1. maxDepth = Math.max(maxDepth, process(next, recMap).depth)
        }
        Record ret = new Record(cur, nodes + 1);
        recMap.put(cur, ret);
        return ret;
    }

}
