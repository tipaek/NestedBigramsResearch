import numpy  as np
n=int(input())
def Repeat(x): 
    _size = len(x) 
    repeated = [] 
    for i in range(_size): 
        k = i + 1
        for j in range(k, _size): 
            if x[i] == x[j] and x[i] not in repeated: 
                repeated.append(x[i]) 
    return repeated 
for k in range(0,n):
    size=int(input())
    matrix = []
    for i in range(size):
        matrix.append(list(map(int, input().rstrip().split())))
    trace=0
    for i in range(size):
        trace+=matrix[i][i]
    #print(trace)
    countr=0
    for i in range(size):
        if(len(Repeat(matrix[i]))>0):
            countr+=1
    #print(countr)
    countc=0
    for j in range(size):
        col = [row[j] for row in matrix]
        if(len(Repeat(col))>0):
            countc+=1
    #print(countc)
    print('Case #',k+1,":",sep='',end=' ')
    print(trace,countr,countc)
            