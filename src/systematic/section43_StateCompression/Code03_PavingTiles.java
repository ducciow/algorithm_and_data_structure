package systematic.section43_StateCompression;

/**
 * @Author: duccio
 * @Date: 06, 06, 2022
 * @Description: Use unlimited 1*2 tiles to pave M*N area. Return the number of paving ways.
 * @Note:   Ver1. brute force.
 *          Ver2. brute force with bit representation of state.
 *          Ver3. dp using memory cache based on Ver2.
 *          ======
 *          1. For every position (i, j), constraint the tile to be paved in (i-1, j) or (i, j+1).
 *          2. Then, for each row, the paving operation is dependent in its up row. For a cell in current row, if its
 *             up cell is unpaved, it must be paved in (i-1, j), otherwise, it can be paved in (i, j+1) or not paved.
 *          3. The outer iteration is for rows, and the inner iteration is for columns using dfs.
 *          ======
 *          When using bit representation for state, ensure the min{N, M} is no bigger than 32.
 */
public class Code03_PavingTiles {

    public static int ways1(int M, int N) {
        if (M < 1 || N < 1 || ((M * N) & 1) != 0) {
            return 0;
        }
        if (M == 1 || N == 1) {
            return 1;
        }
        int[] upRow = new int[N];
        // -1st row are all 1's, meaning paved
        for (int i = 0; i < N; i++) {
            upRow[i] = 1;
        }
        return process1(upRow, 0, M);
    }

    private static int process1(int[] upRow, int row, int numRow) {
        if (row == numRow) {
            for (int i = 0; i < upRow.length; i++) {
                if (upRow[i] != 1) {
                    return 0;
                }
            }
            return 1;
        }
        int[] operation = getOperation1(upRow);
        return dfs1(operation, 0, row, numRow);
    }

    private static int dfs1(int[] op, int col, int row, int numRow) {
        if (col == op.length) {
            return process1(op, row + 1, numRow);
        }
        int ans = 0;
        // option 1: not pave
        ans += dfs1(op, col + 1, row, numRow);
        // option 2: pave horizontally
        if (col + 1 < op.length && op[col] == 0 && op[col + 1] == 0) {
            op[col] = 1;
            op[col + 1] = 1;
            ans += dfs1(op, col + 2, row, numRow);
            op[col] = 0;
            op[col + 1] = 0;
        }
        return ans;
    }

    private static int[] getOperation1(int[] upRow) {
        int[] op = new int[upRow.length];
        for (int i = 0; i < upRow.length; i++) {
            // if up cell is 0, this cell must be paved vertically, no other option
            // otherwise, this cell is 0, leaving two options: pave horizontally or not pave
            op[i] = upRow[i] ^ 1;
        }
        return op;
    }

    public static int ways2(int M, int N) {
        if (M < 1 || N < 1 || ((M * N) & 1) != 0) {
            return 0;
        }
        if (M == 1 || N == 1) {
            return 1;
        }
        int numRow = Math.max(M, N);
        int numCol = Math.min(M, N);
        int upRow = (1 << numCol) - 1;
        return process2(upRow, 0, numRow, numCol);
    }

    private static int process2(int upRow, int row, int numRow, int numCol) {
        if (row == numRow) {
            return upRow == ((1 << numCol) - 1) ? 1 : 0;
        }
        int op = (~upRow) & ((1 << numCol) - 1);
        return dfs2(op, numCol - 1, row, numRow, numCol);  // starts from the last column for convenience
    }

    private static int dfs2(int op, int col, int row, int numRow, int numCol) {
        if (col == -1) {
            return process2(op, row + 1, numRow, numCol);
        }
        int ans = 0;
        // option 1
        ans += dfs2(op, col - 1, row, numRow, numCol);
        // option 2
        if (col > 0 && (op & (1 << col)) == 0 && (op & (1 << (col - 1))) == 0) {
            ans += dfs2(op | (3 << (col - 1)), col - 2, row, numRow, numCol);
        }
        return ans;
    }

    public static int ways3(int M, int N) {
        if (M < 1 || N < 1 || ((M * N) & 1) != 0) {
            return 0;
        }
        if (M == 1 || N == 1) {
            return 1;
        }
        int numRow = Math.max(M, N);
        int numCol = Math.min(M, N);
        int upRow = (1 << numCol) - 1;
        int[][] lookup = new int[1 << numCol][numRow + 1];
        for (int i = 0; i < lookup.length; i++) {
            for (int j = 0; j < lookup[0].length; j++) {
                lookup[i][j] = -1;
            }
        }
        return process3(upRow, 0, numRow, numCol, lookup);
    }

    private static int process3(int upRow, int row, int numRow, int numCol, int[][] lookup) {
        if (lookup[upRow][row] != -1) {
            return lookup[upRow][row];
        }
        int ans = 0;
        if (row == numRow) {
            ans = upRow == ((1 << numCol) - 1) ? 1 : 0;
        } else {
            int op = (~upRow) & ((1 << numCol) - 1);
            ans = dfs3(op, numCol - 1, row, numRow, numCol, lookup);
        }
        lookup[upRow][row] = ans;
        return ans;
    }

    private static int dfs3(int op, int col, int row, int numRow, int numCol, int[][] lookup) {
        if (col == -1) {
            return process3(op, row + 1, numRow, numCol, lookup);
        }
        int ans = 0;
        // option 1
        ans += dfs3(op, col - 1, row, numRow, numCol, lookup);
        // option 2
        if (col > 0 && (op & (3 << (col - 1))) == 0) {
            ans += dfs3(op | (3 << (col - 1)), col - 2, row, numRow, numCol, lookup);
        }
        return ans;
    }


    // copied for test
    public static int dp(int N, int M) {
        if (N < 1 || M < 1 || ((N * M) & 1) != 0) {
            return 0;
        }
        if (N == 1 || M == 1) {
            return 1;
        }
        int big = N > M ? N : M;
        int small = big == N ? M : N;
        int sn = 1 << small;
        int limit = sn - 1;
        int[] dp = new int[sn];
        dp[limit] = 1;
        int[] cur = new int[sn];
        for (int level = 0; level < big; level++) {
            for (int status = 0; status < sn; status++) {
                if (dp[status] != 0) {
                    int op = (~status) & limit;
                    dfs4(dp[status], op, 0, small - 1, cur);
                }
            }
            for (int i = 0; i < sn; i++) {
                dp[i] = 0;
            }
            int[] tmp = dp;
            dp = cur;
            cur = tmp;
        }
        return dp[limit];
    }

    public static void dfs4(int way, int op, int index, int end, int[] cur) {
        if (index == end) {
            cur[op] += way;
        } else {
            dfs4(way, op, index + 1, end, cur);
            if (((3 << index) & op) == 0) { // 11 << index 可以放砖
                dfs4(way, op | (3 << index), index + 1, end, cur);
            }
        }
    }

    public static void main(String[] args) {
        int M = 8;
        int N = 6;
        System.out.println(ways3(M, N));
        System.out.println(dp(M, N));
    }


}
