import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        final int INF = 1000000000;
        
        // Precompute valid divisors for each length L (2 <= L <= n)
        List<Integer>[] divisors = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            divisors[i] = new ArrayList<>();
        }
        for (int L = 2; L <= n; ++L) {
            for (int d = 1; d < L; ++d) {
                if (L % d == 0) {
                    divisors[L].add(d);
                }
            }
        }
        
        // Precompute minimum changes to make s[i..j] a semi-palindrome
        int[][] cost = new int[n][n];
        for (int[] row : cost) {
            Arrays.fill(row, INF);
        }
        
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                int L = j - i + 1;
                int minChanges = INF;
                for (int d : divisors[L]) {
                    int changes = 0;
                    // Check each of the d interleaved groups
                    for (int start = 0; start < d; ++start) {
                        int left = i + start;
                        int right = j - (d - 1 - start);
                        while (left < right) {
                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }
                            left += d;
                            right -= d;
                        }
                    }
                    if (changes < minChanges) {
                        minChanges = changes;
                    }
                }
                cost[i][j] = minChanges;
            }
        }
        
        // dp[p][i]: min changes to partition s[0..i-1] into p semi-palindromes
        int[][] dp = new int[k + 1][n + 1];
        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }
        dp[0][0] = 0;
        
        for (int p = 1; p <= k; ++p) {
            // Each partition needs at least 2 characters
            for (int i = 2 * p; i <= n; ++i) {
                for (int j = 2 * (p - 1); j <= i - 2; ++j) {
                    if (dp[p - 1][j] != INF && cost[j][i - 1] != INF) {
                        dp[p][i] = Math.min(dp[p][i], dp[p - 1][j] + cost[j][i - 1]);
                    }
                }
            }
        }
        
        return dp[k][n];
    }
}