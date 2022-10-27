package section16_Graph;
import section16_Graph.Code01_GraphDefinition.Node;
import section16_Graph.Code01_GraphDefinition.Edge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @Author: duccio
 * @Date: 19, 04, 2022
 * @Description: Dijkstra algorithm that returns the least weighted paths from a given node to all other nodes in a
 *      weighted graph.
 * @Note:   a) Key idea:
 *             1. Initialize the distance from the start node to itself as 0.
 *             2. Iteratively choose the current nearest node that is unexplored and can be reached, update the min
 *                distance to it as well as distances to its to_nodes. Mark this nearest node as explored.
 *             3. Stop when it cannot pick an unexplored node any more.
 *          b) For implementation:
 *             - Ver1. Iteratively choose the current nearest node.
 *             - Ver2. Optimize the iteration using a greater/customized heap, which also handles all info in a
 *                     wrapper class.
 */
public class Code08_Dijkstra {

    // ver. 1
    public static HashMap<Node, Integer> dijkstra1(Node start) {
        // for tracking the distances to every other node, also the returning map
        HashMap<Node, Integer> distMap = new HashMap<>();
        distMap.put(start, 0);
        // for marking explored nodes
        HashSet<Node> explored = new HashSet<>();
        // get the nearest unexplored node, which is the start node at this moment
        Node minNode = getCurMinUnexploredNode(distMap, explored);
        while (minNode != null) {
            int curDist = distMap.get(minNode);
            for (Edge edge : minNode.edges) {
                Node to = edge.to;
                int dist = edge.weight;
                if (!distMap.containsKey(to)) {
                    distMap.put(to, curDist + dist);
                } else {
                    distMap.put(to, Math.min(distMap.get(to), curDist + dist));
                }
            }
            explored.add(minNode);
            minNode = getCurMinUnexploredNode(distMap, explored);
        }
        return distMap;
    }

    // a vanilla method to get the nearest unexplored node
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


    // ver. 2
    // needs the number of nodes as an extra input to initialize the heap
    public static HashMap<Node, Integer> dijkstra2(Node start, int size) {
        MyHeap heap = new MyHeap(size);
        heap.addUpdateIgnore(start, 0);
        HashMap<Node, Integer> distMap = new HashMap<>();
        while (!heap.isEmpty()) {
            Info info = heap.pop();
            Node node = info.node;
            int dist = info.dist;
            for (Edge edge : node.edges) {
                heap.addUpdateIgnore(edge.to, dist + edge.weight);
            }
            distMap.put(node, dist);
        }
        return distMap;
    }

    public static class Info {
        Node node;
        int dist;

        public Info(Node n, int d) {
            node = n;
            dist = d;
        }
    }

    public static class MyHeap {
        Node[] nodes;  // main structure of a heap
        int heapSize;  // indicate the current size as well as the next available position
        HashMap<Node, Integer> indexMap;  // get index by node
        HashMap<Node, Integer> distMap;   // get dist by node

        public MyHeap(int size) {
            nodes = new Node[size];
            indexMap = new HashMap<>();
            distMap = new HashMap<>();
            heapSize = 0;
        }

        public Info pop() {
            Node node = nodes[0];
            Info ret = new Info(node, distMap.get(node));
            swap(0, heapSize - 1);
            indexMap.put(node, -1);  // mark the pop node as -1 in indexMap, indicating explored
            distMap.remove(node);
            nodes[--heapSize] = null;
            heapify(0);
            return ret;
        }

        public boolean hasEntered(Node node) {
            return indexMap.containsKey(node);
        }

        public boolean inHeap(Node node) {
            return hasEntered(node) && indexMap.get(node) != -1;
        }

        // KEY FUNCTION
        public void addUpdateIgnore(Node node, int dist) {
            // add
            if (!hasEntered(node)) {
                indexMap.put(node, heapSize);
                distMap.put(node, dist);
                nodes[heapSize] = node;
                heapInsert(heapSize++);
            }
            // update
            if (inHeap(node)) {
                distMap.put(node, Math.min(distMap.get(node), dist));
                heapInsert(indexMap.get(node));
            }
        }

        // swap upwards from a given position
        public void heapInsert(int idx) {
            int parent = (idx - 1) / 2;
            while (distMap.get(nodes[idx]) < distMap.get(nodes[parent])) {
                swap(idx, parent);
                idx = parent;
                parent = (idx - 1) / 2;
            }
        }

        // swap downwards from a given position
        public void heapify(int idx) {
            int left = 2 * idx + 1;
            while (left < heapSize) {
                int smaller = left + 1 < heapSize && distMap.get(nodes[left + 1]) < distMap.get(nodes[left]) ?
                        left + 1
                        : left;
                if (distMap.get(nodes[smaller]) < distMap.get(nodes[idx])) {
                    swap(idx, smaller);
                    idx = smaller;
                    left = 2 * idx + 1;
                } else {
                    break;
                }
            }
        }

        public void swap(int idx1, int idx2) {
            Node tmp = nodes[idx1];
            nodes[idx1] = nodes[idx2];
            nodes[idx2] = tmp;
            indexMap.put(nodes[idx1], idx1);
            indexMap.put(nodes[idx2], idx2);
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

    }

}
