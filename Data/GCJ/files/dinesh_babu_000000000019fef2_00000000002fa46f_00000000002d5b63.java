 class Solution {
    public static void main(String args[]) {
     
     
      Scanner sc=new Scanner(System.in);
     int T=sc.nextInt();
     for(int j=1;j<=T;j++)
     {
        double ans=0;
        int row=sc.nextInt();
        int col=sc.nextInt();
        int startrow=sc.nextInt();
        int startcol=sc.nextInt();
        int endrow=sc.nextInt();
        int endcol=sc.nextInt();
        double mat[][]=new double[row+1][row+1]
        for(int ind = startrow ; ind <=  endrow ; ind ++ ){
            for(int tr = startcol  ; tr  <= endcol ; tr++ )
            {
                mat[ind][tr]=-1;
            }
        }
        mat[1][1]=1;
        for(int i=1 ; i<= row ; i++ )
        {
            for(int k=1 ; k<= col ;k++)
            {
                if(mat[i][k]!=-1)
                {
                    if(col == 1 )
                    if(i == row )
                    {
                    mat[i][k]=(mat[i-1][j]/2) + mat[i][j-1];
                    }
                }
            }
        }
       System.out.println("Case #"+j+": "+ans);
          
     }
    }
}