package section14_Greedy;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @Author: duccio
 * @Date: 14, 04, 2022
 * @Description: A program has cost and profit. Given the starting principal and an array of programs, return the
 *      maximum final capital you will get if you are allowed to choose at most K any programs to do.
 * @Note: Greedy strategy:
 *          a) Prepare a minHeap and a maxHeap.
 *          b) Put all programs into the minHeap based on costs.
 *          c) Iteratively poll programs that can be done with current capital and put them into the maxHeap
 *             based on profits.
 *          d) Always choose the first program in maxHeap to do.
 */
public class Code04_CostProfitProgram {

    public static class Program {
        int cost;
        int profit;

        public Program(int c, int p) {
            cost = c;
            profit = p;
        }
    }

    public static int maximizeCapital(int[] costs, int[] profits, int initCap, int K) {
        if (costs == null || profits == null || costs.length != profits.length || costs.length == 0 || K < 1) {
            return initCap;
        }
        PriorityQueue<Program> minCostQ = new PriorityQueue<>(new CostComparator());
        PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new ProfitComparator());
        for (int i = 0; i < costs.length; i++) {
            minCostQ.add(new Program(costs[i], profits[i]));
        }
        int cap = initCap;
        for (int i = 0; i < K; i++) {
            while (!minCostQ.isEmpty() && minCostQ.peek().cost <= cap) {
                maxProfitQ.add(minCostQ.poll());
            }
            if (maxProfitQ.isEmpty()) {
                break;
            }
            cap += maxProfitQ.poll().profit;
        }
        return cap;
    }

    public static class CostComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.cost - o2.cost;
        }
    }

    public static class ProfitComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o2.profit - o1.profit;
        }
    }
}
