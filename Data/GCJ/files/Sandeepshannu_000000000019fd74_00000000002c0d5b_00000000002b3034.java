import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.String Tokenizer;
public class solution{
    ststic FastReader sc=new FastReader();
    public static void main(String args[]){
        int t=sc.nextInt();
        for(int i=1;i<=t;i++){
            String res=solve(sc.nextInt());
            System.out.println("Case#"+i+":"+res);
        }
    }
    static String solve(int N){
        String arr[]=new String[N];
        int max = 0;
        int pos = -1;
        boolean flag = true;
        for(int i=0;i<N;i++){
            arr[i] = sc.nextLine();
            if(i>=1)
                if(max<arr[i].length()){
                    max=arr[i].length();
                    pos=i;
                }
        }
        for(int i=0;i<n;i++){
            String sub = arr[i].substring(1);
            if(flag&&!arr[pos].contains(sub)){
                flag=false;
                break;
            }
        }
        if9flag){
            return arr[pos].substring(1);
        }
        return "*";
    }
    static class FastReader{
        BufferedReader br;
        String Tokenizer st;
        public FastReader(){
            br = new Bufferedreader(new inputStreamreader(System.in));
        }
        String next(){
            while(st==null||!st.hasMoreElements()){
                try{
                    st=new String Tokenizer(br.readLine());
                }catch(IOException e){
                    e.printStack Trace();
                }
            }
            return st.nextToken();
        }
        int nextInt(){
            return Integer.parseInt(next());
        }
        long nextLong(){
            return Long.parseLong(next());
        }
        double nextDouble(){
            return Double.parseDouble(next());
        }
        String nextLine(){
            String str="";
            try{
                str=br.readLine();
            }catch (IOException e){
                e.printStackTrace();
            }
            return str;
        }
    }
}





























            
            
            
            
        
        

        
        
                
                
            
        
    
        
    
        
            
        
                
            
    
        
        
    
