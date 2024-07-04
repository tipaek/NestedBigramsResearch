public static String two(int[] a) {
		int i = 0;
		String result = "";
		for(int x : a) {
			if(x>i) {
				for(int j=0; j<x-i; j++) {
					result+="(";
				}
				i+=x;
			}else if(x<i) {
				for(int j=0; j<i-x; j++) {
					result+=")";
				}
				i-=x;
			}
			result+=x;
		}
		//drawling last parenthesis
		for(int j=0; j<i; j++) {
			result+=")";
		}
		return result;
	}