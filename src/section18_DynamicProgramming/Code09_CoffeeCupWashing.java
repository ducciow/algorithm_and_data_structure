package section18_DynamicProgramming;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @Author: duccio
 * @Date: 26, 04, 2022
 * @Description: Two sub-tasks: 1. There are several coffee machines using different amount of time to make a cup of
 *      coffee. Here comes people using those machines for coffee drinking. Return the arrangement that all people
 *      have their coffee at the earliest time. Each machine is making one cup of coffee each time, and once a machine
 *      finishes one cup, it can directly start to make another. 2. There is one cup washing machine that can wash one
 *      coffee cup at one time. When a person finishes the drinking, he can decide to use the washing machine or leave
 *      the cup to evaporate itself. Given the array of time people finish their drinking, return the earliest time
 *      all coffee cups become clear.
 * @Note:   For sub-task1:
 *          - use a min heap storing all coffee machines based on the sum time of their current starting point and
 *            working time. Arrange each coming person to the polled machine.
 *          ======
 *          For sub-task2:
 *          - Ver1. brute force according to whether the current cup is machine washed.
 *          - Ver2. DP.
 *              a) It is attempt with task limit, ie., the readyToWash time point varies.
 *              b) In this case, use the maximum limit as the dp table size.
 *              c) When filling in the dp table, early stop if it meets unrealistic cells, eg., the
 *                 readyToWash exceeds the maximum possible upper limit. Otherwise, out of boundary.
 */
public class Code09_CoffeeCupWashing {

    public static class Machine {
        int start;
        int working;

        public Machine(int s, int w) {
            start = s;
            working = w;
        }
    }

    // ver. 1
    public static int minTime1(int[] coffeeMakingTime, int numPeople, int washTime, int evaporateTime) {
        // sub-task 1
        PriorityQueue<Machine> pq = new PriorityQueue<>(
                (m1, m2) -> ((m1.start + m1.working) - (m2.start + m2.working)));
        for (int i = 0; i < coffeeMakingTime.length; i++) {
            pq.add(new Machine(0, coffeeMakingTime[i]));
        }
        int[] serve = new int[numPeople];
        for (int n = 0; n < numPeople; n++) {
            Machine machine = pq.poll();
            int done = machine.start + machine.working;
            serve[n] = done;
            machine.start = done;
            pq.add(machine);
        }
        // sub-task 2
        return process1(serve, 0, washTime, evaporateTime, 0);
    }

    // readyToWash: available time of the washing machine
    public static int process1(int[] serve, int idx, int wash, int evaporate, int readyToWash) {
        if (idx == serve.length) {
            return 0;
        }
        // if use washing machine
        int selfClean1 = Math.max(serve[idx], readyToWash) + wash;
        int restClean1 = process1(serve, idx + 1, wash, evaporate, selfClean1);
        int p1 = Math.max(selfClean1, restClean1);
        // if evaporate
        int selfClean2 = serve[idx] + evaporate;
        int restClean2 = process1(serve, idx + 1, wash, evaporate, readyToWash);
        int p2 = Math.max(selfClean2, restClean2);

        return Math.min(p1, p2);
    }

    // ver. 2
    public static int minTime2(int[] coffeeMakingTime, int numPeople, int washTime, int evaporateTime) {
        // sub-task 1
        PriorityQueue<Machine> pq = new PriorityQueue<>(
                (m1, m2) -> ((m1.start + m1.working) - (m2.start + m2.working)));
        for (int i = 0; i < coffeeMakingTime.length; i++) {
            pq.add(new Machine(0, coffeeMakingTime[i]));
        }
        int[] serve = new int[numPeople];
        for (int n = 0; n < numPeople; n++) {
            Machine machine = pq.poll();
            int done = machine.start + machine.working;
            serve[n] = done;
            machine.start = done;
            pq.add(machine);
        }
        // sub-task 2
        return process2(serve, washTime, evaporateTime);
    }

    public static int process2(int[] serve, int wash, int evaporate) {
        int maxReady = 0;
        for (int sv : serve) {
            maxReady = Math.max(sv, maxReady) + wash;
        }
        int N = serve.length;
        int[][] dp = new int[N + 1][maxReady + 1];
        for (int idx = N - 1; idx >= 0; idx--) {
            for (int readyToWash = 0; readyToWash <= maxReady; readyToWash++) {
                int selfClean1 = Math.max(serve[idx], readyToWash) + wash;
                // early stop
                if (selfClean1 > maxReady) {
                    break;
                }
                // if use washing machine
                int restClean1 = dp[idx + 1][selfClean1];
                int p1 = Math.max(selfClean1, restClean1);
                // if evaporate
                int selfClean2 = serve[idx] + evaporate;
                int restClean2 = dp[idx + 1][readyToWash];
                int p2 = Math.max(selfClean2, restClean2);
                dp[idx][readyToWash] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }


    public static int[] genRandArr(int len, int maxV) {
        int[] ret = new int[len];
        for (int i = 0; i < len; i++) {
            ret[i] = (int) (Math.random() * maxV) + 1;
        }
        return ret;
    }

    public static void main(String[] args) {
        int numTest = 10;
        int len = 10;
        int maxV = 10;
        System.out.println("Test begin...");
        for (int i = 0; i < numTest; i++) {
            int[] arr = genRandArr(len, maxV);
            int n = (int) (Math.random() * 7) + 1;
            int wash = (int) (Math.random() * 7) + 1;
            int evaporate = (int) (Math.random() * 10) + 1;
            int ans1 = minTime1(arr, n, wash, evaporate);
            int ans2 = minTime2(arr, n, wash, evaporate);
            if (ans1 != ans2) {
                System.out.println("Failed on case: ");
                System.out.println(Arrays.toString(arr));
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
