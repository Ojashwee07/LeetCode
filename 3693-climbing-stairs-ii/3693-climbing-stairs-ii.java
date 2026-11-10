class Solution {
    public int climbStairs(int n, int[] costs) {
        // dp[i] stores the minimum cost to reach step i
        long[] dp = new long[n + 1];
        dp[0] = 0;

        for (int i = 1; i <= n; i++) {
            long minCost = Long.MAX_VALUE;
            
            // Try jumps of size k = 1, 2, and 3
            for (int k = 1; k <= 3; k++) {
                if (i - k >= 0) {
                    long cost = dp[i - k] + costs[i - 1] + (long) k * k;
                    minCost = Math.min(minCost, cost);
                }
            }
            
            dp[i] = minCost;
        }

        return (int) dp[n];
    }
}