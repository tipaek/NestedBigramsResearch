class Solution
{
    public static void main(String args[])throws IOException
    {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int t,c=1;
        t=Integer.parseInt(br.readLine());
        while(c<=t)
        {
            n=Integer.parseInt(br.readLine());
            String str[]=new String[n];
            for(i=0;i<n;i++)
            str[i]=br.readLine();
            for(i=0;i<n;i++)
            {
                for(j=0;j<str[i].length();j++)
                {
                    if(str[i].charAt(j)=='*')
                    {
                        str[i]=str[i].substring(0,j)+"(.*)"+str[i].substring(j+1,str[i].length+1);
                        j=j+2;
                    }
                }
            }
            
            System.out.println("Case #"+c+": "+);
            c++;
        }
    }
}