package systematic.section16_Graph;
import systematic.section16_Graph.Code01_GraphDefinition.Node;

import java.util.HashSet;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 18, 04, 2022
 * @Description: Depth first search of a graph
 * @Note:   1. Use a stack for DFS, and use a set for marking processed items.
 *          2. Items are added to stack and set at the same time.
 *          3. When add a toNode, add its fromNode as well, and the current stack is just the path from start node.
 *          4. Process an item when add.
 */
public class Code03_DFS {

    public static void dfs(Node start) {
        if (start == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        HashSet<Node> set = new HashSet<>();
        stack.push(start);
        set.add(start);
        System.out.println(start.value);
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    System.out.println(next.value);
                    break;
                }
            }
        }
    }

}
