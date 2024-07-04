t=int(input())
count=1
a=[]
ac=[]
c=[0,0]
j=[0,0]
f=0
while(t):
    f=1
    temp=""
    ans=[]
    a=[]
    c=[0,0]
    j=[0,0]
    i=0
    n = int(input())
    for i in range(n):
        a.append(list(map(int, (input()+" "+str(i)).split())))
    i=0
    a.sort()
    #print(a)
    for i in range(n):
        #print(ans)
        if(a[i][0]>=c[1]):
            ans.append("C")
            c[1]=a[i][1]
        elif(a[i][0]>=j[1]):
            ans.append("J")
            j[1]=a[i][1]
        else:
            ans.clear()
            ans="IMPOSSIBLE"
            f=0
            break
    i=0
    if(f):
        for i in range(n):
            if(a[i][2]!=i):
                temp=ans[i]
                ans[i]=ans[a[i][2]]
                ans[a[i][2]]=temp
                a[a[i][2]][2]=a[i][2]
        #         #print(ans)
    print("Case #" + str(count) + ": " + "".join(map(str,ans)))

    t=t-1
    count=count+1
    