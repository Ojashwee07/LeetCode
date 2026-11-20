import java.util.Arrays;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        
        // Maximum possible value in nums is 10^6, and we can increment by 1
        int maxVal = nums[nums.length - 1] + 1;
        int[] dp = new int[maxVal + 1];
        
        int maxConsecutive = 0;
        
        for (int x : nums) {
            // Option 1: Increment x to x + 1 (must be evaluated first)
            dp[x + 1] = dp[x] + 1;
            
            // Option 2: Keep x as x
            dp[x] = dp[x - 1] + 1;
            
            maxConsecutive = Math.max(maxConsecutive, Math.max(dp[x], dp[x + 1]));
        }
        
        return maxConsecutive;
    }
}