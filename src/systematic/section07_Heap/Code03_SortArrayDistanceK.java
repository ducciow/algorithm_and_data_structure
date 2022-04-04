package systematic.section07_Heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @Author: duccio
 * @Date: 04, 04, 2022
 * @Description: Sort an integer array, where for each item, the distance between its current index and its sorted
 *      index is at most K, and K < N.
 * @Note:   1. Add the first K+1 items to a heap. Because the correct index is at most K away, the smallest item
 *              must be added.
 *          2. Iteratively, poll from the heap and put it on the right position. Meanwhile, add an item into the heap.
 *          ======
 *          Time O(N*logK)
 */
public class Code03_SortArrayDistanceK {

    public static void main(String[] args) {
        validate();
    }

    public static void sortArrayDistanceLessK(int[] arr, int K) {
        if (arr == null || arr.length < 2 || K == 0) {
            return;
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int i = 0; i < Math.min(K + 1, arr.length); i++) {
            heap.add(arr[i]);
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = heap.poll();
            if (arr.length - K - 1 > i) {
                heap.add(arr[i + K + 1]);
            }
        }
    }

    public static int[] genRandArrMoveK(int maxSize, int maxValue, int K) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        Arrays.sort(arr);
        boolean[] swapped = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int j = Math.min(i + (int) (Math.random() * (K + 1)), arr.length - 1);
            if (!swapped[i] && !swapped[j]) {
                swapped[i] = true;
                swapped[j] = true;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        return arr;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1 == null ^ arr2 == null) {
            return false;
        }
        if (arr1 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    private static void validate() {
        int numTest = 100000;
        int maxSize = 100;
        int maxValue = 100;
        for (int i = 0; i < numTest; i++) {
            int size = (int) (Math.random() * maxSize) + 1;
            int K = (int) (Math.random() * size);
            int[] arr = genRandArrMoveK(size, maxValue, K);
            int[] arr1 = Arrays.copyOf(arr, arr.length);
            int[] arr2 = Arrays.copyOf(arr, arr.length);
            sortArrayDistanceLessK(arr1, K);
            Arrays.sort(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("Failed on case: " + Arrays.toString(arr));
                return;
            }
        }
        System.out.println("Test passed!");
    }
}
