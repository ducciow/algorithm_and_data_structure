package systematic.section01_SimpleSort;

/**
 * @Author: duccio
 * @Date: 28, 03, 2022
 * @Description:
 * @Note:   1. Iterate through the array, with ending position increasing
 *          2. For each iteration, insert the value of the end index into the right position
 *          ======
 *          1. It is actually swapping until meet the right position, rather than directly inserting into the position
 *          2. After each iteration, the former segment is fixed
 *          3. Time complexity varies based on how the original list is sorted
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
