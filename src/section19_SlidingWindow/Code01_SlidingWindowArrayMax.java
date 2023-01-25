package section19_SlidingWindow;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @Author: duccio
 * @Date: 03, 05, 2022
 * @Description: Given an integer array, and a sliding window of fixed size, return the maximum element within the
 *      window as the window sliding forwards.
 * @Note:   1. Use a Deque for storing indices, making sure their corresponding elements are in descending order
 *             from head to tail, and the head index refers to the current valid maximum element.
 *          2. When it slides to a new element, discard all indices in Deque from tail whose corresponding items are
 *             not bigger than the new element, and add the new index to tail.
 *          3. As the window slides, drop index in Deque from the head if it is invalid (out of window boundary).
 *          ======
 *          Time complexity O(N).
 */
public class Code01_SlidingWindowArrayMax {

    public static int[] getWindowMax(int[] arr, int width) {
        if (arr == null || width < 1 || arr.length < width) {
            return null;
        }
        int[] ans = new int[arr.length - width + 1];
        // declare a deque for tracking valid max
        LinkedList<Integer> dq = new LinkedList<>();
        // as the sliding window moves forward
        for (int R = 0; R < arr.length; R++) {
            // discard indices from the tail that refer to smaller-than-or-equal-to elements
            while (!dq.isEmpty() && arr[dq.peekLast()] <= arr[R]) {
                dq.pollLast();
            }
            // add new index
            dq.addLast(R);
            // get the leftmost index of the window
            int L = R - width + 1;
            // drop invalid element from deque head
            if (L - 1 == dq.peekFirst()) {
                dq.pollFirst();
            }
            // put the current valid max into answer array
            if (L >= 0) {
                ans[L] = arr[dq.peekFirst()];
            }
        }
        return ans;
    }


    public static int[] naiveGetMaxWindow(int[] arr, int width) {
        if (arr == null || width < 1 || arr.length < width) {
            return null;
        }
        int[] ret = new int[arr.length - width + 1];
        int idx = 0;
        for (int L = 0; L < arr.length - width + 1; L++) {
            int R = L + width - 1;
            int max = arr[L];
            for (int i = L + 1; i <= R; i++) {
                max = Math.max(max, arr[i]);
            }
            ret[idx++] = max;
        }
        return ret;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1 == null ^ arr2 == null) {
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
        int numTest = 10000;
        int maxL = 20;
        int maxV = 100;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            int[] arr = generateRandomArray(maxL, maxV);
            int width = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getWindowMax(arr, width);
            int[] ans2 = naiveGetMaxWindow(arr, width);
            if (!isEqual(ans1, ans2)) {
                System.out.println("Failed on case: " + Arrays.toString(arr));
                System.out.println(width);
                System.out.println(Arrays.toString(ans1));
                System.out.println(Arrays.toString(ans2));
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
