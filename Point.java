/*************************************************************************
 * Name: Uros Slana
 * Email: urossla@gmail.com
 *
 * Compilation:  javac Point.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

public class Point {
	
	private final double x;
	private final double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double x() {
		return x;
	}
	
	public double y() {
		return y;
	}

	//retrun Euclidean distance of this and p
	public double distanceTo(Point p) {
		double dx = this.x - p.x;
		double dy = this.y - p.y;

		return Math.sqrt((dx*dx) + (dy*dy));
	}
}
