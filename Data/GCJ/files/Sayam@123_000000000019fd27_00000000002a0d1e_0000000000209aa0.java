public class Solution{
   public static boolean isRowSafe(int a[][],int row,int num) {
		for(int j=0;j<4;j++) {
			if(a[row][j]==num)
				return false;
		}
		return true;
	}
	public static boolean isColSafe(int a[][],int col,int num) {
		for(int i=0;i<4;i++) {
			if(a[i][col]==num)
				return false;
		}
		return true;
	}
	public static boolean isBoxSafe(int a[][],int row,int col,int num) {
		int rowstart=row-row%2;
		int colstart=col-col%2;
		for(int i=rowstart;i<rowstart+2;i++) {
			for(int j=colstart;j<colstart+2;j++) {
				if(a[i][j]==num)
					return false;
			}
		}
		return true;
	}
    public static boolean canPlaceNum(int a[][],int row,int col,int num) {
    	if(!isRowSafe(a,row,num))
    		return false;
    	if(!isColSafe(a,col,num))
    		return false;
    	if(!isBoxSafe(a,row,col,num))
    		return false;
    	return true;
    }
	public static boolean sudokusolver(int board[][]) {
		boolean isEmpty=false;
		int row=-1;
		int col=-1;
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				if(a[i][j]==0) {
				isEmpty=true;
				row=i;
				col=j;
				break;
			    }
			}
			if(isEmpty) {
				break;
			}
		}
		if(!isEmpty)
			return true;
		for(int num=1;num<=4;num++) {
			if(canPlaceNum(a,row,col,num)) {
				board[row][col]=num;
			if(sudokusolver(a))
				return true;
			a[row][col]=0;
			}
		}
		return true;
	}
   public static void main(String[] args){
       Scanner sc=new Scanner(System.in);
       int t=sc.nextInt();
       int f=1;
       while(f<=t)
       {
           int n=sc.nextInt();
           int k=sc.nextInt();
           int a[][]=new int[n][n];
           sudokusolver(a);
           int sum=0;
           for(int i=0;i<N;i++){
              for(int j=0;j<N;j++){
                 if(i==j){
                    sum+=a[i][j];
                 }
               }
            }
            if(sum==k){
              System.out.println("Case #"+t+": "+"POSSIBLE");
              for(int i=0;i<N;i++){
                 for(int j=0;j<N;j++){
                    System.out.print(a[i][j]);
                 }
                 System.out.println();
              }
            }
            else
              System.out.println("Case #"+t+": "+"IMPOSSIBLE");
            f++;
        }
        sc.close();
}