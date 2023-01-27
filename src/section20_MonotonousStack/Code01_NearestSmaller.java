package section20_MonotonousStack;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 04, 05, 2022
 * @Description: A Monotonous Stack aims to solve: Given an integer array, for each element in it, return the left and
 *      right nearest positions that has a smaller item.
 * @Note:   - Monotonous Stack:
 *              a) An ordered stack, where all elements in it are array indices that refer to strictly increasing items.
 *                 Answer for any position is obtained when this position is on top of the stack, and it is about to be
 *                 popped.
 *              b) When it comes to a position i in the array that has a smaller item arr[i] than the top item of stack,
 *                 the answer for the top item in stack now has been obtained, by which index i is the answer to the
 *                 right hand side, and the value in stack just under it is the answer to the left hand side. Pop the
 *                 top item and repeat that process until no item in stack is bigger than arr[i], and push i onto stack.
 *              c) Otherwise, the left/right nearest index is -1, meaning no such an answer.
 *              ======
 *          - For array with repeated items：
 *              a) Indices are stored in the stack by linked list, where indices in one list have the same value.
 *              b) Tail element in a list counts for the answer to the left hand side.
 *              c) Elements in a list are popped (obtained the answer) at the same time.
 */
public class Code01_NearestSmaller {

    // return int[N][2]
    public static int[][] nearestSmallerNoRepeat(int[] arr) {
        if (arr == null || arr.length < 1) {
            return null;
        }
        Stack<Integer> stack = new Stack<>();
        int[][] ans = new int[arr.length][2];
        // go through the array
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                int idx = stack.pop();
                ans[idx][0] = stack.isEmpty() ? -1 : stack.peek();
                ans[idx][1] = i;
            }
            stack.push(i);
        }
        // handle elements remained in the stack
        while (!stack.isEmpty()) {
            int idx = stack.pop();
            ans[idx][0] = stack.isEmpty() ? -1 : stack.peek();
            ans[idx][1] = -1;
        }
        return ans;
    }

    public static int[][] nearestSmallerWithRepeat(int[] arr) {
        if (arr == null || arr.length < 1) {
            return null;
        }
        Stack<LinkedList<Integer>> stack = new Stack<>();
        int[][] ans = new int[arr.length][2];
        // go through the array
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                LinkedList<Integer> list = stack.pop();
                int leftSmaller = stack.isEmpty() ? -1 : stack.peek().getLast();
                for (Integer idx : list) {
                    ans[idx][0] = leftSmaller;
                    ans[idx][1] = i;
                }
            }
            // push i to stack, accordingly
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().addLast(i);
            } else {
                LinkedList<Integer> list = new LinkedList<>();
                list.add(i);
                stack.push(list);
            }
        }
        // handle the remained elements in stack
        while (!stack.isEmpty()) {
            LinkedList<Integer> list = stack.pop();
            int leftSmaller = stack.isEmpty() ? -1 : stack.peek().getLast();
            for (Integer idx : list) {
                ans[idx][0] = leftSmaller;
                ans[idx][1] = -1;
            }
        }
        return ans;
    }


    public static int[][] naive(int[] arr) {
        if (arr == null || arr.length < 1) {
            return null;
        }
        int[][] ret = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            ret[i][0] = -1;
            ret[i][1] = -1;
            for (int l = i - 1; l >= 0; l--) {
                if (arr[l] < arr[i]) {
                    ret[i][0] = l;
                    break;
                }
            }
            for (int r = i + 1; r < arr.length; r++) {
                if (arr[r] < arr[i]) {
                    ret[i][1] = r;
                    break;
                }
            }
        }
        return ret;
    }


    public static int[] getRandomArrayNoRepeat(int size) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < arr.length; i++) {
            int swapIndex = (int) (Math.random() * arr.length);
            int tmp = arr[swapIndex];
            arr[swapIndex] = arr[i];
            arr[i] = tmp;
        }
        return arr;
    }

    public static int[] getRandomArrayWithRepeat(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max) - (int) (Math.random() * max);
        }
        return arr;
    }

    public static boolean isEqual(int[][] res1, int[][] res2) {
        if (res1.length != res2.length) {
            return false;
        }
        for (int i = 0; i < res1.length; i++) {
            if (res1[i][0] != res2[i][0] || res1[i][1] != res2[i][1]) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int size = 20;
        int max = 20;
        int testTimes = 100000;
        System.out.println("Test begin...");
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = getRandomArrayNoRepeat(size);
            int[] arr2 = getRandomArrayWithRepeat(size, max);
            if (!isEqual(nearestSmallerNoRepeat(arr1), naive(arr1))) {
                System.out.println("Failed on cases without repeat");
                return;
            }
            if (!isEqual(nearestSmallerWithRepeat(arr2), naive(arr2))) {
                System.out.println("Failed on cases with repeat");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
