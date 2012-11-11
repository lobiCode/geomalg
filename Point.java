/*************************************************************************
 * Name: Uros Slana
 * Email: urossla(at)gmail.com
 *
 * Compilation: javac Point.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

public class Point implements Comparable<Point> {
	
	public final double x;
	public final double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	// comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point p) {
		//TODO try to write this as a oneliner
		if (y < p.y) {
			return -1;
		}
		if (y > p.y) {
			return 1;
		}
		if (x < p.x) {
			return -1;
		}
		if (x > p.x) {
			return 1;
		}
		return 0;
	}

	//returns Euclidean distance of this and p
	public double distanceTo(Point p) {
		double dx = x - p.x;
		double dy = y - p.y;

		return Math.sqrt((dx*dx) + (dy*dy));
	}
	
	// convert to string
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
