from collections import deque
from typing import List

class Solution:
    def findSafeWalk(self, grid: List[List[int]], health: int) -> bool:
        m, n = len(grid), len(grid[0])

        INF = float('inf')
        dist = [[INF] * n for _ in range(m)]

        dq = deque()

        dist[0][0] = grid[0][0]
        dq.append((0, 0))

        dirs = [(1,0), (-1,0), (0,1), (0,-1)]

        while dq:
            r, c = dq.popleft()

            for dr, dc in dirs:
                nr, nc = r + dr, c + dc

                if 0 <= nr < m and 0 <= nc < n:
                    nd = dist[r][c] + grid[nr][nc]

                    if nd < dist[nr][nc]:
                        dist[nr][nc] = nd
                        if grid[nr][nc] == 0:
                            dq.appendleft((nr, nc))
                        else:
                            dq.append((nr, nc))

        return dist[m-1][n-1] < health