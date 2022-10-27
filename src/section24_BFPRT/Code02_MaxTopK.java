package section24_BFPRT;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 10, 05, 2022
 * @Description: Given an integer array, return its k maximum items.
 * @Note:   Ver1. Sort and then collect.
 *          Ver2. Use heap constructed bottom-up.
 *          Ver3. Use modified partition to firstly find the (N-k)-th minimum, then collect k items.
 */
public class Code02_MaxTopK {

    public static void main(String[] args) {
        validate();
    }

    // O(N * logN)
    public static int[] maxTopK1(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        Arrays.sort(arr);
        int[] res = new int[Math.min(arr.length, k)];
        for (int i = arr.length - 1, j = 0; j < res.length; i--, j++) {
            res[j] = arr[i];
        }
        return res;
    }

    // O(N + K*logN)
    public static int[] maxTopK2(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        k = Math.min(N, k);
        // O(N)
        for (int i = N - 1; i >= 0; i--) {
            heapify(arr, i, N);
        }
        // O(K*longN)
        int size = N;
        int count = 0;
        while (size > 0 && count < k) {
            swap(arr, --size, 0);
            heapify(arr, 0, size);
            count++;
        }
        int[] res = new int[k];
        for (int i = N - 1, j = 0; j < k; i--, j++) {
            res[j] = arr[i];
        }
        return res;
    }

    public static void heapify(int[] arr, int idx, int size) {
        int left = idx * 2 + 1;
        while (left < size) {
            int larger = left + 1 < size && arr[left + 1] > arr[left] ? left + 1 : left;
            if (arr[larger] > arr[idx]) {
                swap(arr, idx, larger);
                idx = larger;
                left = idx * 2 + 1;
            } else {
                break;
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // O(N + K*logK)
    public static int[] maxTopK3(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        k = Math.min(N, k);
        // O(N)
        int nMinusKthMin = Code01_FindMinKth.minKth2(arr, N - k);
        int[] res = new int[k];
        int idx = 0;
        for (int i = 0; i < N; i++) {
            if (arr[i] > nMinusKthMin) {
                res[idx++] = arr[i];
            }
        }
        for (; idx < k; idx++) {
            res[idx] = nMinusKthMin;
        }
        // O(K * logK)
        Arrays.sort(res);
        for (int L = 0, R = k - 1; L < R; L++, R--) {
            swap(res, L, R);
        }
        return res;
    }


    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            // [-? , +?]
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

    public static void validate() {
        int testTime = 50000;
        int maxSize = 100;
        int maxValue = 100;
        boolean pass = true;
        for (int i = 0; i < testTime; i++) {
            int k = (int) (Math.random() * maxSize) + 1;
            int[] arr = generateRandomArray(maxSize, maxValue);
            int[] arr1 = Arrays.copyOf(arr, arr.length);
            int[] arr2 = Arrays.copyOf(arr, arr.length);
            int[] arr3 = Arrays.copyOf(arr, arr.length);
            int[] ans1 = maxTopK1(arr1, k);
            int[] ans2 = maxTopK2(arr2, k);
            int[] ans3 = maxTopK3(arr3, k);
            if (!isEqual(ans1, ans2) || !isEqual(ans1, ans3)) {
                System.out.println("Failed");
                System.out.println(Arrays.toString(ans1));
                System.out.println(Arrays.toString(ans2));
                System.out.println(Arrays.toString(ans3));
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
