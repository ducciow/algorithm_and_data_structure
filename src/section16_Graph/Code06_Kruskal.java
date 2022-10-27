package section16_Graph;
import section16_Graph.Code01_GraphDefinition.Graph;
import section16_Graph.Code01_GraphDefinition.Node;
import section16_Graph.Code01_GraphDefinition.Edge;

import java.util.*;

/**
 * @Author: duccio
 * @Date: 19, 04, 2022
 * @Description: Kruskal algorithm that generates a minimum weighted tree from a weighted graph.
 * @Note:   a) Key idea:
 *            - Sort all edges with ascending weight. At each step, pick an edge and check if it forms a loop.
 *              If it is not, add it to the returning edge set.
 *          b) For implementation:
 *            - Use a PriorityQueue for storing all edges.
 *            - Use a UnionFindSet storing all nodes, used for checking if an edge forms a loop.
 */
public class Code06_Kruskal {

    public static Set<Edge> kruskal(Graph graph) {
        if (graph == null) {
            return null;
        }
        // use a PriorityQueue for storing all edges with ascending weight
        PriorityQueue<Edge> pq = new PriorityQueue<>((e1, e2) -> (e1.weight - e2.weight));
        pq.addAll(graph.edges);
        // use a UnionFindSet storing all nodes
        UFSet ufSet = new UFSet(graph.nodeMap.values());
        // the returning set
        HashSet<Edge> ret = new HashSet<>();
        // dynamically query each edge
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            Node from = edge.from;
            Node to = edge.to;
            if (!ufSet.isSameSet(from, to)) {
                ret.add(edge);
                ufSet.union(from, to);
            }
        }
        return ret;
    }

    public static class UFSet {
        HashMap<Node, Node> parentMap;
        HashMap<Node, Integer> sizeMap;

        public UFSet(Collection<Node> nodes) {
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

        public boolean isSameSet(Node a, Node b) {
            return findHead(a) == findHead(b);
        }

        public void union(Node a, Node b) {
            Node ha = findHead(a);
            Node hb = findHead(b);
            if (ha != hb) {
                Node bigHead = sizeMap.get(ha) > sizeMap.get(hb) ? ha : hb;
                Node smallHead = bigHead == ha ? hb : ha;
                parentMap.put(smallHead, bigHead);
                sizeMap.put(bigHead, sizeMap.get(bigHead) + sizeMap.get(bigHead));
                sizeMap.remove(smallHead);
            }
        }
    }


}
