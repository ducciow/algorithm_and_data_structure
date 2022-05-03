package systematic.section19_SlidingWindow;

import java.util.LinkedList;

/**
 * @Author: duccio
 * @Date: 03, 05, 2022
 * @Description: There are n gas stations along a circular route, where the amount of gas at the i-th station is gas[i].
 *      You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from the i-th station to its
 *      next station. You begin the journey with an empty tank at one of the gas stations. Given two integer arrays gas
 *      and cost, return if each gas station is valid for you to travel around the circuit once clockwise.
 *      https://leetcode.com/problems/gas-station
 * @Note:   1. Essentially, net-value = gas[i] - cost[i] matters.
 *          2. Directly compute a double-length array for testing each gas station.
 *          3. By computing all the partial sums, meaning the so-far accumulative net-value, it can utilize a sliding
 *             window to check if the minimum value within a window is negative. Negative minimum means invalid.
 *          4. There is an offset, the accumulative net-value just before the current window, which can be subtracted
 *             to directly get the current accumulative net-value.
 */
public class Code03_GasStation {

    public int canCompleteCircuit(int[] gas, int[] cost) {
        if (gas == null || cost == null || gas.length != cost.length) {
            return -1;
        }
        boolean[] good = checkStation(gas, cost);
        for (int i = 0; i < good.length; i++) {
            if (good[i]) {
                return i;
            }
        }
        return -1;
    }

    public static boolean[] checkStation(int[] gas, int[] cost) {
        int N = gas.length;
        int[] net = new int[N << 1];
        net[0] = gas[0] - cost[0];
        for (int i = 1; i < net.length; i++) {
            net[i] = gas[i % N] - cost[i % N] + net[i - 1];
        }
        LinkedList<Integer> min = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            while (!min.isEmpty() && net[min.peekLast()] >= net[i]) {
                min.pollLast();
            }
            min.addLast(i);
        }
        boolean[] ret = new boolean[N];
        for (int L = 0, R = N, offset = 0; L < N; offset = net[L], L++, R++) {
            if (net[min.peekFirst()] - offset >= 0) {
                ret[L] = true;
            }
            if (min.peekFirst() == L) {
                min.pollFirst();
            }
            while (!min.isEmpty() && net[min.peekLast()] >= net[R]) {
                min.pollLast();
            }
            min.addLast(R);
        }
        return ret;
    }

}
