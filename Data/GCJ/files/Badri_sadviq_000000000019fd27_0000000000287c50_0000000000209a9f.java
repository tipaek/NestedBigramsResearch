{
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        int i,j;
        int t=sc.nextInt();
        for(i=0;i<t;i++)
        {
            String str=sc.next();
            int len=str.length();
            char[] ch = new char[len];
            for (int q = 0; i < len; i++) 
            { 
               ch[i] = str.charAt(i); 
            } 
            for(j=0;j<len;j++)
            {
               if(ch[j]=='1')
                {
                    System.out.print("("+ch[j]+")");
                   
                }
                else{
                  System.out.print(ch[j]);
                }
            }
        }
    }
}