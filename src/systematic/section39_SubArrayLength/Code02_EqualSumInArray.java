package systematic.section39_SubArrayLength;

import java.util.HashMap;

/**
 * @Author: duccio
 * @Date: 31, 05, 2022
 * @Description: Similar to previous problem, but the elements of array can be non-positive.
 * @Note:   1. Monotonicity does not hold.
 *          2. Use an auxiliary structure to quickly get what is needed.
 *          3. Always think about treat the current position as the end position of any subarray, and the pre-sums.
 */
public class Code02_EqualSumInArray {

    public static int getMaxLen(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);  // <--- important
        int len = 0;
        int preSum = 0;
        for (int i = 0; i < arr.length; i++) {
            preSum += arr[i];
            if (map.containsKey(preSum - k)) {
                len = Math.max(len, i - map.get(preSum - k));
            }
            if (!map.containsKey(preSum)) {
                map.put(preSum, i);
            }
        }
        return len;
    }


    public static int naive(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
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

    public static int[] generateRandomArray(int size, int value) {
        int[] ans = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (int) (Math.random() * value) - (int) (Math.random() * value);
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int len = 50;
        int value = 100;
        System.out.println("Test begin ...");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(len, value);
            int K = (int) (Math.random() * value) - (int) (Math.random() * value);
            int ans1 = getMaxLen(arr, K);
            int ans2 = naive(arr, K);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed!");

    }
}
