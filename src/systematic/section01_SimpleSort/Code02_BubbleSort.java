package systematic.section01_SimpleSort;

/**
 * @Author: duccio
 * @Date: 28, 03, 2022
 * @Description: Bubble sort
 * @Note:   1. Outer iteration: track the ending position of unsorted part, decreasing from N-1 to 1.
 *          2. Inner iteration: compare every pair in the unsorted part, swap if their ordering is wrong.
 *          ======
 *          After each inner iteration, the biggest is fixed.
 */
public class Code02_BubbleSort {

    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        for (int i = N - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

}
