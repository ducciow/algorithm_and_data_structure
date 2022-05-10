package systematic.section25_Reservoir;

/**
 * @Author: duccio
 * @Date: 10, 05, 2022
 * @Description: A pipe automatically gives numbered balls. A box can contain at most 10 balls. Design a rule of
 *      putting balls into the box, so that at any time, the probability of any ball being in the box is equal.
 * @Note:   1. The first 10 balls directly go into the box.
 *          2. When the k-th ball comes, first, with prob of 10/k keep the ball, and then with prob of 1/10 choose
 *             a ball from the box to discard.
 */
public class Code01_ReservoirSampling {

    public static void main(String[] args) {
        int numTest = 1000000;
        int numBalls = 17;
        int[] count = new int[numBalls];
        for (int i = 0; i < numTest; i++) {
            int[] box = new int[10];
            int idx = 0;
            for (int ball = 0; ball < numBalls; ball++) {
                if (ball < 10) {
                    box[idx++] = ball;
                } else {
                    if (randomK(ball + 1) < 10) {
                        idx = (int) (Math.random() * 10);
                        box[idx] = ball;
                    }
                }
            }
            for (int ball : box) {
                count[ball]++;
            }
        }
        for (int n : count) {
            System.out.println(n);
        }
    }

    // equally returns an integer within 0 to k-1
    public static int randomK(int k) {
        return (int) (Math.random() * k);
    }

}
