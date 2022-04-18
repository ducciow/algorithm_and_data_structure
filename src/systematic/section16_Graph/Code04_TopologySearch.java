package systematic.section16_Graph;

import systematic.section16_Graph.Code01_GraphDefinition.Graph;
import systematic.section16_Graph.Code01_GraphDefinition.Node;

import java.util.*;

/**
 * @Author: duccio
 * @Date: 18, 04, 2022
 * @Description: For each directed edge A -> B in a graph, A must be processed before B.
 * @Note:   1. Only process nodes with 0 inDegree.
 *          2. Use a HashMap dynamically storing inDegree, and use a queue storing nodes with 0 inDegree.
 *          3. Each time after processing a node, update inDegree of its nexts.
 */
public class Code04_TopologySearch {

    public static List<Node> topologySearch(Graph graph) {
        if (graph == null) {
            return null;
        }
        HashMap<Node, Integer> inMap = new HashMap<>();
        Queue<Node> zeroInQ = new LinkedList<>();
        for (Node node : graph.nodeMap.values()) {
            inMap.put(node, node.inDegree);
            if (node.inDegree == 0) {
                zeroInQ.add(node);
            }
        }
        List<Node> ret = new ArrayList<>();
        while (!zeroInQ.isEmpty()) {
            Node cur = zeroInQ.poll();
            ret.add(cur);
            for (Node next : cur.nexts) {
                inMap.put(next, inMap.get(next) - 1);
                if (next.inDegree == 0) {
                    zeroInQ.add(next);
                }
            }
        }
        return ret;
    }

}
