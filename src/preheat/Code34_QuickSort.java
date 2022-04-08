package preheat;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 25, 03, 2022
 * @Description: Quick sort 1: ver1, partition for <=, > pivot, which is chosen to be the rightmost item
 *                             ver2, partition for <, ==, > pivot, which is chosen to be the rightmost item
 *               Quick sort 2: iteration version
 * @Note:   1. Unlike merge sort, the process of bigger group does not require the returning from smaller group.
 *          2. Unlike merge sort, the base case is L >= R.
 *          3. Partition ver2 needs an extra swap at the end, unlike ver1.
 *          4. For iteration version, a stack or queue is used, amd make sure it will end by checking base case.
 *          ======
 *          1. Time: O(N*logN), worst O(N**2).
 *          2. Extra space: O(logN), worst O(N).
 */
public class Code34_QuickSort {
    public static void main(String[] args) {
        validate();
    }

    public static void quickSort1(int[] arr) {
        process1(arr, 0, arr.length - 1);
//        process2(arr, 0, arr.length - 1);
    }

    public static void process1(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        int done = partition1(arr, L, R);
        process1(arr, L, done - 1);
        process1(arr, done + 1, R);
    }

    public static int partition1(int[] arr, int L, int R) {
        int lessEqual = L - 1;
        for (int i = L; i <= R; i++) {
            if (arr[i] <= arr[R]) {
                swap(arr, ++lessEqual, i);
            }
        }
        return lessEqual;
    }

    public static void process2(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        int[] equal = partition2(arr, L, R);
        process1(arr, L, equal[0] - 1);
        process1(arr, equal[1] + 1, R);
    }

    public static int[] partition2(int[] arr, int L, int R) {
        int less = L - 1;
        int big = R;
        int idx = L;
        while (idx < big) {
            if (arr[idx] < arr[R]) {
                swap(arr, ++less, idx++);
            } else if (arr[idx] == arr[R]) {
                idx++;
            } else {
                swap(arr, --big, idx);
            }
        }
        swap(arr, big, R);
        return new int[]{less + 1, big};
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // for quick sort iteration version
    public static class Task {
        int L;
        int R;

        public Task(int l, int r) {
            L = l;
            R = r;
        }
    }

    public static void quickSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        Stack<Task> stack = new Stack<>();
        stack.push(new Task(0, arr.length - 1));
        while (!stack.isEmpty()) {
            Task curTask = stack.pop();
            int[] equal = partition2(arr, curTask.L, curTask.R);
            if (equal[0] > curTask.L) {
                stack.push(new Task(curTask.L, equal[0] - 1));
            }
            if (equal[1] < curTask.R) {
                stack.push(new Task(equal[1] + 1, curTask.R));
            }
        }
    }


    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
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

    public static void validate() {
        int testTime = 50000;
        int maxSize = 100;
        int maxValue = 100;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = Arrays.copyOf(arr1, arr1.length);
            quickSort1(arr1);
            Arrays.sort(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("Failed on case: ");
                System.out.println(Arrays.toString(arr1));
                System.out.println(Arrays.toString(arr2));
                return;
            }
        }
        System.out.println("Test passed!");
    }
}
