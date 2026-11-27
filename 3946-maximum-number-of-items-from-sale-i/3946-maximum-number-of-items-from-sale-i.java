class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] bonus = new int[n];
        int minPrice = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            minPrice = Math.min(minPrice, items[i][1]);
            int fi = items[i][0];
            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % fi == 0) {
                    bonus[i]++;
                }
            }
        }

        int[] dp = new int[budget + 1];
        for (int i = 0; i < n; i++) {
            int cost = items[i][1];
            int val = 1 + bonus[i];
            for (int b = budget; b >= cost; b--) {
                if (dp[b - cost] + val > dp[b]) {
                    dp[b] = dp[b - cost] + val;
                }
            }
        }

        int ans = budget / minPrice;
        for (int b = 0; b <= budget; b++) {
            int total = dp[b] + (budget - b) / minPrice;
            if (total > ans) {
                ans = total;
            }
        }

        return ans;
    }
}