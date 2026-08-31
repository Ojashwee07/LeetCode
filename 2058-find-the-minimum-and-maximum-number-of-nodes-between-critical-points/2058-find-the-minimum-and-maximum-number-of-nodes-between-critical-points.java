class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        
        int first = -1;
        int last = -1;
        int minDist = Integer.MAX_VALUE;
        
        int index = 1;
        
        ListNode prev = head;
        ListNode curr = head.next;
        
        while (curr.next != null) {
            
            ListNode next = curr.next;
            
            // Check if current node is a critical point
            if ((curr.val > prev.val && curr.val > next.val) ||
                (curr.val < prev.val && curr.val < next.val)) {
                
                // First critical point
                if (first == -1) {
                    first = index;
                } else {
                    // Distance from previous critical point
                    minDist = Math.min(minDist, index - last);
                }
                
                // Update last critical point
                last = index;
            }
            
            prev = curr;
            curr = next;
            index++;
        }
        
        // Fewer than 2 critical points
        if (first == -1 || first == last) {
            return new int[]{-1, -1};
        }
        
        int maxDist = last - first;
        
        return new int[]{minDist, maxDist};
    }
}