public class Vestigium {
    
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int T = s.nextInt();
        int N = s.nextInt();
        int trace;
        int rowDup = 0;
        int colDup = 0
        for(int x = 0; x < T; x++){
            trace = 0;
            rowDup = 0;
            colDup = 0;
            ArrayList<HashSet<Integer>> col = new ArrayList<>();
            for(int y = 0; y < N; y++){
                col.add(new HashSet<Integer>());
            }
            for(int y = 0; y < N; y++){
                HashSet<Integer> row = new HashSet<Integer>();
                for(int z = 0; z < N; z++){
                    int in = s.nextInt();
                    if(y == z){
                        trace += in;
                    }
                    if(row.contains(in)){
                        rowDup++;
                    }
                    if(col.get(y).contains(in)){
                        colDup++;
                    }
                    row.add(in);
                    col.get(y).add(in);
                }
            }
            System.out.println("Case #" + (x + 1) + ": " + trace + " " + rowDup + " " + colDup);
        }
    }
    
}