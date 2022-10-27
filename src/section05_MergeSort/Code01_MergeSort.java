package section05_MergeSort;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 31, 03, 2022
 * @Description: Merge Sort: Recursion version (top-down)
 *                           Iteration version (bottom-up)
 * @Note:   For recursion version:
 *              - All the operations are done in the original array, so the recursive call needs arguments for operating
 *                range, which means there should be a helper process(arr, L, R) function. Then it needs the merge().
 *              - In each process:
 *                  1) Base case is when L==R.
 *                  2) Process [L, mid] and [mid+1, R].
 *                  3) Merge [L, R].
 *              - In each merge:
 *                  1) Use an extra space of R-L+1 for temporal storing.
 *                  2) Use three indices performing the classic merge.
 *                  3) Assign arr[L, R].
 *          For iteration version:
 *              - Be careful about possible index overflow.
 *              - Outer while loop is for left/right group size. Break at the end if (size > N / 2), meaning the next
 *                doubled size is beyond.
 *              - Inner while loop is for L, M and R. Break at the beginning if (size >= N - L), meaning the remaining
 *                elements are not enough to start the right group.
 *              - Compute R by M + min{N-1-M, size}.
 */
public class Code01_MergeSort {

    public static void mergeSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int L, int R) {
        if (L == R) {
            return;
        }
        int mid = L + ((R - L) >> 1);
        process(arr, L, mid);
        process(arr, mid + 1, R);
        merge(arr, L, mid, R);
    }

    private static void merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int i = 0;
        int idx1 = L;
        int idx2 = M + 1;
        while (idx1 <= M && idx2 <= R) {
            help[i++] = arr[idx1] <= arr[idx2] ? arr[idx1++] : arr[idx2++];
        }
        while (idx1 <= M) {
            help[i++] = arr[idx1++];
        }
        while (idx2 <= R) {
            help[i++] = arr[idx2++];
        }
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
    }

    public static void mergeSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int N = arr.length;
        int step = 1;
        while (step < N) {
            int L = 0;
            while (L < N) {
                if (step >= N - L) {
                    break;
                }
                int M = L + step - 1;
                int R = M + Math.min(N - 1 - M, step);
                merge(arr, L, M, R);
                L = R + 1;
            }

            if (step > N / 2) {
                break;
            }
            step <<= 1;
        }
    }

    public static int[] generateArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return arr;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
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
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("Test begin...");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateArray(maxSize, maxValue);
            int[] arr2 = Arrays.copyOf(arr1, arr1.length);
            mergeSort1(arr1);
            mergeSort2(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed");
    }

}
