package systematic.section01_SimpleSort;

import java.util.Arrays;

import static systematic.section01_SimpleSort.Code01_SelectionSort.selectionSort;
import static systematic.section01_SimpleSort.Code02_BubbleSort.bubbleSort;
import static systematic.section01_SimpleSort.Code03_InsertionSort.insertionSort;

/**
 * @Author: duccio
 * @Date: 28, 03, 2022
 * @Description: Validate those three sorting implementations using my own validator.
 * @Note:
 */
public class Code04_SortValidator {
    public static void main(String[] args) {
        validate();
    }

    public static int[] genRandArr(int maxLen, int maxVal) {
        int N = (int) (Math.random() * (maxLen + 1));
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * (maxVal + 1)) - (int) (Math.random() * maxVal);
        }
        return arr;
    }

    public static boolean isSorted(int[] arr) {
        if (arr == null || arr.length < 2) {
            return true;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private static void validate() {
        int numTest = 10000;
        int maxL = 200;
        int maxV = 200;
        for (int i = 0; i < numTest; i++) {
            int[] arr = genRandArr(maxL, maxV);
//            selectionSort(arr);
//            bubbleSort(arr);
            insertionSort(arr);
            if (!isSorted(arr)) {
                System.out.println("Failed on case: " + Arrays.toString(arr));
                return;
            }
        }
        System.out.println("Test passed!");
    }
}
