package primary.class01;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 17, 03, 2022
 * @Description: Selection Sort
 * @Note:   1. Iterate through the array, with the starting position increasing
 *          2. For each iteration, select the smallest value and swap it once to the very front
 *          ======
 *          After each iteration, the smallest is fixed
 */
public class Code02_SelectionSort {
    public static void main(String[] args) {
        int[] arr = {1, 4, 2, 8, 5, 7};
        System.out.println(Arrays.toString(arr));
        selectionSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public static void selectionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        for (int start = 0; start < N; start++) {
            int minIdx = start;
            for (int i = start + 1; i < N; i++) {
                minIdx = arr[i] < arr[minIdx] ? i : minIdx;
            }
            swap(arr, start, minIdx);
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
