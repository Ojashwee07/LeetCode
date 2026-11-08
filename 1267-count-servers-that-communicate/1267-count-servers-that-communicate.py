from typing import List

class Solution:
    def countServers(self, grid: List[List[int]]) -> int:
        m, n = len(grid), len(grid[0])
        
        row_counts = [0] * m
        col_counts = [0] * n
        
        # Step 1: Count servers in each row and column
        for r in range(m):
            for c in range(n):
                if grid[r][c] == 1:
                    row_counts[r] += 1
                    col_counts[c] += 1
                    
        communicating_servers = 0
        
        # Step 2: Check which servers can communicate
        for r in range(m):
            for c in range(n):
                if grid[r][c] == 1:
                    if row_counts[r] > 1 or col_counts[c] > 1:
                        communicating_servers += 1
                        
        return communicating_servers