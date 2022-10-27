package section10_BucketSort;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 06, 04, 2022
 * @Description: Count Sort.
 * @Note:   - Create a count array of size (max - min + 1):
 *              a) index refers to element in the original array, eg., 0 for min.
 *              b) value indicates the number of occurrences of an element in the original array.
 *          - Iteratively re-fill in the original array according to indices and values of the count array.
 *          ======
 *          - Require: elements of the given array must be integers and lie within a range.
 */
public class Code01_CountSort {

    public static void countSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int n : arr) {
            max = Math.max(max, n);
            min = Math.min(min, n);
        }
        int[] count = new int[max - min + 1];
        for (int n : arr) {
            count[n - min]++;
        }
        int i = 0;
        for (int j = 0; j < count.length; j++) {
            while (count[j]-- > 0) {
                arr[i++] = j + min;
            }
        }
    }

    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int[] arr = new int[(int) ((maxLen + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
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
        int testTime = 50000;
        int maxSize = 100;
        int maxValue = 150;
        System.out.println("Test begin...");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int[] arr1 = Arrays.copyOf(arr, arr.length);
            int[] arr2 = Arrays.copyOf(arr, arr.length);
            countSort(arr1);
            Arrays.sort(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("Failed on case: " + Arrays.toString(arr));
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
