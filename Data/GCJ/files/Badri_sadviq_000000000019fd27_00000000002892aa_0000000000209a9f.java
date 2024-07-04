{
    public static void main(String args[])
    {
        int i,j,q;
        Scanner sc=new Scanner(System.in);
        int t=sc.nextInt();
        for(i=0;i<t;i++)
        {
             String str=sc.next();
            int len=str.length();
            char[] ch = new char[len];
            for( q = 0; q < len; q++) 
            { 
               ch[q] = str.charAt(q); 
            } 
            for(j=0;j<len;j++)
            {
               if(ch[j]=='1')
                {
                    System.out.print("("+ch[j]+")");
                   
                }
                else
                {
                  System.out.print(ch[j]);
                }
            }
        }
    }
}
