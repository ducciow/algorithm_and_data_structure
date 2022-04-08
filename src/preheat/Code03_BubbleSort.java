package preheat;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 17, 03, 2022
 * @Description: Bubble Sort
 * @Note:   1. Iterate through the array, with the ending position decreasing
 *          2. For each iteration, compare every pair and swap if the order is wrong
 *          ======
 *          After each iteration, the biggest is fixed
 */
public class Code03_BubbleSort {
    public static void main(String[] args) {
        int[] arr = {1, 4, 2, 8, 5, 7};
        System.out.println(Arrays.toString(arr));
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        for (int end = N - 1; end > 0; end--) {
            for (int i = 1; i <= end; i++) {
                if (arr[i - 1] > arr[i]) {
                    swap(arr, i - 1, i);
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
