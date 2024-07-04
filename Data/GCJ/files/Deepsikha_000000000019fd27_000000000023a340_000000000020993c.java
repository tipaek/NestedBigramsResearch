n=int(input())
for t in range(1,n+1):
    n=int(input())
    a=[]
    for _ in range(n):
        a.append(list(map(int,input().split())))
    r=0
    for i in range(n):
        d={}
        for j in range(n):
            if a[i][j] not in d:
                d[a[i][j]]=True
            else:
                r+=1
                break
        del d
    c=0
    for i in range(n):
        d={}
        for j in range(n):
            if a[j][i] not in d:
                d[a[j][i]]=True
            else:
                c+=1
                break
        del d
    s=0
    for i in range(n):
       s+=a[i][i]
    print("Case #{}: {} {} {}".format(t,s,r,c))