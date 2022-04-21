package systematic.section17_BruteForcingRecursion;

/**
 * @Author: duccio
 * @Date: 21, 04, 2022
 * @Description: Hanoi Tower
 * @Note:   Ver1. naive recursion.
 *          Ver2. improved.
 */
public class Code01_HanoiTower {

    public static void main(String[] args) {
        hanoi1(3);
        System.out.println("======");
        hanoi2(3);
    }

    public static void hanoi1(int N) {
        left2right(N);
    }

    public static void left2right(int n) {
        if (n == 1) {
            System.out.println("move 1 from left to right");
        } else {
            left2mid(n - 1);
            System.out.println("move " + n + " from left to right");
            mid2right(n - 1);
        }
    }

    public static void left2mid(int n) {
        if (n == 1) {
            System.out.println("move 1 from left to middle");
        } else {
            left2right(n - 1);
            System.out.println("move " + n + " from left to middle");
            right2mid(n - 1);
        }
    }

    public static void mid2left(int n) {
        if (n == 1) {
            System.out.println("move 1 from middle to left");
        } else {
            mid2right(n - 1);
            System.out.println("move " + n + " from middle to left");
            right2left(n - 1);
        }
    }

    public static void mid2right(int n) {
        if (n == 1) {
            System.out.println("move 1 from middle to right");
        } else {
            mid2left(n - 1);
            System.out.println("move " + n + " from middle to right");
            left2right(n - 1);
        }
    }

    public static void right2mid(int n) {
        if (n == 1) {
            System.out.println("move 1 from right to middle");
        } else {
            right2left(n - 1);
            System.out.println("move " + n + " from right to middle");
            left2mid(n - 1);
        }
    }

    public static void right2left(int n) {
        if (n == 1) {
            System.out.println("move 1 from right to left");
        } else {
            right2mid(n - 1);
            System.out.println("move " + n + " from right to left");
            mid2left(n - 1);
        }
    }

    public static void hanoi2(int N) {
        move(N, "left", "right", "middle");
    }

    private static void move(int n, String from, String to, String tmp) {
        if (n == 1) {
            System.out.println("Move 1 from " + from + " to " + to);
        } else {
            move(n - 1, from, tmp, to);
            System.out.println("Move " + n + " from " + from + " to " + to);
            move(n - 1, tmp, to, from);
        }
    }


}
