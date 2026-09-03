class Solution {
public:
    bool uniformArray(vector<int>& nums1) {
        int minOdd = INT_MAX;

        // Find smallest odd number
        for (int x : nums1) {
            if (x % 2 == 1) {
                minOdd = min(minOdd, x);
            }
        }

        // All numbers are even
        if (minOdd == INT_MAX) {
            return true;
        }

        // Every even number must have
        // a smaller odd number to subtract
        for (int x : nums1) {
            if (x % 2 == 0 && x <= minOdd) {
                return false;
            }
        }

        return true;
    }
};