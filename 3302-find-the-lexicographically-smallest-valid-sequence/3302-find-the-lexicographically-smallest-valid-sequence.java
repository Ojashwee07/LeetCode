class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        int[] ans = new int[m];

        // last[j] = rightmost index in word1
        // where word2[j] can be matched while
        // still matching word2[j+1 ...] afterwards.
        int[] last = new int[m];

        for (int i = 0; i < m; i++) {
            last[i] = -1;
        }

        // Build the suffix information from right to left.
        int i = n - 1;
        int j = m - 1;

        while (i >= 0 && j >= 0) {
            if (word1.charAt(i) == word2.charAt(j)) {
                last[j] = i;
                j--;
            }
            i--;
        }

        // We are allowed to use at most one mismatch.
        boolean canMismatch = true;

        j = 0;

        // Greedily choose the smallest possible index.
        for (i = 0; i < n && j < m; i++) {

            // Exact match
            if (word1.charAt(i) == word2.charAt(j)) {
                ans[j] = i;
                j++;
            }

            // Use the one allowed mismatch
            else if (canMismatch) {

                /*
                 * If this is the last character of word2,
                 * we can always use the mismatch.
                 *
                 * Otherwise, we need to make sure that
                 * word2[j + 1 ...] can be matched after i.
                 *
                 * last[j + 1] is the rightmost possible
                 * starting position for the remaining suffix.
                 *
                 * i < last[j + 1] means there is enough room
                 * after i to match the rest.
                 */
                if (j == m - 1 || i < last[j + 1]) {
                    ans[j] = i;
                    j++;
                    canMismatch = false;
                }
            }
        }

        // Could not match all characters
        if (j != m) {
            return new int[0];
        }

        return ans;
    }
}