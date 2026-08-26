class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();

        int[] pos = new int[n];
        int count = 0;

        // Store positions of all 1s
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                pos[count++] = i;
            }
        }

        // Not enough 1s
        if (count < k) {
            return "";
        }

        int minLen = Integer.MAX_VALUE;
        String ans = "";

        // Consider every group of k consecutive 1s
        for (int i = 0; i + k - 1 < count; i++) {

            int left = pos[i];
            int right = pos[i + k - 1];

            int len = right - left + 1;

            String curr = s.substring(left, right + 1);

            if (len < minLen) {
                minLen = len;
                ans = curr;
            } else if (len == minLen && curr.compareTo(ans) < 0) {
                ans = curr;
            }
        }

        return ans;
    }
}