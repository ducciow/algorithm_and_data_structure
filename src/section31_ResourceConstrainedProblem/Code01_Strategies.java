package section31_ResourceConstrainedProblem;

/**
 * @Author: duccio
 * @Date: 17, 05, 2022
 * @Description:
 * @Note:
 */
public class Code01_Strategies {

    /*

    Strategies for resource constrained problems:

    1. Bloom Filter for set construction and search, saving huge space.

    2. Consistent Hashing for server overloading.

    3. UnionFindSet for parallel computing of island problem.

    4. Hash function almost equally distribute resource according to types.

    Q. Given 4 billion unsigned numbers and 1G memory, find the most frequent number.
    A. Fails: Unsigned number range: [0, 2**32 - 1] = 4.2+ billion.
              If use array to sort 4 billion numbers, it uses 16 billion bytes (16G).
              If use HashMap to count all numbers, it uses 8 bytes for best cases, and 32G for worst cases.
       Works: 1G HashMap can contain 1G / 8 = 0.125 billion records. To be conservative, say 0.01 billion records.
              Then 4 / 0.01 = 400, and for each number n, distribute it to its group based on hash(n) % 400. Then
              use HashMap to count each group.

    5. Bit Map for range occurrences of numbers, saving huge space.

    Q. Given 4 billion unsigned numbers and 1G memory, find all the absent numbers.
    A. (2**32 - 1)/8 < 1G, so use a bit array to mark number occurrences. A bit array can be conceptually built using
       an int array, eg., int[10] arr is conceptually bit[320], and for the i-th number, arr[i/32] & (1 << (i%32))
       refers to the corresponding bit.

    Q. Given 4 billion unsigned numbers and 3KB memory, find all the numbers appearing twice.
    A. Use two digits for marking each number, '11' means appearing twice.

    6. Statistics in segments.

    Q. Given 4 billion unsigned numbers and 3KB memory, find one absent number.
    A. A 3KB int array can contain at most 3000/4 = 750 integers. Pick the nearest smaller power of 2, which is 512, as
       the arr length, ie., int[512]. Then split 2**32 into 512 segments, and the range size covered by each segment
       is (2**32) / 512 = 8388608. Since it is given 4 billion < 2**32 numbers, there must be a segment having the range
       statistics < 8388608. Then same operation on that segment until find the absent number.

    Q. If only given several variables, how to find one absent number?
    A. Similar idea but bi-segment each time.

    Q. Given 4 billion unsigned numbers and 3KB memory, find the median number.
    A. Pick the segment that the accumulative statistics is just beyond 2 billion, and repeat.


    7. Use Heap or External Sorting for processing and merging multiple segments.

    Q. Use 5G memory to sort 10G document.
    A. Construct a MaxHeap, iterate through the document, storing the 5G-memory-th smallest (number, occurrences), then
       write all occurrences of numbers in MaxHeap to output in ascending order, pick the maximum number as the
       threshold, cleat MaxHeap, and iterate the document again, just storing numbers larger than the threshold, repeat.

    Q. Find topK frequent numbers.
    A. Use hash to distribute numbers into groups. Find topK in each group. Use external sorting or heap to pick the
       final topK of all topK's.

     */

}
