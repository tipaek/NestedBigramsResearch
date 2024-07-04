T = int(input())
for i in range(1,T+1):
    size=int(input())
    column =[]
    r = 0
    c = 0
    k = 0
    for a in range(size):
        row = list(map(int,input().split()))
        column.append(row)
    for q in range(size):
        k=k+column[q][q]
    for q in column:
        for z in q:
            if q.count(z)>1:
                r = r+1
                break
    column = [[column[j][i] for j in range(len(column))] for i in range(len(column[0]))]

    for q in column:
        for z in q:
            if q.count(z)>1:
                c = c+1
                break
    i=str(i)
    print("Case #"+i+":",k,r,c)