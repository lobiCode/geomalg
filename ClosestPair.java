/***************************************************************************
 * Name: Uros Slana
 * Email: urossla(at)gmail.com
 *
 * Compilation: javac ClosestPair.java
 * 
 * Dependencies: Point.java
 * 
 * Description: Algorithm finds closest pair of points in O(n lg n) 
 *
 * % java ClosestPair set_of_points.txt NUMBER_OF_POINTS
 * 0.1 0.5
 * 1.5 9.3
 * 0.4 93.1
 * 112 39.3
 * 22  39.0
 * 19  0.45
 * ...
 *************************************************************************/

import java.util.Arrays;
import java.util.Scanner;
import java.lang.System;
import java.io.File;
import java.io.FileNotFoundException;

public class ClosestPair {

	private class ClosestDist {
		private Point p1;
		private Point p2;
		private double dist;

		public ClosestDist(Point p1, Point p2, double dist) {
			this.p1 = p1;
			this.p2 = p2;
			this.dist = dist;
		}
	}

	private Point[] aux;
	private Point[] sortX;
	private ClosestDist champion;
	private final int bfThreshold = 50;

	public ClosestPair(Point[] points) {

		int N = points.length;
		sortX = new Point[N];
		aux = new Point[N];
		
		for (int i = 0; i < N; i++) {
			sortX[i] = points[i];
		}

		Arrays.sort(sortX);

		for (int i = 0; i < N-1; i++) {
			if (sortX[i].compareTo(sortX[i+1]) == 0) {
				champion = new ClosestDist(sortX[i], sortX[i+1], 0.0);
				return;
			}
		}

		champion = FindClosestPair(0, N-1);
	}
	
	private ClosestDist FindClosestPair(int lo, int hi) {
	
		if (hi-lo <= bfThreshold) {
			return BruteDist(lo, hi);
		}

		int iYS = 0;
		int mid = lo + (hi-lo)/2;
		ClosestDist leftPair;
		ClosestDist rightPair;
		ClosestDist champPair;

		//save mid point before sort by Y and merge
		Point midle = sortX[mid];

		leftPair = FindClosestPair(lo, mid);
		rightPair = FindClosestPair(mid+1, hi);

		champPair = leftPair.dist < rightPair.dist ? leftPair : rightPair;
		
		merge(lo, mid, hi);
		
		//array sort Y whit all points not in the 2*chapmDist.dist-wide verti. strip removed
		Point[] sortYS = new Point[hi-lo+1];	

		//build sortYS
		int i = lo;
		while (i <= hi) {
			if ( Math.abs(midle.x - sortX[i].x) < champPair.dist) {
				sortYS[iYS++] = sortX[i];
			}
			i++;
		}

		for (i = 0; i < iYS; i++) {
			for (int j = i+1;  j < iYS
					&& (Math.abs(sortYS[j].y - sortYS[i].y) < champPair.dist); j++) {
				if (sortYS[i].distanceTo(sortYS[j]) < champPair.dist) {
					champPair = new ClosestDist(sortYS[i], sortYS[j], sortYS[i].distanceTo(sortYS[j]));
				}
			}
		}

		return champPair;
	}

	private ClosestDist BruteDist(int lo,  int hi) {
		
		ClosestDist champPair = null;
		double min = Double.POSITIVE_INFINITY;

		Arrays.sort(sortX, lo, hi+1, Point.Y_ORDER);

		for (int i=lo; i <= hi; i++) {
			for (int j=i+1; j <= hi; j++) {
				if (sortX[i].distanceTo(sortX[j]) < min) {
					min = sortX[i].distanceTo(sortX[j]);
					champPair = new ClosestDist(sortX[i], sortX[j], min);
				}
			}
		}

		return champPair;
	}

	private void merge(int lo, int mid, int hi) {
		int i= lo;
		int j= mid+1;

		for (int k = lo; k <= hi; k++) {
			aux[k] = sortX[k];
		}

		for (int k = lo; k <= hi; k++) {
			if (i > mid) {
				sortX[k] = aux[j++];
			}
			else if (j > hi) {
				sortX[k] = aux[i++];
			}
			else if (Point.Y_ORDER.compare(aux[j], aux[i]) == -1) {
				sortX[k] = aux[j++];
			}
			else {
				sortX[k] = aux[i++];
			}
		}
	}

	
	public static void main(String[] args) {

		// java ClosestPair arg1 arg2
		if (args.length < 2) {
			System.out.println("Incorrect usage.");
			return;
		}
		
		int i = 0;
		int N = Integer.parseInt(args[1]);
		File fileP = new File(args[0]);
		Point[] points = new Point[N];
		Scanner scanPoint;
		ClosestPair closestPair; 

		try {
			scanPoint = new Scanner(fileP);
			while(scanPoint.hasNextDouble()) {
				double x = scanPoint.nextDouble();
				double y = scanPoint.nextDouble();
				points[i++] = new Point(x, y); 
			}
		}catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		closestPair = new ClosestPair(points);
		System.out.println("Dist.=" + closestPair.champion.dist
				+ " P1= " + closestPair.champion.p1 + " P2= " +  closestPair.champion.p2 );
	}
}
