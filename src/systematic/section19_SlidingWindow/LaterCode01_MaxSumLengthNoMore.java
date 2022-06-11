package systematic.section19_SlidingWindow;

import java.util.LinkedList;

/**
 * @Author: duccio
 * @Date: 11, 06, 2022
 * @Description: Given an integer array and a positive integer m, return the maximum sum of subarray of length no more
 *      than m.
 * @Note:
 */
public class LaterCode01_MaxSumLengthNoMore {

    public static int maxSum(int[] arr, int m) {
        if (arr == null || arr.length == 0 || m < 1) {
            return 0;
        }
        int N = arr.length;
        int[] preSum = new int[N];
        preSum[0] = arr[0];
        for (int i = 1; i < N; i++) {
            preSum[i] = preSum[i - 1] + arr[i];
        }
        LinkedList<Integer> q = new LinkedList<>();
        int R = 0;
        int end = Math.min(N, m);
        for (; R < end; R++) {
            while (!q.isEmpty() && preSum[q.peekLast()] <= preSum[R]) {
                q.pollLast();
            }
            q.add(R);
        }
        int ans = preSum[q.peekFirst()];
        int L = 0;
        for (; R < N; L++, R++) {
            if (q.peekFirst() == L) {
                q.pollFirst();
            }
            while (!q.isEmpty() && preSum[q.peekLast()] <= preSum[R]) {
                q.pollLast();
            }
            q.add(R);
            ans = Math.max(ans, preSum[q.peekFirst()] - preSum[L]);
        }
        for (; L < N - 1; L++) {
            if (q.peekFirst() == L) {
                q.pollFirst();
            }
            ans = Math.max(ans, preSum[q.peekFirst()] - preSum[L]);
        }
        return ans;
    }


    public static int naive(int[] arr, int m) {
        if (arr == null || arr.length == 0 || m < 1) {
            return 0;
        }
        int N = arr.length;
        int ans = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            int sum = 0;
            for (int j = i; j < N; j++) {
                if (j - i + 1 > m) {
                    break;
                }
                sum += arr[j];
                ans = Math.max(ans, sum);
            }
        }
        return ans;
    }

    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 1000000;
        int maxN = 50;
        int maxValue = 100;
        System.out.println("Test begin");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxN);
            int M = (int) (Math.random() * maxN);
            int[] arr = randomArray(N, maxValue);
            int ans1 = maxSum(arr, M);
            int ans2 = naive(arr, M);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed");
    }

}
