Class Solution{
    public static void trace(){
        Scanner in = new Scanner(System.in);  
        int t = in.nextInt();
        for(int i = 0;i<t;i++){
            int n = in.nextInt();
            int[][] array = new int[n][n];
            int trace = 0;
            for(int j = 0;j<n;j++){
                for(int r = 0; r<n;r++){
                array[i][j] = in.nextInt();
                if(i == j)
                    trace+=array[i][j];
                }
            }
            int row = 0;
            int col = 0;
            for(int j = 0;j<n;j++){
                    int res = arr[j][0];
                    for(int c = r+1;c<n;c++){
                        if(res == arr[j][c]){
                            row++;
                            break;
                        }
                    }
                    res = arr[0][j];
                    for(int c = r+1;c<n;c++){
                        if(res == arr[c][j]){
                            col++;
                            break;
                        }
                    }
                }
                System.out.println("Case #"+i+": "+trace+" "+row+" "+col);
            }
        }
    }
}