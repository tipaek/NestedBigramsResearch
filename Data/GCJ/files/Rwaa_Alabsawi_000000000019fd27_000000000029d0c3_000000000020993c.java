public static String one(int[][] a) {
		int sumDiagonal =0; int r =0; int c = 0;
		for(int i=0; i< a.length; i++) {
			sumDiagonal+=a[i][i];
		}
		//rows
		for(int i=0; i< a.length; i++) {
			for(int j=0; j< a.length; j++) {
				int x = a[i][j];
				boolean duplicateFound = false;
				for(int k=j; k< a.length; k++) {
					if(x==a[i][k]) {
						duplicateFound=true;
						break;
					}
				}
				if(duplicateFound) {
					r++;
					break;
				}
			}
		}
		//columns
		for(int i=0; i< a.length; i++) {
			for(int j=0; j< a.length; j++) {
				int x = a[i][j];
				boolean duplicateFound = false;
				for(int k=j; k< a.length; k++) {
					if(x==a[k][j]) {
						duplicateFound=true;
						break;
					}
				}
				if(duplicateFound) {
					c++;
					break;
				}
			}
		}
		return "case: "+sumDiagonal+ " "+r+ " "+c;
	}