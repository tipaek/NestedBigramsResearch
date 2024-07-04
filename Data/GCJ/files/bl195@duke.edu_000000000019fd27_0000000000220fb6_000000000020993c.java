Class Vestigium{
    public static void main(String args[]){
        Scanner scan = new Scanner(System.in);
        int size = scan.nextInt();
        for(int i = 0;i<size;i++){
            int n = scan.nextInt();
            int counter = 0;
            int k = 0;
            int r= n;
            int c = n;
            int[][] board = new int[n][n];
            for(int x = 0;x<n;x++){
                for(int y = 0;y<n;y++){
                    board[x][y]=scan.nextInt();
                    if(y==counter){
                        k += board[x][y];
                        counter++;
                    }
                }
            }
            for(int x = 0;x<n;x++){
                ArrayList<String> row = new ArrayList<String>();
                for(int y = 0;y<n;y++){
                    if(!row.contains(board[x][y]){
                        row.add(board[x][y]);
                        r--;
                        break;
                    }
                }
            }
            for(int x = 0;x<n;x++){
                ArrayList<String> col = new ArrayList<String>();
                for(int y = 0;y<n;y++){
                    if(!row.contains(board[y][x]){
                        row.add(board[y][x]);
                        c--;
                        break;
                    }
                }
            }
            System.out.println("Case #" + (i+1)+": " + k + " " + r+ " " + c);
        }
    }
}