import java.util.Map;
import java.util.TreeMap;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        long remBudget = budget;
        long minPrice = Long.MAX_VALUE;
        int maxFactor = 0;

        for (int[] item : items) {
            maxFactor = Math.max(maxFactor, item[0]);
            minPrice = Math.min(minPrice, item[1]);
        }

        int[] freq = new int[maxFactor + 1];
        for (int[] item : items) {
            freq[item[0]]++;
        }

        // multiplesCount[f] = total items with factor divisible by f (including f itself)
        int[] multiplesCount = new int[maxFactor + 1];
        for (int f = 1; f <= maxFactor; f++) {
            if (freq[f] > 0) {
                for (int mult = f; mult <= maxFactor; mult += f) {
                    multiplesCount[f] += freq[mult];
                }
            }
        }

        // Group available 2-item copies by price (only profitable if price <= 2 * minPrice)
        TreeMap<Long, Long> twoItemCopiesByPrice = new TreeMap<>();
        long limitPrice = 2L * minPrice;

        for (int[] item : items) {
            int f = item[0];
            long price = item[1];
            int bonus = multiplesCount[f] - 1;
            if (bonus > 0 && price <= limitPrice) {
                twoItemCopiesByPrice.put(price, twoItemCopiesByPrice.getOrDefault(price, 0L) + bonus);
            }
        }

        long totalItems = 0;
        for (Map.Entry<Long, Long> entry : twoItemCopiesByPrice.entrySet()) {
            long price = entry.getKey();
            long available = entry.getValue();
            long take = Math.min(available, remBudget / price);
            totalItems += 2L * take;
            remBudget -= take * price;
        }

        totalItems += remBudget / minPrice;
        return (int) totalItems;
    }
}