t=int(input())

for turn in range(t):
    sum,row,col=0,0,0
    n=int(input())
    matrix=[]
    for i in range(0,n):
        a = [int(x) for x in input().split()]
        matrix.append(a)
        
    for i in range(0,n):
        sum+=matrix[i][i]
        if len(matrix[i])!=len(set(matrix[i])):
            row+=1
    for k in range(n):
        li=[]
        for m in range(n):
            li.append(matrix[m][k])
        if len(li)!=len(set(li)):
            col+=1
    
    print("Case #",turn+1,": ",sum," ",row," ",col,sep="")