package section10_BucketSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: duccio
 * @Date: 06, 04, 2022
 * @Description: Radix Sort.
 * @Note:   - Iteratively and partially sort all elements only based on one single digit from right to left.
 *          - Ver1. a) Prepare 10 queues.
 *                  b) Get the biggest number of digits in the given array.
 *                  c) Iterate through each digit from right to left:
 *                      1) for each element, get its current digit value and put it into the corresponding queue.
 *                      2) Poll from all queues, resulting in partially sorted array.
 *          - Ver2. a) Get the biggest number of digits in the given array.
 *                  b) Iterate through each digit from right to left:
 *                      1) prepare a count array of size 10.
 *                      2) fill in count array for occurrences of current digit of each element.
 *                      3) compute the pre-sum array based on count array, indicating the rightmost position for any
 *                         element having current digit value.
 *                      4) reversely iterate each element in arr, put it in the correct position according to pre-sum
 *                         array. Use a temp array for storing and copying back to arr.
 */
public class Code02_RadixSort {

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
            // sort only based on current digit, by reverse iterating
            int[] tmp = new int[arr.length];
            for (int i = arr.length - 1; i >= 0; i--) {
                int digit = getDigit(arr[i], d);
                tmp[count[digit] - 1] = arr[i];
                count[digit]--;
            }
            // copy tmp back to arr
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

    // get the d-th rightmost digit of num
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

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 200;
        System.out.println("Test begin...");
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
