package systematic.section16_Graph;
import systematic.section16_Graph.Code01_GraphDefinition.Node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: duccio
 * @Date: 18, 04, 2022
 * @Description: Breadth first search of a graph
 * @Note:   1. Use a queue for BFS, and a set for marking processed item.
 *          2. Items are added to queue and set at the same time.
 *          3. Process an item when poll.
 */
public class Code02_BFS {

    public static void bfs(Node start) {
        if (start == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(start);
        set.add(start);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.println(cur.value);
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }

}
