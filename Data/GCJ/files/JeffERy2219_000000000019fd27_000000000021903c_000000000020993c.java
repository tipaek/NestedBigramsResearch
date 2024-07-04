public class main {
    int[][] set;;
    
    int row = 0;
    int column = 0;
    for(int i = 0;i<set.length;i++){
        for(int n = 0;n<set[i].length;n++){
            for(j = n + 1;j <set[i].length;j++){
                if(set[i][j] == set[n][j]){
                    row++;
                }
            for(k = i + 1;i<set.length;k++){
                if(set[k][n] == set[i][n]){
                    column++;
            }
            }
        }
    }
    system.out.println(set.length);
    system.out.println(result);
    system.out.println(result);
}