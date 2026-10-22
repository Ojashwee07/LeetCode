from math import inf

class Solution:
    def minOperations(self, nums: list[int]) -> int:
        n = len(nums)
        if n == 1:
            return 0

        pos = nums.index(0)
        ans = inf

        # Forward cyclic order: sorting needs only rotations (or two reverses plus rotations).
        if all(nums[(i + 1) % n] == (nums[i] + 1) % n for i in range(n)):
            ans = min(ans, min(pos, n - pos + 2))

        # Backward cyclic order: sorting needs an odd number of reverses.
        if all(nums[(i + 1) % n] == (nums[i] - 1) % n for i in range(n)):
            ans = min(ans, 1 + min(n - 1 - pos, pos + 1))

        return ans if ans != inf else -1