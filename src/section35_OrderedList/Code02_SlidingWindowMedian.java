package section35_OrderedList;


import section33_SizeBalancedTree.Code01_SizeBalancedTree;

import java.util.Arrays;

/**
 * @Author: duccio
 * @Date: 23, 05, 2022
 * @Description: Given an integer array, and a sliding window of fixed size, return the median within the window as the
 *      window sliding forwards. A median is the average of middle elements.
 * @Note:   Just use the function -- getIndexKey() of Size Balanced Tree.
 *          ======
 *          Median is the middle value of odd window size, and is average of even window size.
 */
public class Code02_SlidingWindowMedian {

    public static void main(String[] args) {
        int[] arr = {1, 4, 2, 8, 5, 7};
        int k = 3;
        System.out.println(Arrays.toString(medianSlidingWindow(arr, k)));
    }

    public static double[] medianSlidingWindow(int[] nums, int k) {
        Code01_SizeBalancedTree.SBT<Node, Integer> sbt = new Code01_SizeBalancedTree.SBT<>();  // only key is used
        for (int i = 0; i < k - 1; i++) {
            sbt.put(new Node(i, nums[i]), 0);  // value does not matter
        }
        double[] ans = new double[nums.length - k + 1];
        int idx = 0;
        for (int i = k - 1; i < nums.length; i++) {
            sbt.put(new Node(i, nums[i]), 0);
            if (sbt.size() % 2 == 0) {
                Node upMid = sbt.getIndexKey(sbt.size() / 2 - 1);
                Node downMid = sbt.getIndexKey(sbt.size() / 2);
                ans[idx++] = ((double) (upMid.value) + (double) (downMid.value)) / 2;
            } else {
                Node mid = sbt.getIndexKey(sbt.size() / 2);
                ans[idx++] = (double) mid.value;
            }
            sbt.remove(new Node(i - k + 1, nums[i - k + 1]));
        }
        return ans;
    }


    public static class Node implements Comparable<Node> {
        int index;
        int value;

        public Node(int i, int v) {
            index = i;
            value = v;
        }


        @Override
        public int compareTo(Node o) {
            return value != o.value ? Integer.valueOf(value).compareTo(o.value)
                    : Integer.valueOf(index).compareTo(o.index);
        }
    }

}
