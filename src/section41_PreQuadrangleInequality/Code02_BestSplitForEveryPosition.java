package section41_PreQuadrangleInequality;

/**
 * @Author: duccio
 * @Date: 04, 06, 2022
 * @Description: Given a non-negative integer array, for every subarray slicing from 0, return the maximum of
 *      min{left_sum, right_sum}.
 * @Note:   Key idea: the best split position for every subarray is not going back.
 *          ======
 *          1. Track the best split position for every subarray.
 *          2. Use pre-sums for quickly getting the partial sums.
 *          ======
 *          If the below holds:
 *              ans = max{min{left_norm, right_norm}}  or  ans = min{max{left_norm, right_norm}},
 *              and there is monotonicity between the norm and range size,
 *          then the split position might not go back.
 */
public class Code02_BestSplitForEveryPosition {

    public static int[] bestSplit(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        // get pre-sums, put an extra 0 at the head for convenience
        int[] preSums = new int[N + 1];
        for (int i = 0; i < N; i++) {
            preSums[i + 1] = preSums[i] + arr[i];
        }
        int[] ans = new int[N];
        //ans[0] = 0;
        int best = 0;  // track the best split position
        for (int range = 1; range < N; range++) {
            while (best + 1 < range) {
                int next = best + 1;
                int before = Math.min(preSums[best + 1] - preSums[0], preSums[range + 1] - preSums[best + 1]);
                int after = Math.min(preSums[next + 1] - preSums[0], preSums[range + 1] - preSums[next + 1]);
                if (after >= before) {
                    best++;
                } else {
                    break;
                }
            }
            ans[range] = Math.min(preSums[best + 1] - preSums[0], preSums[range + 1] - preSums[best + 1]);
        }
        return ans;
    }


    public static int[] naive(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }
        int N = arr.length;
        int[] ans = new int[N];
        //ans[0] = 0;
        for (int range = 1; range < N; range++) {
            for (int split = 0; split < range; split++) {
                int sumL = 0;
                for (int L = 0; L <= split; L++) {
                    sumL += arr[L];
                }
                int sumR = 0;
                for (int R = split + 1; R <= range; R++) {
                    sumR += arr[R];
                }
                ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
            }
        }
        return ans;
    }

    public static int[] randomArray(int len, int max) {
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        return ans;
    }

    public static boolean isSameArray(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        int N = arr1.length;
        for (int i = 0; i < N; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int testTime = 1000000;
        int N = 20;
        int max = 30;
        System.out.println("Test begin...");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, max);
            int[] ans1 = bestSplit(arr);
            int[] ans2 = naive(arr);
            if (!isSameArray(ans1, ans2)) {
                System.out.println("Failed");
                return;
            }
        }
        System.out.println("Test passed");
    }
}
