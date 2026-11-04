#include <vector>
#include <map>
#include <algorithm>
#include <climits>

using namespace std;

class Solution {
public:
    long long minOperations(vector<int>& nums, int x, int k) {
        int n = nums.size();
        
        // Step 1: Precompute cost for every window of size x using dynamic median tracking
        vector<long long> cost(n - x + 1, 0);
        map<int, int> lower, upper;
        long long lowerSum = 0, upperSum = 0;
        int lowerSize = 0, upperSize = 0;

        auto add = [&](int val) {
            if (lower.empty() || val <= lower.rbegin()->first) {
                lower[val]++;
                lowerSum += val;
                lowerSize++;
            } else {
                upper[val]++;
                upperSum += val;
                upperSize++;
            }
        };

        auto remove = [&](int val) {
            if (lower.count(val)) {
                lower[val]--;
                if (lower[val] == 0) lower.erase(val);
                lowerSum -= val;
                lowerSize--;
            } else {
                upper[val]--;
                if (upper[val] == 0) upper.erase(val);
                upperSum -= val;
                upperSize--;
            }
        };

        auto balance = [&]() {
            while (lowerSize < upperSize) {
                int val = upper.begin()->first;
                upper[val]--;
                if (upper[val] == 0) upper.erase(val);
                upperSum -= val;
                upperSize--;

                lower[val]++;
                lowerSum += val;
                lowerSize++;
            }
            while (lowerSize - upperSize > 1) {
                int val = lower.rbegin()->first;
                lower[val]--;
                if (lower[val] == 0) lower.erase(val);
                lowerSum -= val;
                lowerSize--;

                upper[val]++;
                upperSum += val;
                upperSize++;
            }
        };

        for (int i = 0; i < n; ++i) {
            add(nums[i]);
            if (i >= x) {
                remove(nums[i - x]);
            }
            balance();

            if (i >= x - 1) {
                long long median = lower.rbegin()->first;
                cost[i - x + 1] = (median * lowerSize - lowerSum) + (upperSum - median * upperSize);
            }
        }

        // Step 2: Dynamic Programming to choose k non-overlapping subarrays
        // dp[j][i] = min cost choosing j subarrays within prefix ending at index i - 1
        const long long INF = 1e18;
        vector<vector<long long>> dp(k + 1, vector<long long>(n + 1, INF));

        for (int i = 0; i <= n; ++i) {
            dp[0][i] = 0;
        }

        for (int j = 1; j <= k; ++j) {
            for (int i = j * x; i <= n; ++i) {
                // Option 1: Skip element at i - 1
                dp[j][i] = dp[j][i - 1];
                
                // Option 2: Pick subarray ending at i - 1 (starts at i - x)
                long long pick = dp[j - 1][i - x] + cost[i - x];
                dp[j][i] = min(dp[j][i], pick);
            }
        }

        return dp[k][n];
    }
};