class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
        long c1 = cost1, c2 = cost2, cb = costBoth;
        long n1 = need1, n2 = need2;
        
        long ans = n1 * c1 + n2 * c2;
        
        long common = Math.min(n1, n2);
        long rem1 = n1 - common;
        long rem2 = n2 - common;
        
        long costCombo = common * cb 
                            + rem1 * Math.min(c1, cb) 
                            + rem2 * Math.min(c2, cb);
        
        long costAllBoth = Math.max(n1, n2) * cb;
        
        return Math.min(ans, Math.min(costCombo, costAllBoth));
    }
}