import java.io.*; 
import java.util.*; 
//import javafx.util.*; 
import java.math.*;
//import java.lang.*;
public class Solution 
{ 
    
   // static int n;
    static HashSet<Integer> adj[];
    
     static boolean vis[];
   //  static long ans[];
   // static int arr[];
    static long mod=1000000007;
    static final long oo=(long)1e18;
     static int n;
    
    public static void main(String[] args) throws IOException { 
    //    Scanner sc=new Scanner(System.in);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        br = new BufferedReader(new InputStreamReader(System.in));
        int test=nextInt();
        int tes=1;
        outer: while(test--!=0){ 
            int n=nextInt();
            Pair arr[]=new Pair[n];
            //Pair arr[]=new Pair[n];
            HashMap<Integer,Integer> hm=new HashMap<>();
            for(int i=0;i<n;i++){
                int l=nextInt();
                arr[i]=new Pair(i,l,nextInt());
               // hm.put(l,i);
            }
            Arrays.sort(arr);
            for(int i=0;i<n;i++){
                //int l=nextInt();
               // arr[i]=new Pair(i,l,nextInt());
                hm.put(arr[i].l,i);
            }
            char ans[]=new char[n];
            int count[]=new int[10000];
            for(int i=0;i<n;i++){
                count[arr[i].l]++;
                count[arr[i].r]--;
            }
            for(int i=1;i<10000;i++){
                count[i]+=count[i-1];
                if(count[i]>2){
                    pw.println("Case #"+tes+": IMPOSSIBLE");
                    tes++;
                    continue outer;
                }
            }
           // boolean C=false;
            //boolean J=false;
            Arrays.fill(ans,'C');
            for(int i=0;i<10000;){
                if(hm.containsKey(i)){
                    int j=hm.get(i);
                    ans[arr[j].index]='J';
                    i=arr[j].r;
                }
                else{
                    i++;
                }

                // ans[arr[i].index]=t==0?'C':'J';
                //t^=1;
               
            }
            String print=String.valueOf(ans);
            pw.println("Case #"+tes+": "+print);
            tes++;
        }   
        pw.close();
    }
    static class Pair implements Comparable<Pair>{
        int index;int l;int r;
        Pair(int index,int l,int r){
            this.index=index;
            this.l=l;
            this.r=r;
        }   
        public int compareTo(Pair p){
            return (l-p.l);
        }
    }
    static long ncr(long n,long r){
        if(r==0)
            return 1;
        long val=ncr(n-1,r-1);
        val=(n*val)%mod;
        val=(val*modInverse(r,mod))%mod;
        return val;
    }

    static int find(ArrayList<Integer> a,int i){
        
        if(a.size()==0||i<0)return 0;
        ArrayList<Integer> l=new ArrayList<Integer>();
        ArrayList<Integer> r=new ArrayList<Integer>();
        for(int v:a){
            if((v&(1<<i))!=0)l.add(v);
            else r.add(v);
        }

        if(l.size()==0)return find(r,i-1);
        if(r.size()==0)return find(l,i-1);
        return Math.min(find(l,i-1),find(r,i-1))+(1<<i);
    }
    
    
    public static BufferedReader br;
    public static StringTokenizer st;
    public static String next() {
        while (st == null || !st.hasMoreTokens()) {
       try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return st.nextToken();
    }
 
    public static Integer nextInt() {
        return Integer.parseInt(next());
    }
    public static Long nextLong() {
        return Long.parseLong(next());
    }
 
    public static Double nextDouble() {
        return Double.parseDouble(next());
    }
    // static class Pair{
    //     int x;int y;
    //     Pair(int x,int y,int z){
    //         this.x=x;
    //         this.y=y;
    //        // this.z=z;
    //      //   this.z=z;
    //       //  this.i=i;
    //     }
    // }
    // static class sorting implements Comparator<Pair>{
    //     public int compare(Pair a,Pair b){
    //         //return (a.y)-(b.y);
    //         if(a.y==b.y){
    //             return -1*(a.z-b.z);
    //         }
    //         return (a.y-b.y);
    //     }
   // }
    public static int[] na(int n)throws IOException{
        int[] a = new int[n];
        for(int i = 0;i < n;i++)a[i] = nextInt();
        return a;
    }
    static class query implements Comparable<query>{
        int l,r,idx,block;
        static int len;
        query(int l,int r,int i){
            this.l=l;
            this.r=r;
            this.idx=i;
            this.block=l/len;
        }   
        public int compareTo(query a){
            return block==a.block?r-a.r:block-a.block;
        }
    }
    
    // static class sorting implements Comparator<Pair>{  
    //     public int compare(Pair a1,Pair a2){  
    //         if(o1.a==o2.a)
    //             return (o1.b>o2.b)?1:-1;  
    //         else if(o1.a>o2.a)  
    //             return 1;  
    //         else  
    //             return -1;  
    //     }
    // }  
    static boolean isPrime(int n) { 
        if (n <= 1) 
            return false; 
        if (n <= 3) 
            return true; 
        if (n % 2 == 0 ||  
            n % 3 == 0) 
            return false; 
      
        for (int i = 5; 
                 i * i <= n; i = i + 6) 
            if (n % i == 0 || 
                n % (i + 2) == 0) 
                return false; 
      
        return true; 
    } 
    static long gcd(long a, long b) { 
      if (b == 0) 
        return a; 
      return gcd(b, a % b);  
    }  
    // To compute x^y under modulo m 
    static long power(long x, long y, long m){ 
        if (y == 0) 
            return 1;      
        long p = power(x, y / 2, m) % m; 
        p = (p * p) % m; 
      
        if (y % 2 == 0) 
            return p; 
        else
            return (x * p) % m; 
    }
    static long fast_pow(long base,long n,long M){
        if(n==0)
           return 1;
        if(n==1)
        return base;
        long halfn=fast_pow(base,n/2,M);
        if(n%2==0)
            return ( halfn * halfn ) % M;
        else
            return ( ( ( halfn * halfn ) % M ) * base ) % M;
    }
       static long modInverse(long n,long M){
        return fast_pow(n,M-2,M);
    }
    // (1,1)   

    //     (3,2)   (3,5)

} 