package systematic.section16_Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: duccio
 * @Date: 18, 04, 2022
 * @Description: Define a structure of graph representation, and give an example of creating such a graph.
 * @Note:
 */
public class Code01_GraphDefinition {

    // structure definition
    public static class Graph {
        HashMap<Integer, Node> nodeMap;
        HashSet<Edge> edges;

        public Graph() {
            nodeMap = new HashMap<>();
            edges = new HashSet<>();
        }
    }

    public static class Node {
        int value;          // for every node
        int inDegree;       // for to node
        int outDegree;      // for from node
        List<Node> nexts;   // for from node
        List<Edge> edges;   // for from node

        public Node(int val) {
            value = val;
            inDegree = 0;
            outDegree = 0;
            nexts = new ArrayList<>();
            edges = new ArrayList<>();
        }

    }

    public static class Edge {
        int weight;
        Node from;
        Node to;

        public Edge(int w, Node f, Node t) {
            weight = w;
            from = f;
            to = t;
        }
    }


    // an example of converting from another graph representation
    // a matrix M with shape (N, 3) contains all edges [weight, from, to]
    public static Graph createGraph(int[][] M) {
        Graph graph = new Graph();
        for (int[] ints : M) {
            int weight = ints[0];
            int from = ints[1];
            int to = ints[2];
            if (!graph.nodeMap.containsKey(from)) {
                graph.nodeMap.put(from, new Node(from));
            }
            if (!graph.nodeMap.containsKey(to)) {
                graph.nodeMap.put(to, new Node(to));
            }
            Node fromNode = graph.nodeMap.get(from);
            Node toNode = graph.nodeMap.get(to);
            Edge edge = new Edge(weight, fromNode, toNode);
            graph.edges.add(edge);
            fromNode.nexts.add(toNode);
            fromNode.edges.add(edge);
            fromNode.outDegree++;
            toNode.inDegree++;
        }
        return graph;
    }


}
