package section38_CatalanNumber;

import java.util.HashMap;

/**
 * @Author: duccio
 * @Date: 26, 05, 2022
 * @Description: Given n pairs of brackets, return the number of legitimate arrangement.
 * @Note:   1. Let set A = invalid arrangement of n pairs of brackets, and let B = negate the last 2n-k brackets of any
 *             arrangement where k is the size of its head subarray whose number of right bracket is one more than
 *             left bracket.
 *          2. |A| == |B|.
 *          3. legitimate number = all possibilities - invalid number
 *                               = c(2n, n) - c(2n, n+1)
 */
public class Code01_BracketProblem {

    public static void main(String[] args) {
        System.out.println(bracket(101));
    }

    public static long bracket(int n) {
        HashMap<Long, Long> map = new HashMap<>();
        return catalan(n, map);
    }

    public static long catalan(long n, HashMap<Long, Long> map) {
        if (n == 0 || n == 1) {
            return 1;
        }
        if (map.containsKey(n)) {
            return map.get(n);
        }
        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans += catalan(i, map) * catalan(n - 1 - i, map);
        }
        map.put(n, ans);
        return ans;
    }

}
