class Solution {
    public boolean checkOverlap(int radius, int xCenter, int yCenter,
                                int x1, int y1, int x2, int y2) {

        // Find closest x-coordinate inside rectangle
        int closestX = Math.max(x1, Math.min(xCenter, x2));

        // Find closest y-coordinate inside rectangle
        int closestY = Math.max(y1, Math.min(yCenter, y2));

        // Distance between circle center and closest point
        int dx = xCenter - closestX;
        int dy = yCenter - closestY;

        // Check if the point lies inside or on the circle
        return dx * dx + dy * dy <= radius * radius;
    }
}