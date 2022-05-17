package systematic.section30_Hash;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * @Author: duccio
 * @Date: 16, 05, 2022
 * @Description:
 * @Note:   1. Unlimited domain are equally scattered on limited co-domain.
 *          2. One input always get the same output, while two different inputs might get same output.
 *          3. Time complexity for each operation: O(1), due to capacity expansion. The total number of expansion
 *             operations at worst is 1 + 2 + 4 + 8 + ..., which is summed to (1 * (1 - 2**logN)) / (1 - 2) = O(N).
 */
public class Code01_Hash {

    public static void main(String[] args) throws NoSuchAlgorithmException {

        System.out.println("Supported hash algorithms: ");
        for (String str : Security.getAlgorithms("MessageDigest")) {
            System.out.println(str);
        }
        
        System.out.println("=======");

        String algorithm = "MD5";
        MessageDigest hash = MessageDigest.getInstance(algorithm);;
        
        String input1 = "algorithmisfun1";
        String input2 = "algorithmisfun2";
        String input3 = "algorithmisfun3";
        System.out.println(hashCode(hash, input1));
        System.out.println(hashCode(hash, input2));
        System.out.println(hashCode(hash, input3));

    }

    public static String hashCode(MessageDigest hash, String input) {
        return DatatypeConverter.printHexBinary(hash.digest(input.getBytes())).toUpperCase();
    }
    
}
