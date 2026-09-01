import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        // Give every litter cell a bit number
        int[][] litterId = new int[m][n];
        for (int[] row : litterId) {
            Arrays.fill(row, -1);
        }

        int litterCount = 0;
        int sr = 0, sc = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    sr = i;
                    sc = j;
                } else if (ch == 'L') {
                    litterId[i][j] = litterCount++;
                }
            }
        }

        int targetMask = (1 << litterCount) - 1;

        // No litter
        if (targetMask == 0) {
            return 0;
        }

        /*
         * best[r][c][mask] = maximum energy with which
         * we have reached (r,c) having collected mask.
         */
        int[][][] best = new int[m][n][1 << litterCount];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(best[i][j], -1);
            }
        }

        // Queue state: r, c, remainingEnergy, mask, moves
        Queue<int[]> q = new ArrayDeque<>();

        best[sr][sc][0] = energy;
        q.offer(new int[]{sr, sc, energy, 0, 0});

        int[] dr = {1, -1, 0, 0};
        int[] dc = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            int[] cur = q.poll();

            int r = cur[0];
            int c = cur[1];
            int e = cur[2];
            int mask = cur[3];
            int moves = cur[4];

            if (mask == targetMask) {
                return moves;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                    continue;
                }

                if (classroom[nr].charAt(nc) == 'X') {
                    continue;
                }

                // Need one unit of energy for the move
                if (e == 0) {
                    continue;
                }

                int ne = e - 1;
                int nmask = mask;

                // Collect litter
                if (classroom[nr].charAt(nc) == 'L') {
                    int id = litterId[nr][nc];
                    nmask |= (1 << id);
                }

                // Reset energy
                if (classroom[nr].charAt(nc) == 'R') {
                    ne = energy;
                }

                /*
                 * If we already reached this state with
                 * >= remaining energy, this state is useless.
                 */
                if (best[nr][nc][nmask] >= ne) {
                    continue;
                }

                best[nr][nc][nmask] = ne;

                q.offer(new int[]{
                    nr, nc, ne, nmask, moves + 1
                });
            }
        }

        return -1;
    }
}