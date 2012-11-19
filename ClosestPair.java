/*************************************************************************
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

	private final int bfTreshlod = 16;
	private ClosestDist champion; 

	public ClosestPair(Point[] points) {

		int N = points.length;
		Point[] xOrder = new Point[N];
		Point[] yOrder = new Point[N];

		for (int i = 0; i < N; i++) {
			xOrder[i] = points[i];
			yOrder[i] = points[i];
		}

		Arrays.sort(xOrder);

		for (int i = 0; i < N-1; i++) {
			if (xOrder[i].compareTo(xOrder[i+1]) == 0) {
				champion = new ClosestDist(xOrder[i], xOrder[i+1], 0.0);
				return;
			}
		}

		Arrays.sort(yOrder, new Point(0, 0).Y_ORDER);

		champion = FindClosestPair(xOrder, yOrder);
	}
	
	private ClosestDist FindClosestPair(Point[] sortX, Point[] sortY) {
		
		if (sortX.length <= bfTreshlod) {
			return BruteDist(sortX);
		}

		int lY = 0;
		int rY = 0;
		int totalYS = 0;
		int xLength = sortX.length;
		//length of left part of array after split
		int lengthL = xLength/2;
		//length of right part of array after split
		int lengthR = xLength - lengthL;							
		ClosestDist leftPair;
		ClosestDist rightPair;
		ClosestDist champPair;
		Point[] sortXL = new Point[lengthL];
		Point[] sortXR = new Point[lengthR];
		Point[] sortYL = new Point[lengthL];
		Point[] sortYR = new Point[lengthR];
		//array sort Y whit all points not in the 2*chapmDist.dist-wide verti. strip removed
		Point[] sortYS = new Point[xLength];						
		
		//copy left part of sortX to sortXL
		System.arraycopy(sortX, 0, sortXL, 0, lengthL);
		//copy right part of sortX to sortXR
		System.arraycopy(sortX, lengthL, sortXR, 0, lengthR);
		
		//build sortYL & sortYR
		for (int i = 0; i < xLength; i++) {
			if (sortY[i].x < sortX[lengthL-1].x 
					|| (sortY[i].x == sortX[lengthL-1].x && sortY[i].y <= sortX[lengthL-1].y)) {
				sortYL[lY++] = sortY[i];
				}
			else {
				sortYR[rY++] = sortY[i];
			}
		}

		leftPair = FindClosestPair(sortXL, sortYL);
		rightPair = FindClosestPair(sortXR, sortYR);

		champPair = leftPair.dist < rightPair.dist ? leftPair : rightPair;
		
		//build sortYS
		for (int i = 0; i < xLength; i++) {
			if (Math.abs(sortY[i].x - sortXL[lengthL-1].x) < champPair.dist) {
				sortYS[totalYS++] = sortY[i];
			}
		}
		
		for (int i = 0; i < totalYS; i++) {
			for (int j = i+1; j <= i+7  && j < totalYS; j++) {
				if (sortYS[i].distanceTo(sortYS[j]) < champPair.dist) {
					champPair = new ClosestDist(sortYS[i], sortYS[j], sortYS[i].distanceTo(sortYS[j]));
				}
			}
		}

		return champPair;
	}

	private ClosestDist BruteDist(Point[] sortX) {
		
		ClosestDist champPair = null;
		int xLength = sortX.length;
		double min = Double.POSITIVE_INFINITY;
		
		for (int i=0; i < xLength; i++) {
			for (int j=i+1; j < xLength; j++) {
				if (sortX[i].distanceTo(sortX[j]) < min) {
					min = sortX[i].distanceTo(sortX[j]);
					champPair = new ClosestDist(sortX[i], sortX[j], min);
				}
			}
		}
		return champPair;
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
