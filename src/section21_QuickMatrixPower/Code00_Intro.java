package section21_QuickMatrixPower;

/**
 * @Author: duccio
 * @Date: 05, 05, 2022
 * @Description:
 * @Note:
 */
public class Code00_Intro {

    /*
    For a sequence of natural numbers, if there is a strict defining equation for the n-th number:
                f(n) = a_1 * f(n-1) + a_2 * f(n-2) + ... + a_k * f(n-k)
        where, a_i can be neg., pos., or 0, 1<=i<=k, then, there exits:
                [f(n), f(n-1),...f(n-k+1)] = [f(n-1), f(n-2),...,f(n-k)] * M
        where, M is a square matrix, and further:
                [f(n), f(n-1),...f(n-k+1)] = [f(k), f(k-1),...,f(1)] * M**(n-k)
     */

    /*
    To compute M**n, use the binary representation of n, and iteratively do multiplications on M**k, where k is
        aligned with the presence of 1's in n's binary representation. E.g., to compute M**11 and given that 11
        is 1011 in binary, then M**11 = I * M**1 * M**2 * M**8.

    This process costs O(logN).
     */
}
