n=int(input())
for i in range(n):
    l1=[]
    s=[]
    s1=[]
    k=int(input())
    for j in range(k):
        l=list(map(int,input().strip().split(" ")))
        l1.append(l)
    # print(l1)
    x=l1[0][0]
    y=l1[0][1]
    s.append("C")
    for k in range(1,len(l1)):
        # for j in range(len(l1[k])):
        if l1[k][1]>y and l1[k][0]>y or (l1[k][0]<x and l1[k][1]<x):
            # print(l1[k][0],l1[k][1])
            s.append("C")
            # x=l1[k][0]
            # y=l1[k][1]
                # print(s)
        elif (l1[k][0]>x and l1[k][0]<y) or (l1[k][0]<x):
            s.append("J")
        else:
            s.append("I")
    s1.append(s)
for i in range(len(s1)):
    pri=0
    for j in range(len(s1[i])):
        if s1[i][j]=="I":
            print("Case #",end="")
            print(i+1,end="")
            print(":",end=" ")
            print("IMPOSSIBLE")
            pri=1
            break
    if pri==0:
        print("Case #",end="")
        print(i+1,end="")
        print(":",end=" ")
        for j in range(len(s1[i])):
            print(s1[i][j],end="")
    print()
        
        
            
        