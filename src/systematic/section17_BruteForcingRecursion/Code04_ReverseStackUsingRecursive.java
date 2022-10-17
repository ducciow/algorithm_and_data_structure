package systematic.section17_BruteForcingRecursion;

import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 21, 04, 2022
 * @Description: Reversing a stack using recursion solely, ie. no extra data structures.
 * @Note:   - Key idea, recursively:
 *              1. get and keep the bottom item.
 *              2. reverse by calling itself.
 *              3. push the bottom back.
 *          - To get the bottom item, define bottom(), which recursively:
 *              1. get and keep the top item.
 *              2. get the bottom item by calling itself.
 *              3. push the top back.
 *              4. return the bottom.
 */
public class Code04_ReverseStackUsingRecursive {

    public static void main(String[] args) {
        Stack<Integer> test = new Stack<>();
        test.push(1);
        test.push(2);
        test.push(3);
        test.push(4);
        test.push(5);
        reverse(test);
        while (!test.isEmpty()) {
            System.out.println(test.pop());
        }
    }

    public static void reverse(Stack<Integer> stack) {
        if (stack == null || stack.isEmpty()) {
            return;
        }
        int btm = bottom(stack);
        reverse(stack);
        stack.push(btm);
    }

    public static int bottom(Stack<Integer> stack) {
        if (stack.size() == 1) {
            return stack.pop();
        }
        int top = stack.pop();
        int btm = bottom(stack);
        stack.push(top);
        return btm;
    }

}
