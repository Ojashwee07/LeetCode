import java.util.HashMap;
import java.util.Map;

class Solution {
    private static final long BASE = 1_000_003L;
    private static final long MOD = 1_000_000_007L;

    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int low = 1, high = n;
        int ans = n;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (hasUniqueOfLength(nums, mid)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return ans;
    }

    private boolean hasUniqueOfLength(int[] nums, int len) {
        int n = nums.length;
        long pow = 1;
        for (int i = 0; i < len - 1; i++) {
            pow = (pow * BASE) % MOD;
        }

        long hash = 0;
        for (int i = 0; i < len; i++) {
            hash = (hash * BASE + nums[i]) % MOD;
        }

        Map<Long, Integer> freq = new HashMap<>();
        freq.put(hash, 1);

        for (int i = 1; i <= n - len; i++) {
            long outgoing = (nums[i - 1] * pow) % MOD;
            hash = (hash - outgoing + MOD) % MOD;
            hash = (hash * BASE + nums[i + len - 1]) % MOD;
            freq.put(hash, freq.getOrDefault(hash, 0) + 1);
        }

        for (int count : freq.values()) {
            if (count == 1) {
                return true;
            }
        }
        return false;
    }
}
