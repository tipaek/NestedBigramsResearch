import java.util.Scanner;

static Scanner s = new Scanner(System.in);

	public static void main(String[] args) {

		int numTestCases = s.nextInt();

		for(int i = 1; i <= numTestCases; i++) {

			int N = s.nextInt();

			int row = N;

			int matrix [][] = new int [row][];

			for(int j = 0; j < row; j++) {

				int col = N;

				matrix [j] = new int[col];

				for(int k = 0; k < col; k++) {

					matrix [j][k] = s.nextInt();
				}
			}

			int trace = findTrace(matrix);
			int numOfRows = checkRows(matrix);
			int numOfCols = checkCols(matrix);

			System.out.println("Case #" + i + ": " + trace + " " + numOfRows + " " + numOfCols);
		}

	}

	public static int findTrace(int array [][]) {

		int trace = 0;

		for(int i = 0; i < array.length; i++) {

			trace = trace + array[i][i];
		}

		return trace;
	}

	public static int checkRows(int array [][]) {

		int rowCount = 0;
		boolean flag = true;
		
		for(int i = 0; i < array.length; i++) {

			for(int j = 0; j < array[i].length - 1; j++) {

				for(int k = j+1; k < array[i].length; k++) {

					if(array[i][j] == array[i][k]) {

						rowCount++;
						flag = false;
						break;
					}
					else continue;

				}
				if(flag == false) break;				
			}
		}

		return rowCount;
	}



	public static int checkCols(int array [][]) {

		int colCount = 0;
		boolean flag = true;
		
		for(int j = 0; j < array[0].length; j++) {

			for(int i = 0; i < array.length - 1; i++) {

				for(int k = i+1; k < array.length; k++) {
					
					if(array[i][j] == array[k][j]) {
						
						colCount++;
						flag = false;
						break;
					}
					else continue;
				}
				if (flag == false) break;
			}
		}

		return colCount;
	}
