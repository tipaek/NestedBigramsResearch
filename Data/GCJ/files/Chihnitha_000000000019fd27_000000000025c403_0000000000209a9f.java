for i in range(int(input())):
    count=0
    n=input()
    s=""
    for j in n:
        j=int(j)
        if(j==count):
            s+=str(j)
        elif(count<j):
            s+='('*(j-count)+str(j)
        elif(count>j):
            s+=')'*(count-j)+str(j)
        count=j
    s+=')'*count
    print("case #{}".format(i+1),s)