/*************************************************************************
 * Name: Uros Slana
 * Email: urossla(at)gmail.com
 *
 * Compilation: javac Point.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {
	
	public static final Comparator<Point> Y_ORDER = new YOrder();

	public final double x;
	public final double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	// comparing x-coordinates and breaking ties by y-coordinates
	public int compareTo(Point p) {

		if (x < p.x) {
			return -1;
		}
		if (x > p.x) {
			return 1;
		}
		if (y < p.y) {
			return -1;
		}
		if (y > p.y) {
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
	
	private static class YOrder implements Comparator<Point> {

		public int compare(Point p1, Point p2) {

			if (p1.y < p2.y) {
				return -1;
			}
			if (p1.y > p2.y) {
				return 1;
			}
			return 0;
		}
	}

	// convert to string
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
