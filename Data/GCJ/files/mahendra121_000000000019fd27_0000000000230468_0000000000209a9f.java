for t in range(int(input())):
    s=input()
    l=['0']
    top=0
    n='0'
    c=0
    for i in range(len(s)):
        #print(l)
        f=0
        if(int(s[i])<=int(n)):
            k=int(n)-int(s[i])
            k1=top
            #print(k,k1,s[i])package nesting_depth;

import java.util.Scanner;

class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        for(int t =1; t<T+1; t++){
            String s = in.next();
            solve(s, t);
        }
        return;
    }

    public static void solve(String s, int t){
        int d = 0; // depth
        String ans = "";
        for(char c : s.toCharArray()){
            int n = c - '0';
            if(n>d){
                while(n>d){
                    ans += ("(");
                    d++;
                }
            }
            if(n<d){
                while(n<d){
                    ans += (")");
                    d--;
                }
            }
            ans += (c);
        }
        while(d>0){
            ans += (")");
            d--;
        }
        System.out.println(String.format("Case #%d: %s", t, ans));
    }
}
            l.insert(k1+k+1,s[i])
            n=s[i]
            top+=1
            continue
        for j in range(int(s[i])):
            l.append('(')
            top+=1
        l.append(s[i])
        n=s[i]
        top+=1
        for j in range(int(s[i])):
            l.append(')')
    l.remove(l[0])
    k=''
    for i in l:
        k+=i
    print("Case #%d: "%(t+1),k)
