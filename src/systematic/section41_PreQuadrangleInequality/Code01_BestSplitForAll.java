package systematic.section41_PreQuadrangleInequality;

/**
 * @Author: duccio
 * @Date: 01, 06, 2022
 * @Description: Given a non-negative integer array of size N, there is N-1 ways to split it. For evey splitting, there
 *      are min{left_sum, right_sum}. Return the maximum of these minimums, requiring time complexity O(N).
 * @Note:   1. Get the sum of all elements.
 *          2. For every position, accumulate the left_sum, and get right_sum by deducting from all_sum.
 */
public class Code01_BestSplitForAll {

    public static int bestSplit(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sumAll = 0;
        for (int n : arr) {
            sumAll += n;
        }
        int N = arr.length;
        int ans = 0;
        int sumL = 0;
        for (int i = 0; i < N; i++) {
            sumL += arr[i];
            int sumR = sumAll - sumL;
            ans = Math.max(ans, Math.min(sumL, sumR));
        }
        return ans;
    }


    public static int naive(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int ans = 0;
        for (int split = 0; split < N; split++) {
            int sumL = 0;
            for (int L = 0; L <= split; L++) {
                sumL += arr[L];
            }
            int sumR = 0;
            for (int R = split + 1; R < N; R++) {
                sumR += arr[R];
            }
            ans = Math.max(ans, Math.min(sumL, sumR));
        }
        return ans;
    }

    public static int[] randArr(int maxL, int maxV) {
        int N = (int) (Math.random() * maxL);
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxV);
        }
        return arr;
    }

    public static void main(String[] args) {
        int numTest = 100000;
        int maxL = 20;
        int maxV = 30;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            int[] arr = randArr(maxL, maxV);
            int ans1 = bestSplit(arr);
            int ans2 = naive(arr);
            if (ans1 != ans2) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed");
    }

}
