package section01_SimpleSort;

/**
 * @Author: duccio
 * @Date: 28, 03, 2022
 * @Description: Insertion Sort
 * @Note:   1. Outer iteration: track the to-do position about to be sorted, increasing from 1 to N-1.
 *          2. Inner iteration: rolling check the to-do element with the value before it, swap if their ordering is
 *                              wrong, otherwise break.
 *          ======
 *          - After each inner iteration, the former segment is fixed.
 *          - It is actually swapping until meet the right position, rather than directly inserting into the position.
 *          - Time complexity varies based on how the original list is sorted.
 */
public class Code03_InsertionSort {

    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        for (int i = 1; i < N; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}
