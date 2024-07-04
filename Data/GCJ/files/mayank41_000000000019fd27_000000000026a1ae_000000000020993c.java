public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int tc = sc.nextInt();
        while(tc > 0) {
            int n = sc.nextInt();
            int[][] grid = new int[n][n];
            for(int j=0; j<n; j++) {
                for(int k=0; k<n; k++) {
                    grid[j][k] = sc.nextInt();
                }
            }
            int k=0;
            for(int i=0; i<n; i++) {
                k+=grid[i][i];
            }
            Set<Integer> set = new HashSet<>();
            int r=0;
            for(int i=0; i<n; i++) {
                for(int j=0; j<n; j++) {
                    set.add(grid[i][j]);
                }
                if(set.size()<n) {
                    r++;
                }
                set.clear();
            }

            int c=0;
            for(int i=0; i<n; i++) {
                for(int j=0; j<n; j++) {
                    set.add(grid[j][i]);
                }
                if(set.size()<n) {
                    c++;
                }
                set.clear();
            }
            System.out.println("Case #" + tc + ": " + k + " " + r+ " " + c);
        }
    }