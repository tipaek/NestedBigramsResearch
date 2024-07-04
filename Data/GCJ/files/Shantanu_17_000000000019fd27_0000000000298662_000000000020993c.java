t=int(input(""))
for tt in range (1,t+1):
    n=int(input(""))
    l=[]
    rowc=0
    colc=0
    nus=set([int(x) for x in range(n+1)])
    for i in range(n):
        
        c2=[int(x) for x in input("").split(" ")]
        l.append(c2)
        c2=set(c2)
        
        if(n!=len((c2))):
            rowc+=1
    i=0
    
    for i in range(n):
        le=[]
        for j in range(n):
            le.append(l[j][i])
            
        
        lc=set(le)
        
        
        if(n!=len((lc))):
            colc+=1
    i=0
    lc=0
    for i in range(n):
        lc=l[i][i]+lc
    print("Case #"+str(tt)+": " +str(lc)+" "+str(rowc) +" "+str(colc))