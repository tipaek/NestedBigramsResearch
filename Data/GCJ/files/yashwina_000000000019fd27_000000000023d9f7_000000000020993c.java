public void answer(int[][]matrix)
int trace = 0;
int row = 0;
int column = 0;
for (int i=0;i<=matrix.length;i=i+1){
    for (int j=0;j<=matrix[0].length;j=j+1){
        if ((matrix[i][j]==matrix[i][j+1])||(matrix[i][j]==matrix[i][j+2])||(matrix[i][j]==matrix[i][j+3])||(matrix[i][j]==matrix[i][j+4])||(matrix[i][j+2]==matrix[i][j+3])||(matrix[i][j+3]==matrix[i][j+4])){
            row = row+1;    
        }
    }
    for (int j=0;j<=matrix[0].length;j=j+1){
        if ((matrix[i][j]==matrix[i+1][j])||(matrix[i][j]==matrix[i+2][j])||(matrix[i][j]==matrix[i+3][j])||(matrix[i][j]==matrix[i+4][j])||(matrix[i+2][j]==matrix[i+3][j])||(matrix[i+3][j]==matrix[i+4][j])){
            column = column+1;    
        }
    }
}
for (int i=0;i<=matrix.length;i=i+1){
    for (int j=0;j<=matrix[0].length;j=j+1){
        if (i==j){
            trace = trace + matrix[i][j];
        }
        
    }
}
System.out.println(trace, row, column);