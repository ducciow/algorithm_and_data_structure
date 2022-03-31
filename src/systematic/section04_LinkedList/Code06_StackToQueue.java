package systematic.section04_LinkedList;

import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 30, 03, 2022
 * @Description: Implement queue using stack
 * @Note:   1. Use two stacks
 *          2. stack1 is only used for offer, and stack2 is only used for poll/peek
 *          3. At ant moment, stack1 can dump its items to stack2, requiring:
 *              a) stack2 is empty, and
 *              b) stack1 must dump all of its items to stack2
 */
public class Code06_StackToQueue {

    public static void main(String[] args) {
        MyQueue test = new MyQueue();
        test.offer(1);
        test.offer(2);
        test.offer(3);
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
    }

    public static class MyQueue {
        Stack<Integer> mainStack;
        Stack<Integer> helperStack;

        public MyQueue() {
            mainStack = new Stack<>();
            helperStack = new Stack<>();
        }

        public void offer(int val) {
            mainStack.push(val);
        }

        public int poll() {
            if (!helperStack.isEmpty()) {
                return helperStack.pop();
            } else if (!mainStack.isEmpty()) {
                while (!mainStack.isEmpty()) {
                    helperStack.push(mainStack.pop());
                }
                return helperStack.pop();
            } else {
                throw new RuntimeException("Nothing to poll!");
            }
        }

        public int peek() {
            if (!helperStack.isEmpty()) {
                return helperStack.peek();
            } else if (!mainStack.isEmpty()) {
                while (!mainStack.isEmpty()) {
                    helperStack.push(mainStack.pop());
                }
                return helperStack.peek();
            } else {
                throw new RuntimeException("Nothing to poll!");
            }
        }
    }
}
