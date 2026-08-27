class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Keep track of the longest prefix equal to target
        int[] used = freq.clone();

        for (int i = 0; i < n; i++) {
            int x = target.charAt(i) - 'a';

            if (used[x] == 0) {
                break;
            }

            used[x]--;
        }

        // Try to change from right to left
        for (int i = n - 1; i >= 0; i--) {

            int[] cnt = freq.clone();

            // Build prefix equal to target[0...i-1]
            boolean possible = true;

            for (int j = 0; j < i; j++) {
                int x = target.charAt(j) - 'a';

                if (cnt[x] == 0) {
                    possible = false;
                    break;
                }

                cnt[x]--;
            }

            if (!possible) {
                continue;
            }

            int targetChar = target.charAt(i) - 'a';

            // Find the smallest available character
            // greater than target[i]
            for (int c = targetChar + 1; c < 26; c++) {

                if (cnt[c] > 0) {
                    StringBuilder ans = new StringBuilder();

                    // Same prefix
                    for (int j = 0; j < i; j++) {
                        ans.append(target.charAt(j));
                    }

                    // First greater character
                    ans.append((char) ('a' + c));
                    cnt[c]--;

                    // Smallest possible suffix
                    for (int x = 0; x < 26; x++) {
                        while (cnt[x] > 0) {
                            ans.append((char) ('a' + x));
                            cnt[x]--;
                        }
                    }

                    return ans.toString();
                }
            }
        }

        return "";
    }
}
