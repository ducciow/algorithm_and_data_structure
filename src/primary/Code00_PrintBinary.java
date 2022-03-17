package primary;

/**
 * @Author: duccio
 * @Date: 16 03 2022
 * @Description: Print the binary presentation of integers (32-digit).
 */
public class Code00_PrintBinary {

    public static void main(String[] args) {
        int num = 1;
        printBinary(num);
        printBinary(num << 1);
        printBinary(num << 2);
        printBinary(num<< 3);
    }

    private static void printBinary(int num){
        for (int i = 31; i >= 0 ; i--) {
            System.out.print((num & 1 << i) == 0 ? 0 : 1);
        }
        System.out.println();
    }
}
