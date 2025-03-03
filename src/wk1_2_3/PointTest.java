package wk1_2_3;

public class PointTest {
    public static void main(String[] args) {

        System.out.println("Number of points created: " + Point.getCount());
        Point a = new Point(-2, 3);
        Point b = new Point(4, -7);
        System.out.println("Number of points created: " + Point.getCount());

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        double d = a.distance(b);
        System.out.println("Distance between a and b: " + String.format("%.1f", d));

        Point c = new Point(10, a.getY());
        System.out.println("c = " + c);

        Point g = new Point(0, 0);
        System.out.println("g = " + g);

        System.out.println("Number of points created: " + Point.getCount());

        System.out.println(Math.sin(Math.toRadians(60.0)));
    }
}
