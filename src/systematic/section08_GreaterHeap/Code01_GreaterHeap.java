package systematic.section08_GreaterHeap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @Author: duccio
 * @Date: 05, 04, 2022
 * @Description: Self-implemented heap with extra functionality
 * @Note:   For a basic data type like Integer, wrap it beforehand
 */
public class Code01_GreaterHeap {

    public static class GreaterHeap<T> {
        int size;
        HashMap<T, Integer> idxMap;
        ArrayList<T> list;
        Comparator<? super T> comp;

        public GreaterHeap(Comparator<? super T> comparator) {
            size = 0;
            idxMap = new HashMap<>();
            list = new ArrayList<>();
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
            return list.get(0);
        }

        public void remove(T obj) {
            int idx = idxMap.get(obj);
            T replace = list.get(size - 1);
            idxMap.remove(obj);
            list.remove(--size);
            if (obj != replace) {
                idxMap.put(replace, idx);
                list.set(idx, replace);
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
            ret.addAll(list);
            return ret;
        }

        public void add(T elem) {
            idxMap.put(elem, size);
            list.add(elem);
            heapInsert(size++);
        }

        public T poll() {
            T ret = list.get(0);
            swap(0, size - 1);
            idxMap.remove(ret);
            list.remove(--size);
            heapify(0);
            return ret;
        }


        private void heapInsert(int idx) {
            while (comp.compare(list.get(idx), list.get((idx - 1) / 2)) < 0) {
                swap(idx, (idx - 1) / 2);
                idx = (idx - 1) / 2;
            }
        }

        private void heapify(int index) {
            int left = 2 * index + 1;
            while (left < size) {
                int bigger = left + 1 < size && comp.compare(list.get(left + 1), list.get(left)) < 0 ? left + 1 : left;
                if (comp.compare(list.get(bigger), list.get(index)) < 0) {
                    swap(index, bigger);
                    index = bigger;
                    left = 2 * index + 1;
                } else {
                    break;
                }
            }
        }

        private void swap(int i, int j) {
            T o1 = list.get(i);
            T o2 = list.get(j);
            list.set(i, o2);
            list.set(j, o1);
            idxMap.put(o2, i);
            idxMap.put(o1, j);
        }
    }
}
