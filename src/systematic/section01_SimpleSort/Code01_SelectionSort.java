package systematic.section01_SimpleSort;

/**
 * @Author: duccio
 * @Date: 28, 03, 2022
 * @Description: Selection Sort
 * @Note:   1. Outer iteration: track the starting position of unsorted part, increasing from 0 to N-1.
 *          2. Inner iteration: scan the unsorted part, select the smallest one and swap it to the starting position.
 *          ======
 *          After each inner iteration, the smallest is fixed.
 */
public class Code01_SelectionSort {

    public static void selectionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        for (int i = 0; i < N - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < N; j++) {
                minIdx = arr[j] < arr[minIdx] ? j : minIdx;
            }
            swap(arr, minIdx, i);
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}
