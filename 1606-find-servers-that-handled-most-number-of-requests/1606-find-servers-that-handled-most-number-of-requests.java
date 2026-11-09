import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        // Min-heap to store busy servers as [freeTime, serverId]
        PriorityQueue<int[]> busyServers = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        
        // TreeSet to maintain currently available servers in sorted order
        TreeSet<Integer> availableServers = new TreeSet<>();
        for (int i = 0; i < k; i++) {
            availableServers.add(i);
        }
        
        int[] requestsHandled = new int[k];
        int n = arrival.length;
        
        for (int i = 0; i < n; i++) {
            int currTime = arrival[i];
            int duration = load[i];
            
            // 1. Free up servers that have finished their tasks by the current arrival time
            while (!busyServers.isEmpty() && busyServers.peek()[0] <= currTime) {
                int[] freed = busyServers.poll();
                availableServers.add(freed[1]);
            }
            
            // 2. If all servers are busy, drop the request
            if (availableServers.isEmpty()) {
                continue;
            }
            
            // 3. Find the target server index and query the TreeSet
            int target = i % k;
            Integer serverId = availableServers.ceiling(target);
            
            // If no server >= target is available, wrap around to the beginning
            if (serverId == null) {
                serverId = availableServers.ceiling(0);
            }
            
            // 4. Assign the request to the chosen server
            availableServers.remove(serverId);
            busyServers.offer(new int[] { currTime + duration, serverId });
            requestsHandled[serverId]++;
        }
        
        // Find the maximum number of requests handled by any server
        int maxRequests = 0;
        for (int count : requestsHandled) {
            maxRequests = Math.max(maxRequests, count);
        }
        
        // Collect all servers that handled the maximum number of requests
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (requestsHandled[i] == maxRequests) {
                result.add(i);
            }
        }
        
        return result;
    }
}