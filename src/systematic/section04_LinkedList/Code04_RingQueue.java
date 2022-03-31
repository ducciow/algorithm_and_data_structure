package systematic.section04_LinkedList;

/**
 * @Author: duccio
 * @Date: 30, 03, 2022
 * @Description: Implement ring queue using array
 * @Note:   1. Use two indices for offer and poll
 *          2. Use size to decouple index checking
 */
public class Code04_RingQueue {

    public static class RingQueue {
        int[] arr;
        int sizeLimit;
        int offerIdx;
        int pollIdx;
        int size;

        public RingQueue(int limit) {
            sizeLimit = limit;
            arr = new int[sizeLimit];
            offerIdx = 0;
            pollIdx = 0;
            size = 0;
        }

        public void offer(int val) {
            if (size == sizeLimit) {
                throw new RuntimeException("Cannot add items anymore!");
            }
            arr[offerIdx] = val;
            offerIdx = offerIdx % sizeLimit;
            size++;
        }

        public int poll() {
            if (size == 0) {
                throw new RuntimeException("Nothing to poll!");
            }
            int ans = arr[pollIdx];
            pollIdx = pollIdx % sizeLimit;
            size--;
            return ans;
        }

        public boolean isEmpty() {
            return size == 0;
        }
    }
}
