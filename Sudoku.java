/*************************************************************************
 * Name: Uros Slana
 * Email: urossla(at)gmail.com
 *
 * Compilation: Sudoku.java
 * Dependencies:  
 *
 * Description: Sudoku solver
 *
 * % java Sudoku  grid.txt
 * 0 0 0 8 0 1 0 0 0
 * 0 0 0 0 0 0 4 3 0
 * 5 0 0 0 0 0 0 0 0
 * 0 0 0 0 7 0 8 0 0
 * 0 0 0 0 0 0 1 0 0
 * 0 2 0 0 3 0 0 0 0
 * 6 0 0 0 0 0 0 7 5
 * 0 0 3 4 0 0 0 0 0
 * 0 0 0 2 0 0 6 0 0
 *************************************************************************/

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Sudoku {

	public int[][] gridSolved;

	public Sudoku(int[][] grid) {

		gridSolved = new int [9][9];
		int[][] gridZero = new int [9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				gridSolved[i][j] = grid[i][j];
				gridZero[i][j] = grid[i][j];
			}
		}
	
		find(gridZero);
	}

	private boolean find(int[][] grid) {
		
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (grid[i][j] == 0) {
					int n = 1;
					while (n <= 9) {
						if (rowCol(n, i, j, grid) && square(n, i, j, grid)) {
							grid[i][j] = n;
							if (find(grid)) {
								gridSolved[i][j] = n;
								return true;
							}
						}
						n++;
					}
					grid[i][j] = 0;
					return false;
				}
			}
		}

		return true;
	}

	//check if row or col contain number
	private boolean rowCol(int n, int iS, int jS, int[][] grid) { 

		for (int j = 0; j < 9; j++) {
			if (grid[iS][j] == n) {
				return false;
			}
		}

		for (int i = 0; i < 9; i++) {
			if (grid[i][jS] == n) {
				return false;
			}
		}

		return true;
	}

	//check if square contain number
	private boolean square(int n, int i, int j, int[][] grid) {

		for (int iS = (i/3)*3; iS < ((i/3)*3)+3; iS++) {
			for(int jS = (j/3)*3; jS < ((j/3)*3)+3; jS++) {
				if (grid[iS][jS] == n) {
					return false;
				}
			}
		}
		return true;
	}

	public int[][] solution() {
		
		return gridSolved;
	}
	
	public static void main(String[] args) {

		if (args.length < 1) {
			System.out.println("Incorrect usage.");
			return;
		}
		
		Sudoko sudoko;
		Scanner scanNumber;
		int[][] grid = new int[9][9];
		File numbers = new File(args[0]);
		
		try {
			scanNumber = new Scanner(numbers);

			while(scanNumber.hasNextInt()) {

			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (scanNumber.hasNextInt()) {
						grid[i][j] = scanNumber.nextInt();
					}
				}
			}
			sudoko = new Sudoko(grid);
			int[][] solution = sudoko.solution();
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					System.out.print(solution[i][j] + " ");
				}
				System.out.println(" ");
			}

			System.out.println(" ");
			}
		}catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
}
