import java.util.*;
class Solution
{
    public static void main(String args[])
    {
    for tc in range(int(input())):
    n=list(map(int,input()))
    lst=[]
    lst.append(n[0]*'(')
    lst.append(str(n[0]))
    for i in range(1,len(n)):
        d=n[i]-n[i-1]
        if(d>0):
            lst.append(d*'(')
        else:
            lst.append(abs(d)*')')
        lst.append(str(n[i]))
    lst.append(n[-1]*')')
    print("Case #",end="")
    print(tc+1,end="")
    print(":","".join(lst))
    }
}