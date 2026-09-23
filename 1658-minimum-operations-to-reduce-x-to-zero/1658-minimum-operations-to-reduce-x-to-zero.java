class Solution {
    public int minOperations(int[] nums, int x) {

        int n = nums.length;

        // Calculate total sum
        long totalSum = 0;

        for (int num : nums) {
            totalSum += num;
        }

        // We need to keep a subarray with this sum
        long target = totalSum - x;

        // If target is negative, impossible
        if (target < 0) {
            return -1;
        }

        // If target == 0, remove everything
        if (target == 0) {
            return n;
        }

        int left = 0;
        long sum = 0;
        int maxLength = -1;

        for (int right = 0; right < n; right++) {

            sum += nums[right];

            // Shrink window
            while (sum > target && left <= right) {
                sum -= nums[left];
                left++;
            }

            // Found required sum
            if (sum == target) {
                maxLength = Math.max(maxLength, right - left + 1);
            }
        }

        // No valid subarray found
        if (maxLength == -1) {
            return -1;
        }

        return n - maxLength;
    }
}