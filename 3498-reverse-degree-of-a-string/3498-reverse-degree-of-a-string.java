class Solution {
    public int reverseDegree(String s) {
        int sum = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            // Reverse alphabet value
            int value = 'z' - ch + 1;

            // Position is 1-indexed
            int position = i + 1;

            sum += value * position;
        }

        return sum;
    }
}