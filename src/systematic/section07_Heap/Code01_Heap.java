package systematic.section07_Heap;

import java.util.PriorityQueue;

/**
 * @Author: duccio
 * @Date: 04, 04, 2022
 * @Description: MaxHeap, where in every subtree, the root node is the biggest.
 * @Note:   - Also called priority queue.
 *          - Actually is a complete binary tree implemented using an array.
 *          ======
 *          - Height: logN
 *          - If index starts from 0, then:
 *              a) current node idx: i
 *              b) parent node idx: (i - 1) / 2
 *              c) child node idx: 2i + 1, 2i + 2
 *          - If index starts from 1, then:
 *              a) current node idx: i
 *              b) parent node idx: i / 2
 *              c) child node idx: 2i, 2i + 1
 */
public class Code01_Heap {

    public static class MyHeap {
        int sizeLimit;
        int size;
        int[] arr;

        public MyHeap(int limit) {
            sizeLimit = limit;
            size = 0;
            arr = new int[sizeLimit];
        }

        public void add(int val) {
            if (size == sizeLimit) {
                throw new RuntimeException("Heap was full!");
            }
            arr[size] = val;
            heapInsert(size++);
        }

        public void heapInsert(int idx) {
            int parent = (idx - 1) / 2;
            while (arr[idx] > arr[parent]) {
                swap(idx, parent);
                idx = parent;
                parent = (idx - 1) / 2;
            }
        }

        public int poll() {
            if (size == 0) {
                throw new RuntimeException("Heap was empty!");
            }
            int ret = arr[0];
            swap(0, --size);
            heapify(0);
            return ret;
        }

        private void heapify(int idx) {
            int left = (idx << 1) | 1;
            while (left < size) {
                int bigger = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
                if (arr[bigger] > arr[idx]) {
                    swap(bigger, idx);
                    idx = bigger;
                    left = (idx << 1) | 1;
                } else {
                    break;
                }
            }
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == sizeLimit;
        }

        public void swap(int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
    }

    public static void main(String[] args) {
        int numTest = 10000;
        int limit = 100;
        int maxV = 200;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            int curLimit = (int) (Math.random() * limit) + 1;
            int numOperation = (int) (Math.random() * curLimit) + 1;
            MyHeap heap1 = new MyHeap(curLimit);
            PriorityQueue<Integer> heap2 = new PriorityQueue<>((a, b) -> b - a);
            for (int j = 0; j < numOperation; j++) {
                if (heap1.isEmpty() != heap2.isEmpty()) {
                    System.out.println("Failed on isEmpty()");
                    return;
                }
                if (heap1.isEmpty()) {
                    int curValue = (int) (Math.random() * maxV);
                    heap1.add(curValue);
                    heap2.add(curValue);
                } else {
                    if (Math.random() < 0.5) {
                        int curValue = (int) (Math.random() * maxV);
                        heap1.add(curValue);
                        heap2.add(curValue);
                    } else {
                        if (heap1.poll() != heap2.poll()) {
                            System.out.println("Failed on poll()");
                            return;
                        }
                    }
                }
            }
        }
        System.out.println("Test passed!");
    }


}
