import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    private int[] newParent;
    private int[] last;
    private int[] ans;
    private List<Integer>[] adj;
    private List<Integer>[] newAdj;
    private int[] parent;
    private String s;

    @SuppressWarnings("unchecked")
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        this.parent = parent;
        this.s = s;

        adj = new ArrayList[n];
        newAdj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
            newAdj[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            adj[parent[i]].add(i);
        }

        newParent = new int[n];
        Arrays.fill(newParent, -1);

        last = new int[26];
        Arrays.fill(last, -1);

        dfsReassign(0);

        for (int i = 1; i < n; i++) {
            newAdj[newParent[i]].add(i);
        }

        ans = new int[n];
        dfsSize(0);
        return ans;
    }

    private void dfsReassign(int u) {
        int c = s.charAt(u) - 'a';
        if (u > 0) {
            newParent[u] = (last[c] != -1) ? last[c] : parent[u];
        }

        int prev = last[c];
        last[c] = u;

        for (int v : adj[u]) {
            dfsReassign(v);
        }

        last[c] = prev;
    }

    private int dfsSize(int u) {
        int total = 1;
        for (int v : newAdj[u]) {
            total += dfsSize(v);
        }
        ans[u] = total;
        return total;
    }
}