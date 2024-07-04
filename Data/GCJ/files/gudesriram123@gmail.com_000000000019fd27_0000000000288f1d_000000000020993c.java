t=int(input())
t1=t
l=[]
l2=[]
result=[]


while t!=0:
    trace=0
    c=0
    r=0
    res=[]
    n=int(input())
    for i in range(n):
        l1=list(map(int,input().split()))
        l.append(l1)
    for i in range(n):
        trace=trace+l[i][i]
    res.append(trace)
    for lst in l:
        for i in lst:
            if lst.count(i)>1:
                r=r+1
                break
    res.append(r)
    for i in range(n):
        for j in range(n):
            l2.append(l[j][i])
        for k in l2:
            if l2.count(k)>1:
                c=c+1
                break
        l2.clear()
    res.append(c);
    result.append(res)
    #print(res)
    t=t-1
    l.clear()
    #print(result)
for lst in result:
    print("Case #"+str(t1-t1+1)+": "+str(lst[0])+" "+str(lst[1])+" "+str(lst[2]))
    t1=t1-1
    
