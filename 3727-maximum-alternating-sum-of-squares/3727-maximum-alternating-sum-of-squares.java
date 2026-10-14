import java.util.Arrays;

class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        long[] squares = new long[n];

        for (int i = 0; i < n; i++) {
            squares[i] = (long) nums[i] * nums[i];
        }

        Arrays.sort(squares);

        long ans = 0;
        int positiveCount = (n + 1) / 2;

        for (int i = 0; i < n; i++) {
            if (i < positiveCount) {
                ans += squares[n - 1 - i];
            } else {
                ans -= squares[n - 1 - i];
            }
        }

        return ans;
    }
}