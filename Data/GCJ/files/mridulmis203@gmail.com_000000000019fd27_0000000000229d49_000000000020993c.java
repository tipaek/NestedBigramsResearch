public int countNumberOfOnes(char[][] grid) {
        
       if(grid==null || grid.length==0){
            return 0;
        }
        int M = grid.length;
        int N = grid[0].length;
        int count=0;
        boolean [][] visited = new boolean[M][N];
        
        for(int row=0;i<M;row++){
            for(int col=0;col<N;col++){
                if(!visited[row][col] && grid[row][col]=='1'){
                    visit(grid,row,col,visited);
                    count++;
                }
            }
        }
        return count;
    }
    
    private void visit (
            char[][] grid, int row, int col, boolean [][] visited) {
          
        visited[row][col] =true;
        
        // move one row below
        if (isSafe(row+1,col,visited,grid)) {
            visit(grid,row+1,col,visited);
        }
        // move one row behind
        if (isSafe(row-1,col,visited,grid)) {
            visit(grid,row-1,col,visited);
        }
        // move one col front
        if (isSafe(row,col+1,visited,grid)) {
            visit(grid,row,col+1,visited);
        }
	// move one col behind
        if (isSafe(row,col-1,visited,grid)) {
            visit(grid,row,col-1,visited);
        }
         
    }
    
    private boolean isSafe
             (int row, int col, boolean[][] visited,char[][] grid ) {
         
        return row>=0
                && col>=0
                && row<grid.length
                && col<grid[0].length
                && !visited[row][col]
                && grid[row][col]=='1';
    }