package systematic.section16_Graph;
import systematic.section16_Graph.Code01_GraphDefinition.Node;

import java.util.HashSet;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 18, 04, 2022
 * @Description: Depth First Search of a graph.
 * @Note:   a) Use a stack for DFS, and use a set for marking processed items.
 *          b) Items are added to stack and set at the same time.
 *          c) Process an item when ADD.
 *          d) When pop an item from the stack, only process one unmarked toNode from its nexts.
 *          e) When add a toNode to the stack and set, push its fromNode back to the stack first, and thus the
 *             current stack is just the path from start node so far.
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
        System.out.println(start.value);  // process when add
        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    stack.push(cur);  // push the from node back to the stack
                    stack.push(next);
                    set.add(next);
                    System.out.println(next.value);  // process when add
                    break;  // only process one next node
                }
            }
        }
    }

}
