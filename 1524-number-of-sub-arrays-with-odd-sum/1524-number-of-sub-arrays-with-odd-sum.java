class Solution {
    public int numOfSubarrays(int[] arr) {
        final int MOD = 1_000_000_007;
        long ans = 0;
        int prefixSum = 0;
        int oddCount = 0;
        int evenCount = 1; // Initial prefix sum 0 is even

        for (int num : arr) {
            prefixSum += num;
            if (prefixSum % 2 != 0) {
                ans = (ans + evenCount) % MOD;
                oddCount++;
            } else {
                ans = (ans + oddCount) % MOD;
                evenCount++;
            }
        }
        return (int) ans;
    }
}