import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Solution() {

	public static void main(String[] args) {


		try{

			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader br = new BufferedReader(isr);
			String line = br.readLine();
			StringBuilder builder = new StringBuilder();
			int n = Integer.parseInt(line);

			for( int z = 0 ; z < n ; ++z ) {

				int m = Integer.parseInt(br.readLine());

				int[][] matrix = new int[m][m];

				for( int i = 0 ; i < m ; ++i ) {
					line = br.readLine();
					String[] vals = line.split(" ");
					for( int j = 0 ; j < m ; ++j ){
						int value = Integer.parseInt(vals[j]);
						matrix[i][j] = value;
					}
				}


				int contCol = 0;
				int contRow = 0;

				int diag = 0;

				int repRows = 0;
				int repCols = 0;

				int[] arrCol = new int[3];
				int[] arrRow = new int[3];

				for(int i =0; i<m; i++) {

					for(int j = 0; j<m; j++) {

						//Guarda las columnas y filas actuales
						arrRow[j] = matrix[i][j];
						arrCol[j] = matrix[j][i];

						if(i==j)diag+=matrix[i][j];

					}

					for(int x=0; x<m; x++) {

						int actualCol = arrCol[x];
						int actualRow = arrRow[x];

						for(int y = x+1; y<m ; y++) {

							if( contCol ==0 && (actualCol ^ arrCol[y]) == 0) {

								repCols++;
								contCol++;

							}

							if( contRow==0 && (actualRow ^ arrRow[y]) == 0) {

								repRows++;
								contRow++;

							}

						}

					}

					contRow=0;
					contCol=0;

				}

				builder.append("Case #"+(z+1)+": "+diag+" "+repRows+" "+repCols+"\n");
			}

			System.out.println(builder.toString());

		} catch (Exception e) {

		}
	}
}