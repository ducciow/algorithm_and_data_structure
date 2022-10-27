package section16_Graph;

import section16_Graph.Code01_GraphDefinition.Graph;
import section16_Graph.Code01_GraphDefinition.Node;

import java.util.*;

/**
 * @Author: duccio
 * @Date: 18, 04, 2022
 * @Description: For each directed edge A -> B in a graph, A must be processed before B.
 * @Note:   - Only process nodes with 0 inDegree.
 *          - To do that:
 *              a) Use a HashMap dynamically storing inDegree.
 *              b) Use a queue dynamically storing nodes with 0 inDegree.
 *              c) Each time after processing a node, update inDegree of its next nodes.
 *              d) When update next nodes, query from HashMap rather than directly from node attribute.
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
            Node node = zeroInQ.poll();
            ret.add(node);
            for (Node next : node.nexts) {
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0) {
                    zeroInQ.add(next);
                }
            }
        }
        return ret;
    }

}
