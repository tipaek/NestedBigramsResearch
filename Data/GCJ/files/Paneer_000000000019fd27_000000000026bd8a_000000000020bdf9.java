
T=int(input())
for r in range(0,T):
    n=int(input())
    a=[]
    for t in range(0,n):
        b=[int(x) for x in input().split(" ")]
        a.append(b)
    c=[]
    d=[]
    for i in range(0,len(a)):
        c.append(a[i][0])
        d.append(a[i][1])
    for k in range(0,len(c)):
        for j in range(0,len(c)-1):
            if(c[j]>c[j+1]):
                c[j],c[j+1]=c[j+1],c[j]
                d[j],d[j+1]=d[j+1],d[j]
    C=0
    J=0
    S=""
    for m in range(0,len(d)):
        if(C<=J):
            if(c[m]>=C):
                S=S+"C"
                C=C+d[m]
            else :
                S="IMPOSSIBLE"
                break
        else :
            if(c[m]>=J):
                S=S+"J"
                J=J+d[m]
            else :
                S="IMPOSSIBLE"
                break
    print("Case #",end="")
    print(r+1,end="")
    print(":",S)