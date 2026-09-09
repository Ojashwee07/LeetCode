class Solution:
    def countCommas(self, n: int) -> int:
        ans = 0
        start = 1000

        while start <= n:
            # Numbers from start to end have the same number of commas
            end = min(n, start * 1000 - 1)

            commas = len(str(start)) // 3
            ans += (end - start + 1) * commas

            start *= 1000

        return ans