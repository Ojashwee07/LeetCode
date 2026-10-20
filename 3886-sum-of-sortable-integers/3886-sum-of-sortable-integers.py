from typing import List


class Solution:
    def sortableIntegers(self, nums: List[int]) -> int:
        n = len(nums)

        # pre[i] = number of positions t < i with nums[t] > nums[t+1]
        pre = [0] * n
        for i in range(1, n):
            pre[i] = pre[i - 1] + (nums[i - 1] > nums[i])

        total = 0
        for k in range(1, n + 1):
            if n % k:
                continue

            ok = True
            prev_max = None
            for s in range(0, n, k):
                # Descents inside the block, plus the cyclic wrap-around descent
                desc = pre[s + k - 1] - pre[s] + (nums[s + k - 1] > nums[s])
                if desc > 1:
                    ok = False
                    break

                block = nums[s:s + k]
                bmin, bmax = min(block), max(block)
                if prev_max is not None and prev_max > bmin:
                    ok = False
                    break
                prev_max = bmax

            if ok:
                total += k

        return total

    # Alias for the name used in the problem statement
    sumOfSortableIntegers = sortableIntegers