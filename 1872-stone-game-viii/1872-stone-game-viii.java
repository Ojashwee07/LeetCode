class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;

        // Total sum of all stones
        int prefix = 0;
        for (int x : stones) {
            prefix += x;
        }

        // Initially, Alice can take all stones
        int ans = prefix;

        // Try all possible prefix lengths from n-1 down to 2
        for (int i = n - 1; i >= 2; i--) {
            prefix -= stones[i];

            ans = Math.max(ans, prefix - ans);
        }

        return ans;
    }
}