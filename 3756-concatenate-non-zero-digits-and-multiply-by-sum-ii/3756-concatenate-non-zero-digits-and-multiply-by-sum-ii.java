import java.util.*;

class Solution {
    static final int MOD = 1_000_000_007;

    public int[] sumAndMultiply(String s, int[][] queries) {
        int m = s.length();

        List<Integer> posList = new ArrayList<>();
        List<Integer> digitList = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            int d = s.charAt(i) - '0';
            if (d != 0) {
                posList.add(i);
                digitList.add(d);
            }
        }

        int n = digitList.size();

        int[] pos = new int[n];
        int[] digit = new int[n];

        for (int i = 0; i < n; i++) {
            pos[i] = posList.get(i);
            digit[i] = digitList.get(i);
        }

        long[] pow10 = new long[n + 1];
        pow10[0] = 1;
        for (int i = 1; i <= n; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }

        long[] hash = new long[n + 1];
        long[] prefixSum = new long[n + 1];

        for (int i = 0; i < n; i++) {
            hash[i + 1] = (hash[i] * 10 + digit[i]) % MOD;
            prefixSum[i + 1] = prefixSum[i] + digit[i];
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1];

            int left = lowerBound(pos, l);
            int right = upperBound(pos, r) - 1;

            if (left > right) {
                ans[i] = 0;
                continue;
            }

            int len = right - left + 1;

            long x = (hash[right + 1] - (hash[left] * pow10[len]) % MOD + MOD) % MOD;
            long sum = prefixSum[right + 1] - prefixSum[left];

            ans[i] = (int) ((x * (sum % MOD)) % MOD);
        }

        return ans;
    }

    private int lowerBound(int[] arr, int target) {
        int l = 0, r = arr.length;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (arr[mid] >= target) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    private int upperBound(int[] arr, int target) {
        int l = 0, r = arr.length;
        while (l < r) {
            int mid = l + (r - l) / 2;
            if (arr[mid] <= target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return l;
    }
}