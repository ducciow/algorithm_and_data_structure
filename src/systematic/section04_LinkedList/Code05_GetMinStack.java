package systematic.section04_LinkedList;

import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 30, 03, 2022
 * @Description: Implement a stack with an extra function - peek the current minimum value.
 * @Note:   - Use two stacks. One stack is used as usual, while the other stack is used for tracking minimum values
 *            by only pushing new items when the new item is smaller than current minimum, otherwise push current
 *            minimum again.
 */
public class Code05_GetMinStack {

    public static void main(String[] args) {
        MyStack stack = new MyStack();
        stack.push(3);
        System.out.println(stack.getMin());
        stack.push(4);
        System.out.println(stack.getMin());
        stack.push(1);
        System.out.println(stack.getMin());
        System.out.println(stack.pop());
        System.out.println(stack.getMin());
    }

    public static class MyStack {
        Stack<Integer> mainStack;
        Stack<Integer> minStack;

        public MyStack() {
            mainStack = new Stack<>();
            minStack = new Stack<>();
        }

        public void push(int val) {
            mainStack.push(val);
            if (!minStack.isEmpty() && minStack.peek() < val) {
                minStack.push(minStack.peek());
            } else {
                minStack.push(val);
            }
        }

        public int pop() {
            if (mainStack.isEmpty()) {
                throw new RuntimeException("Stack is empty!");
            }
            minStack.pop();
            return mainStack.pop();
        }

        public int getMin() {
            if (mainStack.isEmpty()) {
                throw new RuntimeException("Stack is empty!");
            }
            return minStack.peek();
        }
    }
}
