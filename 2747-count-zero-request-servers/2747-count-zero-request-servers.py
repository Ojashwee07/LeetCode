class Solution:
    def countServers(self, n: int, logs: List[List[int]], x: int, queries: List[int]) -> List[int]:
        # Sort logs by time
        logs.sort(key=lambda item: item[1])
        
        # Pair queries with their original indices and sort by query time
        indexed_queries = sorted(enumerate(queries), key=lambda item: item[1])
        
        ans = [0] * len(queries)
        server_count = [0] * (n + 1)
        active_servers = 0
        
        left = 0
        right = 0
        num_logs = len(logs)
        
        for original_index, q in indexed_queries:
            window_start = q - x
            window_end = q
            
            # Expand window to the right (include logs <= window_end)
            while right < num_logs and logs[right][1] <= window_end:
                server_id = logs[right][0]
                if server_count[server_id] == 0:
                    active_servers += 1
                server_count[server_id] += 1
                right += 1
                
            # Shrink window from the left (exclude logs < window_start)
            while left < right and logs[left][1] < window_start:
                server_id = logs[left][0]
                server_count[server_id] -= 1
                if server_count[server_id] == 0:
                    active_servers -= 1
                left += 1
                
            # Servers with zero requests = total servers - active servers
            ans[original_index] = n - active_servers
            
        return ans