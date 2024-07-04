tt = int(input())
s = []
for i in range(tt):
    s.append(input())
index =1
for i in s:
    narray = []
    for j in i:
        narray.append(int(j))
    brac_weight =[]
    for j in narray:
        brac_weight.append([j,j])
    #print(brac_weight)        
    for j in range(len(narray)):
        for k in range(j+1,len(narray)):
            if (narray[j]==narray[k] and (j+1 == k)):
                brac_weight[j][1]=0
                brac_weight[k][0]=0 
            elif(narray[j]>narray[k] and (j+1 == k)):
                brac_weight[j][1]= (narray[j] - narray[k])
                brac_weight[k][0] = 0
            elif(narray[j]<narray[k] and (j+1 == k)):
                brac_weight[j][1]= 0
                brac_weight[k][0] = (narray[k] - narray[j])                     
    #print(brac_weight)
    #output 
    print("Case #",end='')
    print(index,end = '')
    print(":", end =" ")
    for i in range(len(narray)):
        
        print("("*brac_weight[i][0],end="")
        print(narray[i],end="")
        print(")"*brac_weight[i][1],end="")
    print()
    index = index+1