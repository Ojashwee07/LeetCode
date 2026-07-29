import java.math.BigInteger;

class Solution {

    static final int LIMIT = 1_000_000;

    public String smallestPalindrome(String s, int k) {

        int[] freq = new int[26];

        for (char c : s.toCharArray())
            freq[c - 'a']++;

        String mid = "";
        int[] half = new int[26];
        int halfLen = 0;

        for (int i = 0; i < 26; i++) {
            if ((freq[i] & 1) == 1)
                mid = String.valueOf((char) ('a' + i));

            half[i] = freq[i] / 2;
            halfLen += half[i];
        }

        StringBuilder left = new StringBuilder();

        for (int pos = 0; pos < halfLen; pos++) {

            for (int c = 0; c < 26; c++) {

                if (half[c] == 0)
                    continue;

                half[c]--;

                int ways = countWays(half);

                if (ways >= k) {
                    left.append((char) ('a' + c));
                    break;
                }

                k -= ways;
                half[c]++;
            }
        }

        StringBuilder ans = new StringBuilder(left);
        ans.append(mid);

        for (int i = left.length() - 1; i >= 0; i--)
            ans.append(left.charAt(i));

        if (k > 1) return "";

        return ans.toString();
    }

    private int countWays(int[] cnt) {

        int total = 0;
        for (int x : cnt) total += x;

        BigInteger res = BigInteger.ONE;

        int remain = total;

        for (int x : cnt) {

            if (x == 0) continue;

            res = res.multiply(binomial(remain, x));

            if (res.compareTo(BigInteger.valueOf(LIMIT)) > 0)
                return LIMIT + 1;

            remain -= x;
        }

        if (res.compareTo(BigInteger.valueOf(LIMIT)) > 0)
            return LIMIT + 1;

        return res.intValue();
    }

    private BigInteger binomial(int n, int r) {

        if (r > n - r)
            r = n - r;

        BigInteger ans = BigInteger.ONE;

        for (int i = 1; i <= r; i++) {
            ans = ans.multiply(BigInteger.valueOf(n - r + i));
            ans = ans.divide(BigInteger.valueOf(i));

            if (ans.compareTo(BigInteger.valueOf(LIMIT)) > 0)
                return BigInteger.valueOf(LIMIT + 1);
        }

        return ans;
    }
}