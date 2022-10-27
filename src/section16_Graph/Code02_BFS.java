package section16_Graph;
import section16_Graph.Code01_GraphDefinition.Node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: duccio
 * @Date: 18, 04, 2022
 * @Description: Breadth First Search of a graph.
 * @Note:   a) Use a queue for BFS, and a set for marking processed item.
 *          b) Items are added to queue and set at the same time.
 *          c) Process an item when POLL.
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
            System.out.println(cur.value);  // process when poll
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }

}
