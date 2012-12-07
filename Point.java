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
	
	public final Comparator<Point> POLAR_ORDER = new POLorder();

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

	//is abc a counterclockwise turn?
	public static int CCW(Point a, Point b, Point c) {
		
		double ccw;

		ccw = ((b.x - a.x)*(c.y -a.y)) - ((b.y -a.y)*(c.x -a.x));

		//counterclockwise
		if (ccw > 0) {
			return -1;
		}
		//clockwise
		if (ccw < 0) {
			return 1;
		}
		//collinear
		return 0;
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

	private class POLorder implements Comparator<Point> {

		public int compare(Point p1, Point p2) {

				double dp1y = p1.y - y;
				double dp1x = p1.x - x;
				double dp2y = p2.y - y;
				double dp2x = p2.x - x;

				//p1 is above P; p2 in below P; p1 makes smaller polar angel
				if (dp1y >= 0  && dp2y < 0) {
					return -1;
				}
				//p1 is below P; p2 is above P; p1 makes larger polar angel
				if (dp1y < 0 && dp2y >= 0) {
					return 1;
				}
				//points are colinear and horizontal
				if (dp1y == 0 && dp2y == 0) {
					if (dp1x >= 0 && dp2x < 0) {
						return -1;
					}
					else if (dp1x < 0 && dp2x >= 0) {
						return 1;
					}
					else {
						return 0;
					}
				}

				return CCW(Point.this, p1, p2);
		}
	}

	// convert to string
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
