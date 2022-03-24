package primary.class07;

import java.util.LinkedList;
import java.util.Stack;

/**
 * @Author: duccio
 * @Date: 24, 03, 2022
 * @Description: Three versions of using stack in Java
 * @Note:
 */
public class Code27_StackInJava {
    public static void main(String[] args) {
        // 1. Stack
        Stack<Integer> s1 = new Stack<>();
        s1.push(1);
        s1.push(2);
        s1.push(3);
        while (!s1.isEmpty()) {
            System.out.println(s1.pop());
        }

        // 2. LinedList slowest!
        System.out.println("======");
        LinkedList<Integer> s2 = new LinkedList<>();
        s2.addLast(1);
        s2.addLast(2);
        s2.addLast(3);
        while (!s2.isEmpty()) {
            System.out.println(s2.pollLast());
        }

        // 3. array fastest!!!
        System.out.println("======");
        int[] s3 = new int[100];
        int idx = 0;
        s3[idx++] = 1;
        s3[idx++] = 2;
        s3[idx++] = 3;
        while (idx > 0) {
            System.out.println(s3[--idx]);
        }
    }
}
