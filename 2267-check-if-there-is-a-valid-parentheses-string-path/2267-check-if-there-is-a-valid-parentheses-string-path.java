class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // A valid parentheses string must have even length
        if ((m + n - 1) % 2 != 0) {
            return false;
        }

        boolean[][][] dp = new boolean[m][n][m + n];

        // Starting cell
        int startBalance = grid[0][0] == '(' ? 1 : -1;

        if (startBalance < 0) {
            return false;
        }

        dp[0][0][startBalance] = true;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                // Skip starting cell
                if (i == 0 && j == 0) {
                    continue;
                }

                for (int balance = 0; balance < m + n; balance++) {

                    boolean possible = false;

                    // From top
                    if (i > 0 && dp[i - 1][j][balance]) {
                        possible = true;
                    }

                    // From left
                    if (j > 0 && dp[i][j - 1][balance]) {
                        possible = true;
                    }

                    if (!possible) {
                        continue;
                    }

                    int newBalance;

                    if (grid[i][j] == '(') {
                        newBalance = balance + 1;
                    } else {
                        newBalance = balance - 1;
                    }

                    // Balance can never become negative
                    if (newBalance >= 0 && newBalance < m + n) {
                        dp[i][j][newBalance] = true;
                    }
                }
            }
        }

        // Valid string requires final balance = 0
        return dp[m - 1][n - 1][0];
    }
}