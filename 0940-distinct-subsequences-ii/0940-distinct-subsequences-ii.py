class Solution:
    def distinctSubseqII(self, s: str) -> int:
        MOD = 10**9 + 7

        # dp = number of distinct subsequences including empty
        dp = 1

        # last[c] = value of dp before previous occurrence of c
        last = [0] * 26

        for ch in s:
            idx = ord(ch) - ord('a')

            new_dp = (2 * dp - last[idx]) % MOD

            # Store old dp for future duplicate removal
            last[idx] = dp

            dp = new_dp

        # Remove empty subsequence
        return (dp - 1) % MOD