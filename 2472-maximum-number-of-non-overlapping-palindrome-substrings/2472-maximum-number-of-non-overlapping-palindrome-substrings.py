class Solution:
    def maxPalindromes(self, s: str, k: int) -> int:
        n = len(s)

        # pal[i][j] = whether s[i..j] is a palindrome
        pal = [[False] * n for _ in range(n)]

        # Build palindrome table
        for length in range(1, n + 1):
            for i in range(n - length + 1):
                j = i + length - 1

                if s[i] == s[j] and (length <= 2 or pal[i + 1][j - 1]):
                    pal[i][j] = True

        # dp[i] = maximum answer using first i characters
        dp = [0] * (n + 1)

        for i in range(n):
            # Don't take a substring starting at i
            dp[i + 1] = max(dp[i + 1], dp[i])

            # Take a palindrome starting at i
            for j in range(i + k - 1, n):
                if pal[i][j]:
                    dp[j + 1] = max(dp[j + 1], dp[i] + 1)

        return dp[n]