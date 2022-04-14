package systematic.section14_Greedy;

import java.util.HashSet;

/**
 * @Author: duccio
 * @Date: 14, 04, 2022
 * @Description: Given a string of 'x' and '.', where 'x' refers to wall that cannot put light and '.' refers to road
 *      that can put light on. A light at index i can light up three positions: i-1, i, i+1. Requiring all roads to be
 *      lighten up, return the minimum number of lights needed.
 * @Note:   Ver1. Greedy strategy: Discuss based on whether i, i+1 and i+2 are '.'.
 *          Ver2. Brute force.
 *          Ver3. Dynamic programming (see later section).
 */
public class Code05_MinLight {

    public static void main(String[] args) {
        validate();
    }

    public static int minLight1(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        int ans = 0;
        int i = 0;
        while (i < chars.length) {
            if (chars[i] == 'x') {
                i++;
            } else {
                ans++;
                if (i + 1 == chars.length) {
                    break;
                } else if (chars[i + 1] == 'x') {
                    i += 2;
                } else {
                    i += 3;
                }
            }
        }
        return ans;
    }

    public static int minLight2(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        char[] chars = str.toCharArray();
        return process2(chars, 0, new HashSet<Integer>());
    }

    // chars[0...idx-1] are processed
    // chars[idx...] needs to process
    // lights stores indices of lights
    public static int process2(char[] chars, int idx, HashSet<Integer> lights) {
        if (idx == chars.length) {
            // remove invalid cases
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '.') {
                    if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
                        return Integer.MAX_VALUE;
                    }
                }
            }
            return lights.size();
        }
        int noPut = process2(chars, idx + 1, lights);
        int yesPut = Integer.MAX_VALUE;
        if (chars[idx] == '.') {
            lights.add(idx);
            yesPut = process2(chars, idx + 1, lights);
            lights.remove(idx);
        }
        return Math.min(noPut, yesPut);
    }

    public static String randomString(int len) {
        char[] res = new char[(int) (Math.random() * len) + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = Math.random() < 0.5 ? 'x' : '.';
        }
        return String.valueOf(res);
    }

    public static void validate() {
        int numTest = 10000;
        int len = 20;
        for (int i = 0; i < numTest; i++) {
            String test = randomString(len);
            int ans1 = minLight1(test);
            int ans2 = minLight2(test);
            if (ans1 != ans2) {
                System.out.println("Failed on case: " + test);
                return;
            }
        }
        System.out.println("Test passed!");
    }

}
