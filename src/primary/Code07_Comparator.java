package primary;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 18, 03, 2022
 * @Description: Comparator for testing
 * @Note:
 */
public class Code07_Comparator {
    public static void main(String[] args) {
        tester();
    }

    public static int[] generateArr(int maxLen, int maxVal) {
        int len = (int) (Math.random() * maxLen);
        int[] res = new int[len];
        for (int i = 0; i < len; i++) {
            res[i] = (int) (Math.random() * maxVal);
        }
        return res;
    }

    public static boolean isSorted(int[] arr) {
        int N = arr.length;
        if (N < 2) {
            return true;
        }
        for (int i = 0; i < N - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static void tester() {
        int numTest = 10000;
        int maxLen = 50;
        int maxVal = 1000;
        for (int i = 0; i < numTest; i++) {
            int[] arr = generateArr(maxLen, maxVal);
            //Code02_SelectionSort.selectionSort(arr);
            //Code03_BubbleSort.bubbleSort(arr);
            Code04_InsertionSort.insertionSort(arr);
            if (!isSorted(arr)) {
                System.out.println("Fail on case: " + Arrays.toString(arr));
                break;
            }
        }
        System.out.println("Test succeed!");
    }
}
