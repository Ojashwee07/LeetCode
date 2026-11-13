import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        long ans = 0;
        long cost = 0;
        // Monotonic deque storing indices of `nums` in decreasing order of values
        Deque<Integer> dq = new ArrayDeque<>();
        
        int j = nums.length - 1;
        for (int i = nums.length - 1; i >= 0; --i) {
            final int num = nums[i];
            
            // Maintain monotonic property by popping smaller elements and adding their adjustment cost
            while (!dq.isEmpty() && nums[dq.peekLast()] < num) {
                final int l = dq.pollLast();
                final int r = dq.isEmpty() ? j + 1 : dq.peekLast();
                cost += (long) (r - l) * (num - nums[l]);
            }
            dq.offerLast(i);
            
            // Shrink the window from the right if the cost exceeds k
            while (cost > k) {
                cost -= nums[dq.peekFirst()] - nums[j];
                if (dq.peekFirst() == j) {
                    dq.pollFirst();
                }
                --j;
            }
            
            // Add all valid subarrays starting at index i
            ans += j - i + 1;
        }
        
        return ans;
    }
}