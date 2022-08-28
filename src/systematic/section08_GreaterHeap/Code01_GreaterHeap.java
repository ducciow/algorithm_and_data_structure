package systematic.section08_GreaterHeap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @Author: duccio
 * @Date: 05, 04, 2022
 * @Description: Self-implemented heap with extra functionality.
 * @Note:   - Needs a HashMap for tracking the indices of all elements.
 *          - swap() handles the swapping regarding both the Array(List) and HashMap.
 *          - For extra functionality: remove a given element:
 *              1) get the index of the element to be removed.
 *              2) get the replacement element, ie. the last element in Array.
 *              3) check if the replacement ought to be kept.
 *              4) reassign() the replacement, by heapInsert and heapify on its index.
 */
public class Code01_GreaterHeap {

    public static class GreaterHeap<T> {
        int size;
        HashMap<T, Integer> idxMap;
        ArrayList<T> heap;
        Comparator<? super T> comp;

        public GreaterHeap(Comparator<? super T> comparator) {
            size = 0;
            idxMap = new HashMap<>();
            heap = new ArrayList<>();
            comp = comparator;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public boolean contains(T obj) {
            return idxMap.containsKey(obj);
        }

        public T peek() {
            return heap.get(0);
        }

        public void remove(T obj) {
            int idx = idxMap.get(obj);
            T replace = heap.get(size - 1);
            idxMap.remove(obj);
            heap.remove(--size);
            if (obj != replace) {
                idxMap.put(replace, idx);
                heap.set(idx, replace);
                reassign(replace);
            }
        }

        public void reassign(T obj) {
            int idx = idxMap.get(obj);
            heapInsert(idx);
            heapify(idx);
        }

        public ArrayList<T> getAllElements() {
            ArrayList<T> ret = new ArrayList<>();
            ret.addAll(heap);
            return ret;
        }

        public void add(T elem) {
            idxMap.put(elem, size);
            heap.add(elem);
            heapInsert(size++);
        }

        public T poll() {
            T ret = heap.get(0);
            swap(0, size - 1);
            idxMap.remove(ret);
            heap.remove(--size);
            heapify(0);
            return ret;
        }


        private void heapInsert(int idx) {
            while (comp.compare(heap.get(idx), heap.get((idx - 1) / 2)) < 0) {
                swap(idx, (idx - 1) / 2);
                idx = (idx - 1) / 2;
            }
        }

        private void heapify(int index) {
            int left = 2 * index + 1;
            while (left < size) {
                int bigger = left + 1 < size && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? left + 1 : left;
                if (comp.compare(heap.get(bigger), heap.get(index)) < 0) {
                    swap(index, bigger);
                    index = bigger;
                    left = 2 * index + 1;
                } else {
                    break;
                }
            }
        }

        private void swap(int i, int j) {
            T o1 = heap.get(i);
            T o2 = heap.get(j);
            heap.set(i, o2);
            heap.set(j, o1);
            idxMap.put(o2, i);
            idxMap.put(o1, j);
        }
    }
}
