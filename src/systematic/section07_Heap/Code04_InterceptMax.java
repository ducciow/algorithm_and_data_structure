package systematic.section07_Heap;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @Author: duccio
 * @Date: 05, 04, 2022
 * @Description: A line is defined as (start point, end point). Given a list of lines, return the maximum number of
 *      lines that intercept with each other.
 * @Note:   - Ver1. Check points discretely:
 *              a) Eg., check number of intersections at each 0.5 point in the entire range.
 *              b) Would be conditionally acceptable according to size of dataset.
 *          - Ver2. Use Heap sort:
 *              1. Sort lines in ascending order based on their start points (using any sort algorithm).
 *              2. For each line l:
 *                  a) put l's end point into a min heap.
 *                  b) poll the heap until there is no endpoint in it that is smaller than or equal to l's start point,
 *                      meaning all end points remained in the heap belong to lines intercepting with l, which is in
 *                      total of the current heap size.
 *              3. Return the maximum size of the heap.
 */
public class Code04_InterceptMax {

    public static int coverMax1(int[][] lines) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int[] line : lines) {
            min = Math.min(min, line[0]);
            max = Math.max(max, line[1]);
        }
        int ans = 0;
        for (double point = min + 0.5; point < max; point += 1) {
            int curAns = 0;
            for (int[] line : lines) {
                if (line[0] < point && line[1] > point) {
                    curAns++;
                }
            }
            ans = Math.max(ans, curAns);
        }
        return ans;
    }

    public static int coverMax2(int[][] lines) {
        int[][] arr = Arrays.copyOf(lines, lines.length);
        Arrays.sort(arr, (o1, o2) -> (o1[0] - o2[0]));
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int ans = 0;
        for (int[] line : arr) {
            heap.add(line[1]);
            while (!heap.isEmpty() && heap.peek() <= line[0]) {
                heap.poll();
            }
            ans = Math.max(ans, heap.size());
        }
        return ans;
    }

    public static int[][] generateLines(int maxLen, int maxVal) {
        int[][] lines = new int[(int) (Math.random() * maxLen) + 1][2];
        for (int i = 0; i < lines.length; i++) {
            lines[i][1] = (int) (Math.random() * maxVal) + 1;
            lines[i][0] = (int) (Math.random() * lines[i][1]);
        }
        return lines;
    }

    public static void main(String[] args) {
        int numTest = 10000;
        int maxL = 100;
        int maxV = 200;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            int[][] lines = generateLines(maxL, maxV);
            int ans1 = coverMax1(lines);
            int ans2 = coverMax2(lines);
            if (ans1 != ans2) {
                System.out.println("Failed on case: " + Arrays.toString(lines));
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
