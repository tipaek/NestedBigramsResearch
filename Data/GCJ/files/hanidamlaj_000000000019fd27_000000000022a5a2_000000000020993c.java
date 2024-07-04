public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String line;
    StringTokenizer st;
    int case = 1;
    while((line = in.nextLine()) != null) {
        
        // parse the first line as the number of lines to be read
        int num_lines = Integer.parseInt(line);
        if(num_lines == 0) {
            System.out.println("Case #" + (case++) + ": 0 0 0");
            continue;
        }
        
        // create the matrix
        int[][] mat = new int[num_lines][num_lines];
        for(int row = 0; row < num_lines; row++) {
            line = in.nextLine();
            st = new StringTokenizer(line);
            for(int col = 0; col < num_lines; col++) {
                mat[row][col] = Integer.parseInt(st.next());
            }
        }
        
        int trace = 0;
        for(int index = 0; index < num_lines; index++)
            trace += mat[index][index];
        
        int num_repeated_rows = 0;
        for(int row = 0; row < num_lines; row++) {
            HashSet<Integer> s = new HashSet<>();
            for(int col = 0; col < num_lines; col++) {
                if(s.contains(mat[row][col])) {
                    num_repeated_rows++;
                    break;
                }
                s.add(mat[row][col]);
            }
        }
        
        int num_repeated_cols = 0;
        for(int col = 0; col < num_lines; col++) {
            HashSet<Integer> s = new HashSet<>();
            for(int row = 0; row < num_lines; row++) {
                if(s.contains(mat[row][col])) {
                    num_repeated_cols++;
                    break;
                }
                s.add(mat[row][col]);
            }
        }
        
        System.out.println("Case #" + (case++) + " " + trace + " " + num_repeated_rows + " " + num_repeated_cols);
    }
    
}