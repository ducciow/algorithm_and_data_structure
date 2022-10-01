package systematic.section14_Greedy;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @Author: duccio
 * @Date: 14, 04, 2022
 * @Description: A gold bar needs to be split into segments with corresponding lengths indicated in an array. Each
 *      splitting costs the equal value to the length of segment that is operated on. Return the minimum cost of
 *      splitting this gold bar.
 * @Note:   Sol1. Greedy strategy: Huffman Coding.
 *          Sol2. Brute force.
 */
public class Code03_SplitGold {

    // Sol. 1
    public static int split1(int[] segments) {
        if (segments == null || segments.length == 0) {
            return 0;
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int segment : segments) {
            pq.add(segment);
        }
        int ans = 0;
        while (pq.size() > 1) {
            int seg1 = pq.poll();
            int seg2 = pq.poll();
            ans += seg1 + seg2;
            pq.add(seg1 + seg2);
        }
        return ans;
    }

    // Sol. 2
    public static int split2(int[] segments) {
        if (segments == null || segments.length == 0) {
            return 0;
        }
        return process2(segments, 0);
    }

    public static int process2(int[] segments, int cost) {
        if (segments.length == 1) {
            return cost;
        }
        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i < segments.length; i++) {
            for (int j = i + 1; j < segments.length; j++) {
                int[] remainSegments = copyMerge(segments, i, j);
                minCost = Math.min(minCost, process2(remainSegments, cost + segments[i] + segments[j]));
            }
        }
        return minCost;
    }

    public static int[] copyMerge(int[] arr, int i, int j) {
        int[] ret = new int[arr.length - 1];
        int idx = 0;
        for (int k = 0; k < arr.length; k++) {
            if (k != i && k != j) {
                ret[idx++] = arr[k];
            }
        }
        ret[idx] = arr[i] + arr[j];
        return ret;
    }

    // test
    public static int[] generateArr(int maxL, int maxV) {
        int[] arr = new int[(int) (Math.random() * (maxL + 1))];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxV + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int numTest = 10000;
        int maxL = 6;
        int maxV = 200;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            int[] arr = generateArr(maxL, maxV);
            int ans1 = split1(arr);
            int ans2 = split2(arr);
            if (ans1 != ans2) {
                System.out.println("Failed on case: " + Arrays.toString(arr));
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
