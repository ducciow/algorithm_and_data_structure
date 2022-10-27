package section45_SimplifyErternalInfoForDP;

/**
 * @Author: duccio
 * @Date: 09, 06, 2022
 * @Description: You are given several boxes with different colors represented by different positive numbers. You may
 *      experience several rounds to remove boxes until there is no box left. Each time you can choose some continuous
 *      boxes with the same color (i.e., composed of k boxes, k >= 1), remove them and get k * k points. Return the
 *      maximum points you can get.
 *      https://leetcode.com/problems/remove-boxes/
 * @Note:   Ver1. brute force.
 *          Ver2. dp with memory cache and some small optimization tricks
 *          ======
 *          Key idea: For any range to deal with, assume there are k consecutive boxes before have the same color with
 *                    the head box in this range. Then discuss based on remove the head same colored boxes together,
 *                    or keep them to remove with some same colored box latter.
 */
public class Code02_RemoveBoxes {

    public static int removeBoxes1(int[] boxes) {
        int N = boxes.length;
        return process1(boxes, 0, N - 1, 0);
    }

    // to remove arr[L..R], assuming there are k consecutive boxes before have the color arr[L]
    // return the maximum points by removing arr[L..R]
    public static int process1(int[] arr, int L, int R, int k) {
        if (L > R) {
            return 0;
        }
        // remove arr[L] with those k boxes first
        int ans = process1(arr, L + 1, R, 0) + (k + 1) * (k + 1);
        // remove those k boxes and arr[L] with some same colored box in arr[L+1..R]
        for (int i = L + 1; i <= R; i++) {
            if (arr[i] == arr[L]) {
                ans = Math.max(ans, process1(arr, L + 1, i - 1, 0) + process1(arr, i, R, k + 1));
            }
        }
        return ans;
    }


    public static int removeBoxes2(int[] boxes) {
        int N = boxes.length;
        int[][][] lookup = new int[N][N][N];
        return process2(boxes, 0, N - 1, 0, lookup);
    }

    // use a memory cache and some small optimization
    public static int process2(int[] arr, int L, int R, int k, int[][][] lookup) {
        if (L > R) {
            return 0;
        }
        if (lookup[L][R][k] > 0) {
            return lookup[L][R][k];
        }
        // find the last position of consecutive same colored boxes in the head of arr
        int last = L;
        while (last + 1 <= R && arr[last + 1] == arr[L]) {
            last++;
        }
        // combine the k + last - L boxes together, leaving the last box there
        // because separating them will never be the optimal solution anyway
        int pre = k + last - L;
        // remove all same colored boxes in the head together
        int ans = process2(arr, last + 1, R, 0, lookup) + (pre + 1) * (pre + 1);
        for (int i = last + 2; i <= R; i++) {
            // find the first same colored box in a latter position
            // no need to process all the same colored boxes, because the next call will deal with them
            if (arr[i] == arr[L] && arr[i - 1] != arr[L]) {
                ans = Math.max(ans,
                        process2(arr, last + 1, i - 1, 0, lookup) +
                                process2(arr, i, R, pre + 1, lookup));
            }
        }
        lookup[L][R][k] = ans;
        return ans;
    }

}
