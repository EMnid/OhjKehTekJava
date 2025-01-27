public class Point {
    private double x;
    private double y;

    private static int count = 0;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;

        count++;
    }

    public static int getCount() {
        return Point.count;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return String.format("(%.1f; %.1f)",this.x, this.y);
    }

    public double distance(Point other) {
        return Math.hypot(this.x - other.x, this.y - other.y);
    }
}

