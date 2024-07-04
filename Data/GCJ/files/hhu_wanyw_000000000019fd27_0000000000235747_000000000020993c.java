    void run(){
        Scanner in=new Scanner(System.in);
        int t=in.nextInt();
        for(int l=1;l<=t;l++){
            int n=in.nextInt();
            int[] cnt=new int[n+1];
            int[][] grid=new int[n+1][n+1];
            int trace=0,rc=0,cc=0;
            for(int i=0;i<n;i++){
                boolean counted=false;
                for(int j=0;j<n;j++){
                    grid[i][j]=in.nextInt();
                    if(i==j) trace+=grid[i][j];
                    cnt[grid[i][j]]++;
                if(!counted&&cnt[grid[i][j]]>1){
                    rc++;
                    counted=true;
                }
                }
                Arrays.fill(cnt,0);
            }
            for(int i=0;i<n;i++){
                boolean counted=false;
                for(int j=0;j<n;j++){
                    cnt[grid[j][i]]++;
                    if(!counted&&cnt[grid[j][i]]>1) {
                        cc++;
                        counted=true;
                    }
                }
                Arrays.fill(cnt,0);
            }
            System.out.println("Case #"+l+": "+trace+" "+rc+" "+cc);
        }
    }
