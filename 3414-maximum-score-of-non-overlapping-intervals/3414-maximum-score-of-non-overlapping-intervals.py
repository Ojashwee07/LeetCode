from bisect import bisect_right

class Solution:
    def maximumWeight(self, intervals):
        n = len(intervals)

        # (left, right, weight, original_index)
        arr = [
            (l, r, w, i)
            for i, (l, r, w) in enumerate(intervals)
        ]

        # Sort by left endpoint
        arr.sort()

        starts = [x[0] for x in arr]

        # Find next non-overlapping interval
        nxt = [0] * n

        for i in range(n):
            nxt[i] = bisect_right(starts, arr[i][1])

        # dp[i][k] = (maximum score, lexicographically smallest indices)
        dp = [[(0, []) for _ in range(5)] for _ in range(n + 1)]

        for i in range(n - 1, -1, -1):
            for k in range(1, 5):

                # 1. Skip current interval
                skip_score, skip_indices = dp[i + 1][k]

                # 2. Take current interval
                next_score, next_indices = dp[nxt[i]][k - 1]

                take_score = arr[i][2] + next_score

                # IMPORTANT:
                # Keep selected original indices sorted
                take_indices = sorted(
                    [arr[i][3]] + next_indices
                )

                if take_score > skip_score:
                    dp[i][k] = (take_score, take_indices)

                elif take_score < skip_score:
                    dp[i][k] = (skip_score, skip_indices)

                else:
                    # Same score -> lexicographically smaller indices
                    if take_indices < skip_indices:
                        dp[i][k] = (take_score, take_indices)
                    else:
                        dp[i][k] = (skip_score, skip_indices)

        return dp[0][4][1]