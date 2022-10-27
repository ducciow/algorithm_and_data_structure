package section03_BitOperation;

/**
 * @Author: duccio
 * @Date: 29, 03, 2022
 * @Description: Swap two numbers without using extra variables.
 * @Note:   - 0 ^ n = n
 *          - n ^ n = 0
 *          - Two addresses must be different, otherwise will be incorrect.
 */
public class Code01_SwapXOR {

    public static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }
}
