package systematic.section01_SimpleSort;

/**
 * @Author: duccio
 * @Date: 28, 03, 2022
 * @Description: Bubble sort
 * @Note:   1. Iterate through the array, with the ending position decreasing
 *          2. For each iteration, compare every pair and swap if the order is wrong
 *          ======
 *          After each iteration, the biggest is fixed
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
