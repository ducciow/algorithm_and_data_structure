package systematic.section16_Graph;
import systematic.section16_Graph.Code01_GraphDefinition.Graph;
import systematic.section16_Graph.Code01_GraphDefinition.Node;
import systematic.section16_Graph.Code01_GraphDefinition.Edge;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @Author: duccio
 * @Date: 19, 04, 2022
 * @Description: Prim algorithm that generates a minimum weighted tree from a weighted graph.
 * @Note:   Pick a starting node, mark it as explored, and choose the least weighted edge connected from it. If that
 *              edge's to node has not been explored, add that edge to returning edge set and mark that to node as
 *              explored. Use an outer for-loop to include disjoint nodes.
 */
public class Code07_Prim {

    public static Set<Edge> prim(Graph graph) {
        if (graph == null) {
            return null;
        }
        HashSet<Node> explored = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>((e1, e2) -> (e1.weight - e2.weight));
        HashSet<Edge> ret = new HashSet<>();
        for (Node node : graph.nodeMap.values()) {
            if (!explored.contains(node)) {
                explored.add(node);
                pq.addAll(node.edges);
                while (!pq.isEmpty()) {
                    Edge cur = pq.poll();
                    Node to = cur.to;
                    if (!explored.contains(to)) {
                        explored.add(to);
                        ret.add(cur);
                        pq.addAll(to.edges);
                    }
                }
            }
            // break;
        }
        return ret;
    }

}
