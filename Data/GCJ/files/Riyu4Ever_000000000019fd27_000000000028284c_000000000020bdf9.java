for i in range(0,int(input())):
    x=list(input())
    for j in range(0,len(x)):
        x[j]=int(x[j])
    o=0
    c=0
    s=""
    for j in range(0,len(x)):
        if(s==""):
            if(x[j]==0):
                s=s+"0"
            else:
                s=s+"("*x[j]+str(x[j])
                c=c+x[j]
        elif(x[j]<x[j-1]):
            s=s+")"*(x[j-1]-x[j])+str(x[j])
            c=c-(x[j-1]-x[j])
        elif(x[j-1]==x[j]):
            s=s+str(x[j])
        elif(x[j]>x[j-1]):
            s=s+"("*(x[j]-x[j-1])+str(x[j])
            c=c+(x[j]-x[j-1])
    if(c!=0):
        s=s+")"*c
    print("Case #"+str(i+1)+": "+s)