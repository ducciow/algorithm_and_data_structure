package systematic.section07_Heap;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 04, 04, 2022
 * @Description: Heap sort.
 * @Note:   - Ver1. heapInsert, then heapify, O(N*logN) + O(N*logN).
 *          - Ver2. heapify, then heapify again, O(N) + O(N*logN).
 *          ======
 *          - For heapInsert:
 *              a) iterate with increasing index i.
 *          - For heapify:
 *              a) iterate with decreasing index j.
 *                  1) swap arr[0] and arr[j].
 *                  2) call heapify with an extra argument j, indicating the current range under consideration.
 *              b) does not need swap for the first heapify of Ver2.
 *          ======
 *          - The first round of heapInsert or heapify guarantees the condition of a max heap is satisfied, and
 *              arr[0] ends with the biggest element.
 *          - The first heapify of Ver2 requires all items are given at hand. Its O(N) results from the fact that
 *              half of the nodes are leaf nodes, demanding just O(1) operations downwards in heapify.
 *          - Extra space: O(1).
 */
public class Code02_HeapSort {

    public static void heapSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr, i);
        }
        for (int j = arr.length - 1; j > 0; j--) {
            swap(arr, 0, j);
            heapify(arr, 0, j);
        }
    }

    public static void heapSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            heapify(arr, i, arr.length);
        }
        for (int j = arr.length - 1; j > 0; j--) {
            swap(arr, 0, j);
            heapify(arr, 0, j);
        }
    }

    private static void heapInsert(int[] arr, int i) {
        while (arr[i] > arr[(i - 1) / 2]) {
            swap(arr, i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    private static void heapify(int[] arr, int idx, int size) {
        int left = 2 * idx + 1;
        while (left < size) {
            int bigger = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
            if (arr[bigger] > arr[idx]) {
                swap(arr, bigger, idx);
                idx = bigger;
                left = 2 * idx + 1;
            } else {
                break;
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
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

    public static void main(String[] args) {
        int numTest = 50000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int[] arr1 = Arrays.copyOf(arr, arr.length);
            int[] arr2 = Arrays.copyOf(arr, arr.length);
            heapSort2(arr1);
            Arrays.sort(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("Failed on case: " + Arrays.toString(arr));
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
