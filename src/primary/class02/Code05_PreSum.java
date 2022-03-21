package primary.class02;

/**
 * @Author: duccio
 * @Date: 18, 03, 2022
 * @Description: Return the sum ranging from index L to R
 * @Note:   Sol1. Use 2-d table: directly return the value
 *          Sol2. Use 1-d array: one subtraction before returning <---
 */
public class Code05_PreSum {

    public static void main(String[] args) {
        int[] arr = {1, 4, 2, 8, 5, 7};

        PreSum ps = new PreSum();
        ps.generateSumArr(arr);

        System.out.println(ps.rangeSum(0, 3));
        System.out.println(ps.rangeSum(1, 3));
        System.out.println(ps.rangeSum(1, 4));
    }

    public static class PreSum {
        private int[] sumArr;  // store pre sums in a 1-d array

        public void generateSumArr(int[] arr) {
            int N = arr.length;
            sumArr = new int[N];
            int curSum = 0;
            for (int i = 0; i < N; i++) {
                curSum += arr[i];
                sumArr[i] = curSum;
            }
        }

        public int rangeSum(int L, int R) {
            return L == 0 ? sumArr[R] : sumArr[R] - sumArr[L - 1];
        }
    }

}
