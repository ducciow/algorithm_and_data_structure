package section04_LinkedList_Queue_Stack;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 30, 03, 2022
 * @Description: Implement stack using queue.
 * @Note:   - Use two queues mainQ and helperQ.
 *          - Every time for pop/peek:
 *              a) dump from mainQ to helperQ until mainQ has only one item left, then return it.
     *          b) Swap those two queues, so that every time push can directly operate on mainQ.
 */
public class Code07_QueueToStack {

    public static class MyStack {
        Queue<Integer> mainQ;
        Queue<Integer> helperQ;

        public MyStack() {
            mainQ = new LinkedList<>();
            helperQ = new LinkedList<>();
        }

        public void push(int val) {
            mainQ.offer(val);
        }

        public int pop() {
            if (mainQ.isEmpty()) {
                throw new RuntimeException("Nothing to poll!");
            }
            while (mainQ.size() > 1) {
                helperQ.offer(mainQ.poll());
            }
            int ans = mainQ.poll();
            swapQ();
            return ans;
        }

        public int peek() {
            if (mainQ.isEmpty()) {
                throw new RuntimeException("Nothing to poll!");
            }
            while (mainQ.size() > 1) {
                helperQ.offer(mainQ.poll());
            }
            int ans = mainQ.peek();
            helperQ.offer(mainQ.poll());
            swapQ();
            return ans;
        }

        public void swapQ() {
            Queue<Integer> tmp = mainQ;
            mainQ = helperQ;
            helperQ = tmp;
        }

        public boolean isEmpty() {
            return mainQ.isEmpty();
        }
    }


    public static void main(String[] args) {
        int numTest = 10000;
        int maxVal = 200;
        MyStack stack1 = new MyStack();
        Stack<Integer> stack2 = new Stack<>();
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            if (stack2.isEmpty()) {
                if (!stack1.isEmpty()) {
                    System.out.println("Failed on isEmpty()");
                    return;
                }
                int num = (int) (Math.random() * maxVal) + 1;
                stack1.push(num);
                stack2.push(num);
            } else {
                if (Math.random() < 0.25) {
                    int num = (int) (Math.random() * maxVal);
                    stack1.push(num);
                    stack2.push(num);
                } else if (Math.random() < 0.5) {
                    if (stack1.peek() != stack2.peek()) {
                        System.out.println("Failed on peek()");
                        return;
                    }
                } else if (Math.random() < 0.75) {
                    if (stack1.pop() != stack2.pop()) {
                        System.out.println("Failed on pop()");
                        return;
                    }
                } else {
                    if (stack1.isEmpty() != stack2.isEmpty()) {
                        System.out.println("Failed on isEmpty()");
                        return;
                    }
                }
            }
        }
        System.out.println("Test passed!");
    }

}
