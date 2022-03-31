package primary.class04;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 21, 03, 2022
 * @Description: Implement queue and stack using uni-directional linked list
 * @Note:
 */
public class Code13_LinkedListToQueueAndStack {
    public static void main(String[] args) {
        System.out.println("Test on MyQueue:");
        validateMyQueue();
        System.out.println("Test on MyStack:");
        validateMyStack();
    }

    // Node
    public static class Node<V> {
        V value;
        Node<V> next;

        public Node(V val) {
            value = val;
        }
    }

    // Queue
    public static class MyQueue<V> {
        private int size;
        Node<V> head;
        Node<V> tail;

        public MyQueue() {
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public void offer(V val) {
            Node<V> cur = new Node<V>(val);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                tail.next = cur;
                tail = cur;
            }
            size++;
        }

        public V poll() {
            V res = null;
            if (head != null) {
                res = head.value;
                head = head.next;
                size--;
            }
            if (head == null) {
                tail = null;
            }
            return res;
        }

        public V peek() {
            return head == null ? null : head.value;
        }
    }

    // Stack
    public static class MyStack<V> {
        private int size;
        Node<V> top;

        public MyStack() {
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public void push(V val) {
            Node<V> cur = new Node<V>(val);
            cur.next = top;
            top = cur;
            size++;
        }

        public V pop() {
            V res = null;
            if (top != null) {
                res = top.value;
                top = top.next;
                size--;
            }
            return res;
        }

        public V peek() {
            return top == null ? null : top.value;
        }
    }

    public static void validateMyQueue() {
        int numTest = 100000;
        int maxVal = 200;
        MyQueue<Integer> myQueue = new MyQueue<>();
        Queue<Integer> testQueue = new LinkedList<>();
        for (int i = 0; i < numTest; i++) {
            if (myQueue.isEmpty() != testQueue.isEmpty()) {
                System.out.println("Fail on isEmpty()");
                return;
            }
            if (myQueue.size() != testQueue.size()) {
                System.out.println("Fail on size()");
                return;
            }
            double decide = Math.random();
            if (decide < 0.33) {
                int val = (int) (Math.random() * maxVal);
                myQueue.offer(val);
                testQueue.offer(val);
            } else if (decide < 0.66) {
                if (!myQueue.isEmpty()) {
                    int num1 = myQueue.poll();
                    int num2 = testQueue.poll();
                    if (num1 != num2) {
                        System.out.println("Failed on poll()");
                        System.out.println(num1);
                        System.out.println(num2);
                        return;
                    }
                }
            } else {
                if (!myQueue.isEmpty()) {
                    if (!myQueue.peek().equals(testQueue.peek())) {
                        System.out.println("Failed on peek()");
                        return;
                    }
                }
            }
        }
        if (myQueue.size() != testQueue.size()) {
            System.out.println("Failed on size()..");
            return;
        }
        while (!myQueue.isEmpty()) {
            int num1 = myQueue.poll();
            int num2 = testQueue.poll();
            if (num1 != num2) {
                System.out.println("Failed on poll()..");
                System.out.println(num1);
                System.out.println(num2);
                return;
            }
        }
        System.out.println("Test passed!");
    }

    public static void validateMyStack() {
        int numTest = 100000;
        int maxVal = 200;
        MyStack<Integer> myStack = new MyStack<>();
        Stack<Integer> testStack = new Stack<>();
        for (int i = 0; i < numTest; i++) {
            if (myStack.isEmpty() != testStack.isEmpty()) {
                System.out.println("Failed on isEmpty()");
                return;
            }
            if (myStack.size() != testStack.size()) {
                System.out.println("Failed on size()");
                return;
            }
            double decide = Math.random();
            if (decide < 0.33) {
                int val = (int) (Math.random() * maxVal);
                myStack.push(val);
                testStack.push(val);
            } else if (decide < 0.66) {
                if (!myStack.isEmpty()) {
                    int num1 = myStack.pop();
                    int num2 = testStack.pop();
                    if (num1 != num2) {
                        System.out.println("Failed on pop()");
                        System.out.println(num1);
                        System.out.println(num2);
                        return;
                    }
                }
            } else {
                if (!myStack.isEmpty()) {
                    if (!myStack.peek().equals(testStack.peek())) {
                        System.out.println("Failed on peek()");
                        return;
                    }
                }
            }
        }
        if (myStack.size() != testStack.size()) {
            System.out.println("Failed on size()..");
            return;
        }
        while (!myStack.isEmpty()) {
            int num1 = myStack.pop();
            int num2 = testStack.pop();
            if (num1 != num2) {
                System.out.println("Failed on pop()..");
                System.out.println(num1);
                System.out.println(num2);
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
