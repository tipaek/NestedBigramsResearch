public static void main(String[] args) throws IOException {
	    Scanner in = new Scanner(System.in);
	    
	    int numCases = in.nextInt();
	    for(int i = 0; i < numCases; i++) {
	    	int numRows = in.nextInt();
	    	int [][] countRows = new int[numRows][numRows];
	    	int [][] countCols = new int[numRows][numRows];
	    	HashMap<Integer, Boolean> hasMoreRows = new HashMap<>();
	    	HashMap<Integer, Boolean> hasMoreCols = new HashMap<>();
	    	int sum = 0;
	    	for(int j=0; j<numRows; j++) {
	    		for(int k=0; k<numRows;k++) {
	    			int val = in.nextInt();
	    			if(j==k)
	    				sum+=val;
	    			countRows[j][val-1]++; 
	    			countCols[k][val-1]++;
	    			if(countRows[j][val-1]>1 && !hasMoreRows.containsKey(j)) 
	    			{
	    				hasMoreRows.put(j, true);
	    			}
	    			if(countCols[k][val-1]>1 && !hasMoreCols.containsKey(k)) 
	    			{
	    				hasMoreCols.put(k, true);
	    			}
	    		}
	    	}
	    	System.out.println(sum + " " + hasMoreRows.size() + " " + hasMoreCols.size());
	    }
	    in.close();
	}