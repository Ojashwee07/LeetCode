import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {

        Map<Integer, Integer> map = new HashMap<>();

        // Only seats 2 to 9 matter
        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            if (col >= 2 && col <= 9) {
                map.put(row, map.getOrDefault(row, 0) | (1 << col));
            }
        }

        // Rows without any relevant reservation can have 2 groups
        long ans = 2L * (n - map.size());

        for (int mask : map.values()) {

            // 2,3,4,5
            boolean left = (mask & ((1 << 2) | (1 << 3) |
                                    (1 << 4) | (1 << 5))) == 0;

            // 6,7,8,9
            boolean right = (mask & ((1 << 6) | (1 << 7) |
                                     (1 << 8) | (1 << 9))) == 0;

            // 4,5,6,7
            boolean middle = (mask & ((1 << 4) | (1 << 5) |
                                      (1 << 6) | (1 << 7))) == 0;

            if (left && right) {
                ans += 2;
            } else if (left || middle || right) {
                ans += 1;
            }
        }

        return (int) ans;
    }
}