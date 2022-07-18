package systematic.section06_QuickSort;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 01, 04, 2022
 * @Description: Quick sort 1: ver1, partition for <=, > pivot, with the pivot chosen to be the rightmost item.
 *                             ver2, partition for <, ==, > pivot, with the pivot chosen to be the rightmost item.
 *                             ver3, randomly choose an item from the current group as the pivot, swap it to the
 *                                   current end, then call ver2 or ver1.
 *               Quick sort 2: iteration version.
 * @Note:   - Unlike merge sort:
 *              a) The process of broader group does not require the returning from narrower groups.
     *          b) The base case is L >= R instead of L == R.
 *          - Partition ver2 needs an extra swap at the end if the more-area starts from R (see partition2()). And if
 *            the more-area starts from R+1, it is no need for an extra swap but has a little different return (see
 *            partition2_another_version()).
 *          - Key idea of partition ver2:
 *              a) idx_less starts form L-1.
 *              b) idx_more starts from R or R+1.
 *              c) while the processing idx < idx_more:
 *                      if arr[idx] < pivot:
 *                          idx_less++, swap, idx++
 *                      else if arr[idx] == pivot:
 *                          idx++
 *                      else:
 *                          idx_more--, swap, idx not change
 *          - For iteration version, a stack or queue is used, amd make sure it will end by checking base case.
 *          ======
 *          - Time: O(N*logN), worst O(N**2).
 *          - Extra space: O(logN), worst O(N).
 */
public class Code01_QuickSort {

    public static void quickSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
//        process1(arr, 0, arr.length - 1);
        process2(arr, 0, arr.length - 1);
//        process3(arr, 0, arr.length - 1);
    }

    private static void process1(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        int done = partition1(arr, L, R);
        process1(arr, L, done - 1);
        process1(arr, done + 1, R);
    }

    private static int partition1(int[] arr, int L, int R) {
        int lessEqual = L - 1;
        for (int i = L; i <= R; i++) {
            if (arr[i] <= arr[R]) {
                swap(arr, ++lessEqual, i);
            }
        }
        return lessEqual;
    }

    private static void process2(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        int[] done = partition2(arr, L, R);
        process1(arr, L, done[0] - 1);
        process1(arr, done[1] + 1, R);
    }

    private static int[] partition2(int[] arr, int L, int R) {
        int less = L - 1;
        int bigger = R;
        int i = L;
        while (i < bigger) {
            if (arr[i] < arr[R]) {
                swap(arr, ++less, i++);
            } else if (arr[i] == arr[R]) {
                i++;
            } else {
                swap(arr, --bigger, i);
            }
        }
        swap(arr, bigger, R);
        return new int[]{less + 1, bigger};
    }

    public static int[] partition2_another_version(int[] arr, int L, int R) {
        int pivot = arr[R];
        int less = L - 1;
        int more = R + 1;
        int i = L;
        while (i < more) {
            if (arr[i] < pivot) {
                swap(arr, ++less, i++);
            } else if (arr[i] == pivot) {
                i++;
            } else {
                swap(arr, --more, i);
            }
        }
        return new int[]{less + 1, more - 1};
    }


    private static void process3(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        swap(arr, L + (int) (Math.random() * (R - L + 1)), R);
        int[] done = partition2(arr, L, R);
        process1(arr, L, done[0] - 1);
        process1(arr, done[1] + 1, R);
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    // for iteration version
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
        Stack<Task> tasks = new Stack<>();
        tasks.push(new Task(0, arr.length - 1));
        while (!tasks.isEmpty()) {
            Task task = tasks.pop();
            if (task.L < task.R) {
                swap(arr, task.L + (int) (Math.random() * (task.R - task.L + 1)), task.R);
                int[] done = partition2(arr, task.L, task.R);
                tasks.push(new Task(task.L, done[0] - 1));
                tasks.push(new Task(done[1] + 1, task.R));
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

    public static void main(String[] args) {
        int testTime = 50000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("Test begin...");
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
