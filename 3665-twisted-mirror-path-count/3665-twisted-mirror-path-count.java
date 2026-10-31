class Solution {
    static final int MOD = 1_000_000_007;

    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // dp[c] will store an array of size 2: 
        // [ways coming from top/right-deflection, ways coming from left/down-deflection]
        // To save space, we can maintain a 2D array of size (n + 1) x 2
        long[][] dp = new long[n + 1][2];
        
        // Base case initialization for the start
        dp[1][0] = 1;
        dp[1][1] = 1;

        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == 1) {
                    // Mirror cell: deflection swaps/updates incoming paths
                    dp[c + 1] = new long[] { dp[c + 1][1], dp[c][0] };
                } else {
                    // Empty cell: standard accumulation from top and left
                    long val = (dp[c + 1][1] + dp[c][0]) % MOD;
                    dp[c + 1] = new long[] { val, val };
                }
            }
        }

        return (int) dp[n][0];
    }
}