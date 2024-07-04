 static void generateNewString(int testCase, String[] input){
        for(int i=0;i<testCase;i++){
            int current = 0;
            String output = "";
            int length = input[i].length();
            char[] stringToCharArray = input[i].toCharArray();
            System.out.println(length+","+input[i]);
            for(int j=0;j<length;j++){
                int digit = Character.getNumericValue(stringToCharArray[j]);
                //System.out.println(digit);
                if(digit>current){
                    int dif = digit - current;
                    for(int k=0;k<dif;k++){
                        output=output.concat("(");
                    }
                    output=output.concat(String.valueOf(stringToCharArray[j]));
                    current=digit;
                }
                else if(digit<current){
                    int dif = current - digit;
                    for(int k=0;k<dif;k++){
                        output=output.concat(")");
                    }
                    output=output.concat(String.valueOf(stringToCharArray[j]));
                    current=digit;
                }
                else{
                    output=output.concat(String.valueOf(stringToCharArray[j]));
                }
            }
            for(int k=0;k<current;k++){
                output=output.concat(")");
            }
            System.out.println(output);
        }
    }