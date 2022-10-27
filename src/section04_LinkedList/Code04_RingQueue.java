package section04_LinkedList;

/**
 * @Author: duccio
 * @Date: 30, 03, 2022
 * @Description: Implement ring queue using array.
 * @Note:   - The constructor needs one argument: arr_size.
 *          - Key Variables:
 *              * two pointers for offer and poll, and remember to increase both pointers in a modulus way.
 *              * size, to decouple index checking.
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
            size++;
            arr[offerIdx] = val;
            offerIdx = nextIndex(offerIdx);
        }

        public int poll() {
            if (size == 0) {
                throw new RuntimeException("Nothing to poll!");
            }
            size--;
            int ans = arr[pollIdx];
            pollIdx = nextIndex(pollIdx);
            return ans;
        }

        private int nextIndex(int i) {
            return i < sizeLimit - 1 ? i + 1 : 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }
    }
}
