package section24_BFPRT;

import java.util.PriorityQueue;

/**
 * @Author: duccio
 * @Date: 10, 05, 2022
 * @Description: Given an integer array, find the k-th smallest value in it.
 * @Note:   Ver1. Use a maxHeap for storing k elements.
 *          Ver2. Modify quick sort by only enter left/right part after partition.
 *          Ver3. BFPRT that selects the optimal pivot for partition. To do that, firstly divide array in groups of size
 *                5, then sort each group, pick medians from each group to form an mArr, and pick the median of mArr
 *                as the pivot.
 */
public class Code01_FindMinKth {

    public static void main(String[] args) {
        validate();
    }

    // O(N*logK)
    public static int minKth1(int[] arr, int k) {
        if (arr == null || arr.length < k) {
            return -1;
        }
        PriorityQueue<Integer> heap = new PriorityQueue<>((o1, o2) -> (o2 - o1));
        for (int i = 0; i < k; i++) {
            heap.add(arr[i]);
        }
        for (int i = k; i < arr.length; i++) {
            if (arr[i] < heap.peek()) {
                heap.poll();
                heap.add(arr[i]);
            }
        }
        return heap.peek();
    }

    // O(N)
    public static int minKth2(int[] arr, int k) {
        if (arr == null || arr.length < k) {
            return -1;
        }
        int L = 0;
        int R = arr.length - 1;
        while (L < R) {
            int pivot = arr[L + (int) (Math.random() * (R - L + 1))];
            int[] range = partition(arr, L, R, pivot);
            if (k - 1 >= range[0] && k - 1 <= range[1]) {
                return arr[k - 1];
            } else if (k - 1 < range[0]) {
                R = range[0] - 1;
            } else {
                L = range[1] + 1;
            }
        }
        return arr[L];
    }

    public static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        while (cur < more) {
            if (arr[cur] < pivot) {
                swap(arr, ++less, cur++);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[]{less + 1, more - 1};
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // O(N)
    public static int minKth3(int[] arr, int k) {
        if (arr == null || arr.length < k) {
            return -1;
        }
        return bfprt(arr, 0, arr.length - 1, k - 1);
    }

    private static int bfprt(int[] arr, int L, int R, int k) {
        if (L == R) {
            return arr[L];
        }
        int pivot = medianOfMedians(arr, L, R);
        int[] range = partition(arr, L, R, pivot);
        if (k >= range[0] && k <= range[1]) {
            return arr[k];
        } else if (k < range[0]) {
            return bfprt(arr, L, range[0] - 1, k);
        } else {
            return bfprt(arr, range[1] + 1, R, k);
        }
    }

    public static int medianOfMedians(int[] arr, int L, int R) {
        int size = R - L + 1;
        int offset = size % 5 == 0 ? 0 : 1;
        int[] mArr = new int[size / 5 + offset];
        for (int i = 0; i < mArr.length; i++) {
            int groupL = L + i * 5;
            int groupR = Math.min(groupL + 4, R);
            mArr[i] = getMedian(arr, groupL, groupR);
        }
        return bfprt(mArr, 0, mArr.length - 1, mArr.length / 2);
    }

    public static int getMedian(int[] arr, int L, int R) {
        insertionSort(arr, L, R);
        return arr[(L + R) / 2];
    }


    public static void insertionSort(int[] arr, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }


    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void validate() {
        int testTime = 10000;
        int maxSize = 100;
        int maxValue = 100;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = minKth1(arr, k);
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
