mport java.util.*;



public class Solution {

public static void main(String[] args) {
Scanner sc=new Scanner(System.in);
t=int(input())
T=t
while(t>0):
    t-=1
    nums = list(map(int, list(input())))
    val=0
    ans=""
    for n in nums:
        if n>val:
            ans+="("*(n-val)+str(n)
        if n<val:
            ans+=")"*(val-n)+str(n)
        if n==val:
            ans+=str(n)
        val=n
    ans+=")"*(val)
    print("Case #" + str(T-t) + ": " + ans)