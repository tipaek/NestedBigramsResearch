class Vestigium{
    
    public static void main(String args[]){
        int t;
        Scanner scanner = new Scanner(System.in);
        t = scanner.nextInt();
        for(int i = 0; i < t; i++){
            int rows = 0, columns = 0;
            int n = scanner.nextInt();
            int sum = (n * (n + 1)) / 2;
            int colSumArr[] = new int[n];
            int arr[][] = new int[n][n];
            int trace = 0;
            for(int i = 0; i < n; i++){
                int rowSum = 0;
                for(int j = 0; j < n; j++){
                    arr[i][j] = scanner.nextInt();
                    rowSum += arr[i][j];
                    colSumArr[j] += arr[i][j]; 
                    if(i == j){
                        trace += arr[i][j];
                    }
                }
                if(rowSum != sum){
                    rows++;
                }
            }
            for(int i = 0; i < n; i++){
                if(colSumArr[i] != sum){
                    columns++;
                }
            }
            System.out.println(traces+" "+rows+" "+columns);
        }
        
    }
    
    
}
}