public class Vestigium {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStream(System.in)));
        int numTests = Integer.parseInt(in.nextLine());
        for (int i = 0; i < numTest; i++) {
            int dim = Integer.parseInt(in.nextLine());
            HashMap<Integer, Set<Integer> rows = new HashMap<>();
            HashMap<Integer, Set<Integer> col = new HashMap<>();
            int trace = 0;
            for (int j = 0; j < dim; j++) {
                rows.put(j, new HashSet<Integer>()
                for (k = 0; k < dim; k++) {
                    Scanner line = new Scanner(in.nextLine());
                    int val = in.nextInt();
                    if (j == k) {
                        trace += val;
                    }
                    if (!col.containsKey(k)) {
                        col.put(k, new HashSet<Integer>);
                    }
                    row.get(j).add(val);
                    col.get(k).add(val);
                    
                }
            }
            int numRows = 0;
            int numCols = 0;
            for (int z = 0; z < dim; z++) {
                if (rows.get(z).size() != dim) {
                    numRows++;
                }
                if (col.get(z).size() != dim) {
                    numCols++;
                }
            }
            System.out.println("Case #" + i + ": " + trace + " " + numRows + " " + numCols);
        }
    }
}