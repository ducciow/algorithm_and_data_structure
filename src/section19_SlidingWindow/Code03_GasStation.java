package section19_SlidingWindow;

import java.util.LinkedList;

/**
 * @Author: duccio
 * @Date: 03, 05, 2022
 * @Description: There are n gas stations along a circular route, where the i-th station provides gas[i] amount of gas.
 *      You have a car with an unlimited gas tank, and it costs cost[i] of gas to travel from the i-th station to the
 *      next station. You begin the journey with an empty tank at one of the gas stations. Given two integer arrays gas
 *      and cost, return if each gas station is valid for you to travel around the circuit once clockwise.
 *      https://leetcode.com/problems/gas-station
 * @Note:   - Essentially, gas[i] - cost[i] matters.
 *          - By computing partial sums of gas[i]-cost[i], it can utilize a sliding window of width N to check if the
 *            minimum partial sum within a circuit is negative, which means invalid.
 *          - It is convenient to convert a circuit of length N to an array of length 2*N, where every single station
 *            can be simply checked.
 *          - When the sliding window starts from i, by subtracting an offset of partial_sum[i-1], the station at i
 *            is just treated as the starting station in the circuit.
 */
public class Code03_GasStation {

    // this function only asks for a valid starting gas station
    public int canCompleteCircuit(int[] gas, int[] cost) {
        if (gas == null || cost == null || gas.length != cost.length) {
            return -1;
        }
        boolean[] good = checkStations(gas, cost);
        for (int i = 0; i < good.length; i++) {
            if (good[i]) {
                return i;
            }
        }
        return -1;
    }

    // checks the validity of every station as the starting station
    public static boolean[] checkStations(int[] gas, int[] cost) {
        int N = gas.length;
        int[] net = new int[N << 1];
        // partial sums of gas[i] - cost[i]
        net[0] = gas[0] - cost[0];
        for (int i = 1; i < net.length; i++) {
            net[i] = gas[i % N] - cost[i % N] + net[i - 1];
        }
        // deque
        LinkedList<Integer> min = new LinkedList<>();
        // get the min deque of the first N elements, ie., when the circuit starts from the 0-th station
        for (int i = 0; i < N; i++) {
            while (!min.isEmpty() && net[min.peekLast()] >= net[i]) {
                min.pollLast();
            }
            min.addLast(i);
        }
        // store the validity of every station
        boolean[] ans = new boolean[N];
        // slide the window forwards
        for (int L = 0, R = N, offset = 0; L < N; offset = net[L], L++, R++) {
            // check validity of this circuit
            if (net[min.peekFirst()] - offset >= 0) {
                ans[L] = true;
            }
            // check out-of-boundary
            if (min.peekFirst() == L) {
                min.pollFirst();
            }
            // update the current min
            while (!min.isEmpty() && net[min.peekLast()] >= net[R]) {
                min.pollLast();
            }
            min.addLast(R);
        }
        return ans;
    }

}
