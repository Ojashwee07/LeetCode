import java.math.BigInteger;

class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int maxLen = 0;

        for (int i = 0; i < n; i++) {
            long g = nums[i];
            long l = nums[i];
            BigInteger prod = BigInteger.valueOf(nums[i]);

            for (int j = i; j < n; j++) {
                if (j > i) {
                    long val = nums[j];
                    g = gcd(g, val);
                    l = lcm(l, val);
                    prod = prod.multiply(BigInteger.valueOf(val));
                }

                BigInteger rhs = BigInteger.valueOf(l).multiply(BigInteger.valueOf(g));
                if (prod.equals(rhs)) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }

        return maxLen;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
            long tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    private long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }
}