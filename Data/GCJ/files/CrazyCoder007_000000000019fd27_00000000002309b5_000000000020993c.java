  public static void Calculate(int t, int n, int[][] arr){
        int trace=0;
        int dup_rows=0;
        int dup_cols=0;
        int case_count=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                if(i==j)
                       trace+=arr[i][j];
               }
        }
         for (int i = 0; i < n; i++) 
        { 
            for (int j = 0; j <n; j++)  
            { 
                 for (int k = 0; k < n; k++)  
                { 
                    if (arr[i][j]== arr[k][j] &&  i != k)  
                    { 
                        dup_rows+=1;
                        
                    } 
                } 
              for (int k = 0; k < n; k++) 
                { 
                    if (arr[i][j]== arr[i][k] &&   j != k) 
                    { 
                        dup_cols+=1;
                        
                    } 
                } 
            } 
        } 
         case_count+=1;
        System.out.println("Case #"+case_count+":"+ trace +" "+ dup_rows+" "+dup_cols) ;
    }
    
}
