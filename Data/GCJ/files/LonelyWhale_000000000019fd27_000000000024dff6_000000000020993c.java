Scanner scan = new Scanner(system.in);
int trials= scan.nextInt();
for(int i =0 ; i< trials ; i++){
    int N = scan.nextInt();
    int[][] matrix = new int[N][N];
    for(int j =0 ; j<N ; j++){
        for(int k =0 ; k<N ; k++){
            int[j][k]= scan.nextInt();
        }
    }
    system.out.println(Array.deepToString(matrix);
}