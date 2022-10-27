package section16_Graph;
import section16_Graph.Code01_GraphDefinition.Graph;
import section16_Graph.Code01_GraphDefinition.Node;
import section16_Graph.Code01_GraphDefinition.Edge;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @Author: duccio
 * @Date: 19, 04, 2022
 * @Description: Prim algorithm that generates a minimum weighted tree from a weighted graph.
 * @Note:   a) Key idea:
 *             - Pick a starting node, mark it as explored, and choose the least weighted edge from it. If this
 *               edge's to_node has not been explored, add this edge to the returning edge set and mark the to_node
 *               as explored.
 *          b) For implementation:
 *             - Use a HashSet for marking explored nodes.
 *             - Use a PriorityQueue for storing edges of the current from_node in ascending weight.
 *             - Use an outer for-loop in case disjoint nodes are missing.
 */
public class Code07_Prim {

    public static Set<Edge> prim(Graph graph) {
        if (graph == null) {
            return null;
        }
        // for marking explored nodes
        HashSet<Node> explored = new HashSet<>();
        // for storing edges of the current from_node in ascending weight
        PriorityQueue<Edge> pq = new PriorityQueue<>((e1, e2) -> (e1.weight - e2.weight));
        // the returning set
        HashSet<Edge> ret = new HashSet<>();
        for (Node node : graph.nodeMap.values()) {
            if (!explored.contains(node)) {
                explored.add(node);
                pq.addAll(node.edges);
                while (!pq.isEmpty()) {
                    Edge edge = pq.poll();
                    Node to = edge.to;
                    if (!explored.contains(to)) {
                        explored.add(to);
                        ret.add(edge);
                        pq.addAll(to.edges);
                    }
                }
            }
            // can break here if the graph is connected
        }
        return ret;
    }

}
