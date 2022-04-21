package systematic.section16_Graph;
import systematic.section16_Graph.Code01_GraphDefinition.Graph;
import systematic.section16_Graph.Code01_GraphDefinition.Node;
import systematic.section16_Graph.Code01_GraphDefinition.Edge;

import java.util.ArrayList;
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
 *          ======
 *          Ver1. Iteratively choose the current shortest node.
 *          Ver2. Optimize the iteration using a greater heap.
 */
public class Code08_Dijkstra {

    public static HashMap<Node, Integer> dijkstra1(Node start) {
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


    public static HashMap<Node, Integer> dijkstra2(Node start, int size) {
        MyHeap heap = new MyHeap(size);
        heap.addUpdateIgnore(start, 0);
        HashMap<Node, Integer> res = new HashMap<>();
        while (!heap.isEmpty()) {
            Record rc = heap.pop();
            Node cur = rc.node;
            int dist = rc.dist;
            for (Edge edge : cur.edges) {
                heap.addUpdateIgnore(edge.to, dist + edge.weight);
            }
            res.put(cur, dist);
        }
        return res;
    }

    public static class Record {
        Node node;
        int dist;

        public Record(Node n, int d) {
            node = n;
            dist = d;
        }
    }

    public static class MyHeap {
        Node[] nodes;
        HashMap<Node, Integer> indexMap;
        HashMap<Node, Integer> distMap;
        int heapSize;

        public MyHeap(int size) {
            nodes = new Node[size];
            indexMap = new HashMap<>();
            distMap = new HashMap<>();
            heapSize = 0;
        }

        public Record pop() {
            Node node = nodes[0];
            Record ret = new Record(node, distMap.get(node));
            swap(0, heapSize - 1);
            indexMap.put(node, -1);  // mark the pop node as -1 in indexMap
            distMap.remove(node);
            nodes[--heapSize] = null;
            heapify(0);
            return ret;
        }

        public void addUpdateIgnore(Node node, int dist) {
            if (!isEntered(node)) {
                indexMap.put(node, heapSize);
                distMap.put(node, dist);
                nodes[heapSize] = node;
                insertHeapify(heapSize++);
            }
            if (inHeap(node)) {
                distMap.put(node, Math.min(distMap.get(node), dist));
                insertHeapify(indexMap.get(node));
            }
        }

        public boolean isEntered(Node node) {
            return indexMap.containsKey(node);
        }

        public boolean inHeap(Node node) {
            return isEntered(node) && indexMap.get(node) != -1;
        }


        public void insertHeapify(int idx) {
            while (distMap.get(nodes[idx]) < distMap.get(nodes[(idx - 1) / 2])) {
                swap(idx, (idx - 1) / 2);
                idx = (idx - 1) / 2;
            }
        }

        public void heapify(int idx) {
            int left = 2 * idx + 1;
            while (left < heapSize) {
                int smaller = left + 1 < heapSize && distMap.get(nodes[left + 1]) < distMap.get(nodes[left]) ?
                        left + 1 : left;
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
