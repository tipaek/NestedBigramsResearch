
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(NestingDepth("4\n0000\n101\n111000\n1"));
	}
	public static String NestingDepth (String input){
		String[] cases = input.split("\n");
		StringBuilder sb = new StringBuilder();
		int prev = 0;
		for (int i = 1; i < cases.length; i++) {
		    System.out.print("Case #"+ i + ": ");
			for (int j = 0; j < cases[i].length(); j ++){
				if (Integer.parseInt(cases[i].substring(j, j+1)) > prev) {
					sb.append("(");
				}
				else if ((Integer.parseInt(cases[i].substring(j, j+1)) < prev)){
					sb.append(")");
				}
				
				sb.append(cases[i].charAt(j));
				prev = Integer.parseInt(cases[i].substring(j, j+1));
			}
			for (int j = 0; j < prev; j++) {
				sb.append(")");
			}
			prev = 0;
			sb.append("\n");
		}

		return sb.toString();
	}