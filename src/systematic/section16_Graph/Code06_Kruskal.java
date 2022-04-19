package systematic.section16_Graph;
import systematic.section16_Graph.Code01_GraphDefinition.Graph;
import systematic.section16_Graph.Code01_GraphDefinition.Node;
import systematic.section16_Graph.Code01_GraphDefinition.Edge;

import java.util.*;

/**
 * @Author: duccio
 * @Date: 19, 04, 2022
 * @Description: Kruskal algorithm that generates a minimum weighted tree from a weighted graph.
 * @Note:   Sort all edges with ascending weight. At each step, pick an edge and check if it forms a loop. If it is not,
 *              add it to the returning edge set.
 *          ======
 *          1. Use a PriorityQueue for storing all edges.
 *          2. Use a UnionFindSet storing all nodes, used for checking if an edge forms a loop.
 */
public class Code06_Kruskal {

    public static Set<Edge> kruskal(Graph graph) {
        if (graph == null) {
            return null;
        }
        PriorityQueue<Edge> pq = new PriorityQueue<>((e1, e2) -> (e1.weight - e2.weight));
        pq.addAll(graph.edges);
        UFSet ufSet = new UFSet(graph.nodeMap.values());
        HashSet<Edge> ret = new HashSet<>();
        while (!pq.isEmpty()) {
            Edge cur = pq.poll();
            Node from = cur.from;
            Node to = cur.to;
            if (!ufSet.isSameSet(from, to)) {
                ret.add(cur);
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
            Node pa = findHead(a);
            Node pb = findHead(b);
            if (pa != pb) {
                Node bigHead = sizeMap.get(pa) > sizeMap.get(pb) ? pa : pb;
                Node smallHead = bigHead == pa ? pb : pa;
                parentMap.put(pb, pa);
                sizeMap.put(pa, sizeMap.get(pa) + sizeMap.get(pb));
                sizeMap.remove(pb);
            }
        }
    }


}
