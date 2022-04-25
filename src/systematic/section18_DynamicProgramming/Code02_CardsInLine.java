package systematic.section18_DynamicProgramming;

/**
 * @Author: duccio
 * @Date: 22, 04, 2022
 * @Description: Two players are taking cards alternatively from a line of cards. Each time only cards at head or tail
 *      can be taken. Each card has a positive face value, and the player with higher accumulative value will win. Known
 *      that both players are smart. Return the winner's accumulative value.
 * @Note:   Ver1. Brute forcing recursion by two processes for first/second move respectively.
 *                Player1 maximizes its options.
 *                Player2 maximizes its options after Player1 trying to minimize Player2's maximization.
 *          Ver2. Use two naive cache tables.
 *          Ver3. DP, where varying arguments are two indices that cards can be taken.
 *                No need to initialize all cells to be -1.
 *          ======
 *          Attempt on a shrinking range.
 */
public class Code02_CardsInLine {

    public static void main(String[] args) {
        int[] cards = {5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7};
        System.out.println(win1(cards));
        System.out.println(win2(cards));
        System.out.println(win3(cards));
    }

    public static int win1(int[] cards) {
        if (cards == null || cards.length < 1) {
            return 0;
        }
        int p1 = firstMove1(cards, 0, cards.length - 1);
        int p2 = secondMove1(cards, 0, cards.length - 1);
        return Math.max(p1, p2);
    }

    // the first move is allowed to take a card from position L or R
    public static int firstMove1(int[] cards, int L, int R) {
        if (L == R) {
            return cards[L];
        }
        int option1 = cards[L] + secondMove1(cards, L + 1, R);
        int option2 = cards[R] + secondMove1(cards, L, R - 1);
        return Math.max(option1, option2);
    }

    // the second move is allowed to wait for a card from position L or R is taken, and then becomes the first move
    private static int secondMove1(int[] cards, int L, int R) {
        if (L == R) {
            return 0;
        }
        int option1 = firstMove1(cards, L + 1, R);
        int option2 = firstMove1(cards, L, R - 1);
        return Math.min(option1, option2);
    }


    public static int win2(int[] cards) {
        if (cards == null || cards.length < 1) {
            return 0;
        }
        int[][] firstTable = new int[cards.length][cards.length];
        int[][] secondTable = new int[cards.length][cards.length];
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards.length; j++) {
                firstTable[i][j] = -1;
                secondTable[i][j] = -1;
            }
        }
        int p1 = firstMove2(cards, 0, cards.length - 1, firstTable, secondTable);
        int p2 = secondMove2(cards, 0, cards.length - 1, firstTable, secondTable);
        return Math.max(p1, p2);
    }

    public static int firstMove2(int[] cards, int L, int R, int[][] table1, int[][] table2) {
        if (table1[L][R] != -1) {
            return table1[L][R];
        }
        int ans = 0;
        if (L == R) {
            ans = cards[L];
        } else {
            int option1 = cards[L] + secondMove2(cards, L + 1, R, table1, table2);
            int option2 = cards[R] + secondMove2(cards, L, R - 1, table1, table2);
            ans = Math.max(option1, option2);
        }
        table1[L][R] = ans;
        return ans;
    }

    private static int secondMove2(int[] cards, int L, int R, int[][] table1, int[][] table2) {
        if (table2[L][R] != -1) {
            return table2[L][R];
        }
        int ans = 0;
        if (L == R) {
            ans = 0;
        } else {
            int option1 = firstMove2(cards, L + 1, R, table1, table2);
            int option2 = firstMove2(cards, L, R - 1, table1, table2);
            ans = Math.min(option1, option2);
        }
        table2[L][R] = ans;
        return ans;
    }


    public static int win3(int[] cards) {
        if (cards == null || cards.length < 1) {
            return 0;
        }
        int N = cards.length;
        int[][] table1 = new int[N][N];
        int[][] table2 = new int[N][N];
        // base case when L == R
        for (int d = 0; d < N; d++) {
            table1[d][d] = cards[d];
        }
        // fill the table diagonally from center to top-right
        for (int startCol = 1; startCol < N; startCol++) {
            int L = 0;
            int R = startCol;
            while (R < N) {
                table1[L][R] = Math.max(cards[L] + table2[L + 1][R], cards[R] + table2[L][R - 1]);
                table2[L][R] = Math.min(table1[L][R - 1], table1[L + 1][R]);
                L++;
                R++;
            }
        }
        int p1 = table1[0][N - 1];
        int p2 = table2[0][N - 1];
        return Math.max(p1, p2);
    }

}
