import java.util.*;

class Solution {
    public int maximumLength(int[] nums) {
        Map<Long, Integer> freq = new HashMap<>();

        for (int x : nums) {
            freq.put((long) x, freq.getOrDefault((long) x, 0) + 1);
        }

        int ans = 1;

        for (long start : freq.keySet()) {

            if (start == 1) {
                int cnt = freq.get(1L);
                ans = Math.max(ans, cnt % 2 == 0 ? cnt - 1 : cnt);
                continue;
            }

            long curr = start;
            int len = 0;

            while (freq.getOrDefault(curr, 0) >= 2) {
                len += 2;
                if (curr > 1000000000L) break; // prevent overflow
                curr = curr * curr;
            }

            if (freq.getOrDefault(curr, 0) == 1) {
                len++;
            } else if (len > 0) {
                len--;
            }

            ans = Math.max(ans, len);
        }

        return ans;
    }
}