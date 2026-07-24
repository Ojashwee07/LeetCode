class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int maxXor = 2048;

        boolean[] pairXor = new boolean[maxXor];
        int n = nums.length;

        // All pair XORs (i <= j)
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                pairXor[nums[i] ^ nums[j]] = true;
            }
        }

        // Combine with third element
        boolean[] result = new boolean[maxXor];
        for (int x = 0; x < maxXor; x++) {
            if (!pairXor[x]) continue;
            for (int num : nums) {
                result[x ^ num] = true;
            }
        }

        int ans = 0;
        for (boolean b : result) {
            if (b) ans++;
        }

        return ans;
    }
}