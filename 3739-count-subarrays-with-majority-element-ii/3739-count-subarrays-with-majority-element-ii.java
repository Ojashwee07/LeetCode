import java.util.*;

class Solution {
    static class Fenwick {
        int[] bit;

        Fenwick(int n) {
            bit = new int[n + 2];
        }

        void add(int idx, int val) {
            while (idx < bit.length) {
                bit[idx] += val;
                idx += idx & -idx;
            }
        }

        int sum(int idx) {
            int res = 0;
            while (idx > 0) {
                res += bit[idx];
                idx -= idx & -idx;
            }
            return res;
        }
    }

    public long countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;
        int[] pref = new int[n + 1];

        for (int i = 0; i < n; i++) {
            pref[i + 1] = pref[i] + (nums[i] == target ? 1 : -1);
        }

        int[] vals = pref.clone();
        Arrays.sort(vals);

        Fenwick ft = new Fenwick(n + 2);
        long ans = 0;

        for (int x : pref) {
            int idx = Arrays.binarySearch(vals, x) + 1;
            ans += ft.sum(idx - 1);
            ft.add(idx, 1);
        }

        return ans;
    }
}