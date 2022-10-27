package section39_SubArrayLength;

/**
 * @Author: duccio
 * @Date: 31, 05, 2022
 * @Description: Given a positive integer array, and an integer k, find the longest subarray whose sum is equal to k,
 *      and return its length.
 * @Note:   1. Positive array means there is monotonicity.
 *          2. Use a sliding window.
 */
public class Code01_EqualSumInPositiveArray {

    public static int getMaxLen(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) {
            return 0;
        }
        int L = 0;
        int R = 0;
        int sum = arr[0];
        int len = 0;
        while (R < arr.length) {
            if (sum == k) {
                len = Math.max(len, R - L + 1);
                sum -= arr[L++];
            } else if (sum < k) {
                R++;
                if (R == arr.length) {
                    break;
                }
                sum += arr[R];
            } else {
                sum -= arr[L++];
            }
        }
        return len;
    }


    public static int naive(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) {
            return 0;
        }
        int len = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (isValid(arr, i, j, k)) {
                    len = Math.max(len, j - i + 1);
                }
            }
        }
        return len;
    }

    private static boolean isValid(int[] arr, int L, int R, int k) {
        int sum = 0;
        for (int i = L; i <= R; i++) {
            sum += arr[i];
        }
        return sum == k;
    }

    public static int[] generatePositiveArray(int size, int value) {
        int[] ans = new int[size];
        for (int i = 0; i != size; i++) {
            ans[i] = (int) (Math.random() * value) + 1;
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTime = 50000;
        int len = 50;
        int value = 100;
        System.out.println("Test begin ...");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generatePositiveArray(len, value);
            int K = (int) (Math.random() * value) + 1;
            int ans1 = getMaxLen(arr, K);
            int ans2 = naive(arr, K);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed");
    }

}
