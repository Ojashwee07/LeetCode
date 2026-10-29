from typing import List


class Solution:
    def palindromePath(self, n: int, edges: List[List[int]], s: str, queries: List[str]) -> List[bool]:
        adj = [[] for _ in range(n)]
        for u, v in edges:
            adj[u].append(v)
            adj[v].append(u)

        # Root the tree at 0: parent, depth, and Euler-tour interval [tin, tout) per subtree.
        parent = [-1] * n
        depth = [0] * n
        tin = [0] * n
        tout = [0] * n
        timer = 0
        stack = [(0, False)]
        visited = [False] * n
        visited[0] = True
        while stack:
            node, leaving = stack.pop()
            if leaving:
                tout[node] = timer
                continue
            tin[node] = timer
            timer += 1
            stack.append((node, True))
            for w in adj[node]:
                if not visited[w]:
                    visited[w] = True
                    parent[w] = node
                    depth[w] = depth[node] + 1
                    stack.append((w, False))

        # Binary lifting for LCA.
        LOG = max(1, n.bit_length())
        up = [[0] * n for _ in range(LOG)]
        for v in range(n):
            up[0][v] = parent[v] if parent[v] != -1 else v
        for k in range(1, LOG):
            prev, cur = up[k - 1], up[k]
            for v in range(n):
                cur[v] = prev[prev[v]]

        def lca(a: int, b: int) -> int:
            if depth[a] < depth[b]:
                a, b = b, a
            diff = depth[a] - depth[b]
            k = 0
            while diff:
                if diff & 1:
                    a = up[k][a]
                diff >>= 1
                k += 1
            if a == b:
                return a
            for k in range(LOG - 1, -1, -1):
                if up[k][a] != up[k][b]:
                    a = up[k][a]
                    b = up[k][b]
            return up[0][a]

        # Fenwick tree over Euler positions supporting range-XOR and point query.
        # A node's value = XOR of letter bits over its root-to-node path.
        bit = [0] * (n + 1)

        def fen_update(i: int, d: int) -> None:
            i += 1
            while i <= n:
                bit[i] ^= d
                i += i & -i

        def fen_query(i: int) -> int:
            i += 1
            res = 0
            while i > 0:
                res ^= bit[i]
                i -= i & -i
            return res

        def subtree_xor(v: int, d: int) -> None:
            fen_update(tin[v], d)
            fen_update(tout[v], d)

        cur = list(s)
        for v in range(n):
            subtree_xor(v, 1 << (ord(cur[v]) - 97))

        ans: List[bool] = []
        for q in queries:
            t, a, b = q.split()
            if t == "update":
                x = int(a)
                old = 1 << (ord(cur[x]) - 97)
                new = 1 << (ord(b) - 97)
                if old != new:
                    subtree_xor(x, old ^ new)
                cur[x] = b
            else:
                u, v = int(a), int(b)
                l = lca(u, v)
                m = fen_query(tin[u]) ^ fen_query(tin[v]) ^ (1 << (ord(cur[l]) - 97))
                ans.append((m & (m - 1)) == 0)
        return ans