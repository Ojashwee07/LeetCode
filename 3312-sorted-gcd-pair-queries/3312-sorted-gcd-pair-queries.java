class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int max = 0;
        for (int x : nums) max = Math.max(max, x);

        int[] freq = new int[max + 1];
        for (int x : nums) freq[x]++;

        long[] exact = new long[max + 1];

        // exact[g] = number of pairs whose GCD is exactly g
        for (int g = max; g >= 1; g--) {
            long cnt = 0;
            for (int m = g; m <= max; m += g) {
                cnt += freq[m];
            }

            long pairs = cnt * (cnt - 1) / 2;
            for (int m = g + g; m <= max; m += g) {
                pairs -= exact[m];
            }
            exact[g] = pairs;
        }

        // Prefix counts in sorted gcdPairs
        long[] prefix = new long[max + 1];
        for (int g = 1; g <= max; g++) {
            prefix[g] = prefix[g - 1] + exact[g];
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            long q = queries[i];

            int lo = 1, hi = max;
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (prefix[mid] > q) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }
            ans[i] = lo;
        }

        return ans;
    }
}