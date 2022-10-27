package section20_MonotonousStack;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 04, 05, 2022
 * @Description: Given an integer array, for each item in it, return the left and right nearest indices that have a
 *      smaller item.
 * @Note:   1. A monotonous stack is used, where all elements in stack are indices that have strictly increasing items
 *             in the original array.
 *          2. If there is repeated item in the array, stack stores list, where each index in the same list has the
 *             same item in the original array.
 *          3. When it comes to the current item that is smaller than the top value of stack, it means the current index
 *             is the right nearest smaller one, and the remaining top value in stack is the left nearest smaller one.
 *          4. Otherwise, the left/right nearest index is -1.
 */
public class Code01_NearestSmaller {

    public static void main(String[] args) {
        validate();
    }

    public static int[][] nearestSmallerNoRepeat(int[] arr) {
        if (arr == null || arr.length < 1) {
            return null;
        }
        Stack<Integer> stack = new Stack<>();
        int[][] ret = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                int idx = stack.pop();
                ret[idx][0] = stack.isEmpty() ? -1 : stack.peek();
                ;
                ret[idx][1] = i;
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int idx = stack.pop();
            ret[idx][0] = stack.isEmpty() ? -1 : stack.peek();
            ;
            ret[idx][1] = -1;
        }
        return ret;
    }

    public static int[][] nearestSmaller(int[] arr) {
        if (arr == null || arr.length < 1) {
            return null;
        }
        Stack<LinkedList<Integer>> stack = new Stack<>();
        int[][] ret = new int[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek().get(0)] > arr[i]) {
                LinkedList<Integer> list = stack.pop();
                int leftSmaller = stack.isEmpty() ? -1 : stack.peek().getLast();
                for (Integer idx : list) {
                    ret[idx][0] = leftSmaller;
                    ret[idx][1] = i;
                }
            }
            if (!stack.isEmpty() && arr[stack.peek().get(0)] == arr[i]) {
                stack.peek().addLast(i);
            } else {
                LinkedList<Integer> list = new LinkedList<>();
                list.add(i);
                stack.push(list);
            }
        }
        while (!stack.isEmpty()) {
            LinkedList<Integer> list = stack.pop();
            int leftSmaller = stack.isEmpty() ? -1 : stack.peek().getLast();
            for (Integer idx : list) {
                ret[idx][0] = leftSmaller;
                ret[idx][1] = -1;
            }
        }
        return ret;
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

    public static int[] getRandomArray(int size, int max) {
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

    public static void validate() {
        int size = 10;
        int max = 20;
        int testTimes = 20000;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = getRandomArrayNoRepeat(size);
            int[] arr2 = getRandomArray(size, max);
            if (!isEqual(nearestSmallerNoRepeat(arr1), naive(arr1))) {
                System.out.println("Failed on cases without repeat");
                return;
            }
            if (!isEqual(nearestSmaller(arr2), naive(arr2))) {
                System.out.println("Failed on cases with repeat");
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
