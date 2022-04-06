package systematic.section10_OtherSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: duccio
 * @Date: 06, 04, 2022
 * @Description: Radix Sort -- another type of bucket sort
 * @Note:   1. Within each single iteration, partially sort all elements only based on one single digit.
 *          2. To sort based on one single digit:
 *              Ver1. a) Prepare 10 queues, add all elements to the queue corresponding to the digit value (0 - 9).
 *                    b) Poll from all queues, resulting in partially sorted array.
 *              Ver2. a) Compute the count array for the number of occurrences of each digit value (0 - 9).
 *                    b) Compute the pre-sum array. Indices of it means the digit, and values means how many elements
 *                      are less than or equal to it on this digit.
 *                    c) Reversely for each element in arr, put it in the correct position according to aforementioned
 *                      indices and values.
 */
public class Code02_RadixSort {

    public static void main(String[] args) {
        validate();
    }

    public static void radixSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // prepare 10 queues
        ArrayList<Queue<Integer>> queues = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            queues.add(new LinkedList<>());
        }
        // get the number of digits of the maximum number in arr
        int numDigits = getNumDigits(arr);
        // iterate each digit from lowest to highest
        for (int d = 0; d < numDigits; d++) {
            // add each element to its corresponding queue
            for (int i = 0; i < arr.length; i++) {
                int digit = getDigit(arr[i], d);
                queues.get(digit).offer(arr[i]);
            }
            // poll from each queue
            int idx = 0;
            for (int j = 0; j < 10; j++) {
                while (!queues.get(j).isEmpty()) {
                    arr[idx++] = queues.get(j).poll();
                }
            }
        }
    }

    public static void radixSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // get the number of digits of the maximum number in arr
        int numDigits = getNumDigits(arr);
        // iterate each digit from lowest to highest
        for (int d = 0; d < numDigits; d++) {
            int[] count = new int[10];
            // fill in the count array
            for (int i = 0; i < arr.length; i++) {
                int digit = getDigit(arr[i], d);
                count[digit]++;
            }
            // compute pre-sum
            for (int i = 1; i < 10; i++) {
                count[i] = count[i - 1] + count[i];
            }
            // sort only based on current digit
            int[] tmp = new int[arr.length];
            for (int i = arr.length - 1; i >= 0; i--) {
                int digit = getDigit(arr[i], d);
                tmp[count[digit] - 1] = arr[i];
                count[digit]--;
            }
            // copy tmp to arr
            for (int i = 0; i < arr.length; i++) {
                arr[i] = tmp[i];
            }
        }
    }

    private static int getNumDigits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int n : arr) {
            max = Math.max(max, n);
        }
        int ans = 0;
        while (max > 0) {
            ans++;
            max /= 10;
        }
        return ans;
    }

    private static int getDigit(int num, int d) {
        while (d > 0) {
            num = num / 10;
            d--;
        }
        return num % 10;
    }


    public static int[] generateRandomArray(int maxLen, int maxValue) {
        int[] arr = new int[(int) ((maxLen + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
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

    public static void validate() {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 200;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int[] arr1 = Arrays.copyOf(arr, arr.length);
            int[] arr2 = Arrays.copyOf(arr, arr.length);
            radixSort2(arr1);
            Arrays.sort(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("Failed on case: " + Arrays.toString(arr));
                return;
            }
        }
        System.out.println("Test passed!");
    }
}
