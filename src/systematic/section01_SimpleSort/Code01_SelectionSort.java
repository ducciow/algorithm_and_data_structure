package systematic.section01_SimpleSort;

/**
 * @Author: duccio
 * @Date: 28, 03, 2022
 * @Description: Selection sort
 * @Note:   1. Iterate through the array, with the starting position increasing
 *          2. For each iteration, select the smallest value and swap it once to the very front
 *          ======
 *          After each iteration, the smallest is fixed
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
