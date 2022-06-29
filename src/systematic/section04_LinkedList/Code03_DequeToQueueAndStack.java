package systematic.section04_LinkedList;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 30, 03, 2022
 * @Description: Implement stack and queue using dequeue.
 * @Note:   Deque:
 *          - Variables: head, tail.
 *          - Functions: offerHead(), offerTail(), pollHead(), pollTail(), isEmpty().
 *          - Remember to release pointers when poll.
 *          ======
 *          Stack:
 *          - offerHead and pollHead.
 *          ======
 *          Queue:
 *          - offerHead and pollTail.
 */
public class Code03_DequeToQueueAndStack {

    public static void main(String[] args) {
        validate();
    }

    public static class BiNode<T> {
        T value;
        BiNode<T> pre;
        BiNode<T> next;

        public BiNode(T val) {
            value = val;
        }
    }

    public static class Deque<T> {
        BiNode<T> head;
        BiNode<T> tail;

        public void offerHead(T val) {
            BiNode<T> cur = new BiNode<T>(val);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                head.pre = cur;
                cur.next = head;
                head = cur;
            }
        }

        public void offerTail(T val) {
            BiNode<T> cur = new BiNode<T>(val);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                tail.next = cur;
                cur.pre = tail;
                tail = cur;
            }
        }

        public T pollHead() {
            if (head == null) {
                return null;
            }
            BiNode<T> ret = head;
            head = head.next;
            if (head != null) {
                head.pre = null;
            } else {
                tail = null;
            }
            ret.next = null;
            return ret.value;
        }

        public T pollTail() {
            if (head == null) {
                return null;
            }
            BiNode<T> ret = tail;
            tail = tail.pre;
            if (tail == null) {
                head = null;
            } else {
                tail.next = null;
            }
            ret.pre = null;
            return ret.value;
        }

        public boolean isEmpty() {
            return head == null;
        }
    }

    public static class MyStack<T> {
        Deque<T> queue;

        public MyStack() {
            queue = new Deque<>();
        }

        public void push(T val) {
            queue.offerHead(val);
        }

        public T pop() {
            return queue.pollHead();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    public static class MyQueue<T> {
        Deque<T> queue;

        public MyQueue() {
            queue = new Deque<>();
        }

        public void offer(T val) {
            queue.offerHead(val);
        }

        public T poll() {
            return queue.pollTail();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }


    public static boolean isEqual(Integer o1, Integer o2) {
        if (o1 == null && o2 != null) {
            return false;
        }
        if (o1 != null && o2 == null) {
            return false;
        }
        if (o1 == null && o2 == null) {
            return true;
        }
        return o1.equals(o2);
    }

    public static void validate() {
        int testTimes = 100000;
        int perTestNumData = 100;
        int maxValue = 10000;
        for (int i = 0; i < testTimes; i++) {
            MyStack<Integer> myStack = new MyStack<>();
            MyQueue<Integer> myQueue = new MyQueue<>();
            Stack<Integer> stack = new Stack<>();
            Queue<Integer> queue = new LinkedList<>();
            for (int j = 0; j < perTestNumData; j++) {
                int num1 = (int) (Math.random() * maxValue);
                if (stack.isEmpty()) {
                    myStack.push(num1);
                    stack.push(num1);
                } else {
                    if (Math.random() < 0.5) {
                        myStack.push(num1);
                        stack.push(num1);
                    } else {
                        if (!isEqual(myStack.pop(), stack.pop())) {
                            System.out.println("Failed on MyStack pop()");
                            return;
                        }
                    }
                }
                int num2 = (int) (Math.random() * maxValue);
                if (queue.isEmpty()) {
                    myQueue.offer(num2);
                    queue.offer(num2);
                } else {
                    if (Math.random() < 0.5) {
                        myQueue.offer(num2);
                        queue.offer(num2);
                    } else {
                        if (!isEqual(myQueue.poll(), queue.poll())) {
                            System.out.println("Failed on MyQueue poll()");
                            return;
                        }
                    }
                }
            }
        }
        System.out.println("Test passed!");
    }

}
