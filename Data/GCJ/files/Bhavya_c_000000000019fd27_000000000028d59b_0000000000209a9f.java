
        for(int i=0; i<first;i++){
            sb.append('(');
        }
        sb.append(first);
       
        for (int i=1;i<chars.length;i++){
            int d=Character.getNumericValue(chars[i]);
           
            if(d==num){
                sb.append(d);
            } else if(d>num){
                int diff=d-num;
                for (int j=0;j<diff;j++){
                    sb.append('(');
                    brackets++;
                }
                sb.append(d);
            } else {
                int diff=num-d;
               
                for (int j=0;j<diff;j++){
                    sb.append(')');
                    brackets--;
                }
                sb.append(d);
            }
           
            num=Character.getNumericValue(chars[i]);
        }
       
        while(brackets-->0){
            sb.append(')');
        }
       
        System.out.println("Case #"+(tn++)+": "+sb.toString());
    }
}
