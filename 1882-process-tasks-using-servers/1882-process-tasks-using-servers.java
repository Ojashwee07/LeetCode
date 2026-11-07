import java.util.PriorityQueue;

class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {
        int n = servers.length;
        int m = tasks.length;
        int[] ans = new int[m];

        // Free servers: ordered by (weight, index)
        // Element: int[]{weight, index}
        PriorityQueue<int[]> freeServers = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return Integer.compare(a[0], b[0]);
            return Integer.compare(a[1], b[1]);
        });

        // Busy servers: ordered by (freeTime, weight, index)
        // Element: long[]{freeTime, weight, index}
        PriorityQueue<long[]> busyServers = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) return Long.compare(a[0], b[0]);
            if (a[1] != b[1]) return Long.compare(a[1], b[1]);
            return Long.compare(a[2], b[2]);
        });

        // Initialize free servers at t = 0
        for (int i = 0; i < n; i++) {
            freeServers.offer(new int[]{servers[i], i});
        }

        long time = 0;

        for (int j = 0; j < m; j++) {
            // Current time must be at least the arrival time of task j
            time = Math.max(time, j);

            // If no free server, jump to the time when the next busy server finishes
            if (freeServers.isEmpty()) {
                time = Math.max(time, busyServers.peek()[0]);
            }

            // Release all servers that become free at or before 'time'
            while (!busyServers.isEmpty() && busyServers.peek()[0] <= time) {
                long[] server = busyServers.poll();
                freeServers.offer(new int[]{(int) server[1], (int) server[2]});
            }

            // Pick the best available server
            int[] bestServer = freeServers.poll();
            ans[j] = bestServer[1];

            // Add server to busy queue with completion time = time + tasks[j]
            busyServers.offer(new long[]{time + tasks[j], bestServer[0], bestServer[1]});
        }

        return ans;
    }
}