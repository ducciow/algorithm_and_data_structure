package systematic.section36_UseValidatorToFindPattern;

/**
 * @Author: duccio
 * @Date: 25, 05, 2022
 * @Description: A cow and a sheep are eating grass alternatively, with each time eating exactly power of 4 quantities.
 *      Given n quantities of grass, return who eats the last grass. Assuming the cow and sheep are smart.
 * @Note:
 */
public class Code02_EatGrass {

    // "offensive" refers to the current player
    // "defensive" refers to the alternative player
    public static String naive(int n) {
        if (n < 5) {
            return n == 0 || n == 2 ? "defensive" : "offensive";
        }
        int eat = 1;
        while (eat <= n) {
            if (naive(n - eat).equals("defensive")) {
                return "offensive";
            }
            if (n / 4 >= eat) {
                eat *= 4;
            } else {
                break;
            }
        }
        return "defensive";
    }

    public static String pattern(int n) {
        int mod = n % 5;
        if (mod == 0 || mod == 2) {
            return "defensive";
        } else {
            return "offensive";
        }
    }

    public static void main(String[] args) {
//        for (int n = 0; n < 50; n++) {
//            System.out.println(n + " : " + naive(n));
//        }

        for (int i = 0; i < 50; i++) {
            if (!naive(i).equals(pattern(i))) {
                System.out.println("Failed for n = " + i);
                return;
            }
        }
        System.out.println("Test passed!");
    }


}
