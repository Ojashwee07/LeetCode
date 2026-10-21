class Solution:
    def minOperations(self, s: str) -> int:
        n = len(s)
        sorted_s = sorted(s)
        if list(s) == sorted_s:
            return 0
        if n == 2:
            return -1
        
        l = 0
        while s[l] == sorted_s[l]:
            l += 1
        r = n - 1
        while s[r] == sorted_s[r]:
            r -= 1
        
        if not (l == 0 and r == n - 1):
            if sorted(s[l:r+1]) == sorted_s[l:r+1]:
                return 1
        
        min_first_part = min(s[:-1])
        max_last_part = max(s[1:])
        
        if min_first_part <= s[-1] or max_last_part >= s[0]:
            return 2
        
        return 3