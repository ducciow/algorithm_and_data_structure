package systematic.section16_Graph;
import systematic.section16_Graph.Code01_GraphDefinition.Graph;
import systematic.section16_Graph.Code01_GraphDefinition.Node;
import systematic.section16_Graph.Code01_GraphDefinition.Edge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @Author: duccio
 * @Date: 19, 04, 2022
 * @Description: Dijkstra algorithm that returns the least weighted paths from a given node to all other nodes in a
 *      weighted graph.
 * @Note:   1. Initialize the distance from start node to itself as 0, and to other nodes as infinite.
 *          2. Iteratively choose the current shortest node, regard it as the intermediate node, and update distances
 *             to other nodes. Mark that intermediate node as explored.
 *          3. Stop when it cannot pick that kind of intermediate node.
 */
public class Code08_Dijkstra {

    public static HashMap<Node, Integer> dijkstra(Node start) {
        HashMap<Node, Integer> distMap = new HashMap<>();
        HashSet<Node> explored = new HashSet<>();
        distMap.put(start, 0);
        Node curMin = getCurMinUnexploredNode(distMap, explored);
        while (curMin != null) {
            int curDist = distMap.get(curMin);
            for (Edge edge : curMin.edges) {
                Node to = edge.to;
                int dist = edge.weight;
                if (!distMap.containsKey(to)) {
                    distMap.put(to, curDist + dist);
                } else {
                    distMap.put(to, Math.min(distMap.get(to), curDist + dist));
                }
            }
            explored.add(curMin);
            curMin = getCurMinUnexploredNode(distMap, explored);
        }
        return distMap;
    }

    private static Node getCurMinUnexploredNode(HashMap<Node, Integer> distMap, HashSet<Node> explored) {
        Node minNode = null;
        int minDist = Integer.MAX_VALUE;
        for (Map.Entry<Node, Integer> entry : distMap.entrySet()) {
            Node node = entry.getKey();
            int dist = entry.getValue();
            if (!explored.contains(node) && dist < minDist) {
                minNode = node;
                minDist = dist;
            }
        }
        return minNode;
    }

}
