package systematic.section37_TryAccordingToDataScale;

import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: duccio
 * @Date: 26, 05, 2022
 * @Description: Given n snacks with sizes v[], and a bag of size w, return the number of packing ways using the bag,
 *      where 1 <= n <= 30, 1 <= w <= 2 * 10**9, 0 <= v[i] <= 10**9.
 *      https://www.nowcoder.com/questionTerminal/d94bb2fa461d42bcb4c0f2b94f5d4281
 * @Note:   Divide and Conquer
 */
public class Code03_SnackPacking {

    public static long ways(int[] arr, int bag) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] <= bag ? 2 : 1;
        }
        int mid = arr.length / 2;
        TreeMap<Long, Long> map1 = new TreeMap<>();
        long ans = process(arr, bag, 0, mid, 0, map1);
        TreeMap<Long, Long> map2 = new TreeMap<>();
        ans += process(arr, bag, mid + 1, arr.length - 1, 0, map2);

        TreeMap<Long, Long> sumMap = new TreeMap<>();
        Long preSum = 0L;
        for (Map.Entry<Long, Long> elem2 : map2.entrySet()) {
            preSum += elem2.getValue();
            sumMap.put(elem2.getKey(), preSum);
        }
        for (Map.Entry<Long, Long> elem1 : map1.entrySet()) {
            Long key = elem1.getKey();
            Long val = elem1.getValue();
            Long floor = sumMap.floorKey(bag - key);
            if (floor != null) {
                ans += val * sumMap.get(floor);
            }
        }

        // slower:
//        for (Map.Entry<Long, Long> elem1 : map1.entrySet()) {
//            for (Map.Entry<Long, Long> elem2 : map2.entrySet()) {
//                if (elem1.getKey() + elem2.getKey() <= bag) {
//                    ans += elem1.getValue() * elem2.getValue();
//                }
//            }
//        }
        return ans + 1;
    }

    public static long process(int[] arr, int bag, int idx, int end, long sum, TreeMap<Long, Long> map) {
        if (sum > bag) {
            return 0;
        }
        if (idx > end) {
            if (sum != 0) {
                map.put(sum, map.getOrDefault(sum, 0L) + 1);
                return 1;
            } else {
                return 0;
            }
        } else {
            long ways = process(arr, bag, idx + 1, end, sum, map);
            ways += process(arr, bag, idx + 1, end, sum + arr[idx], map);
            return ways;
        }

    }

}
