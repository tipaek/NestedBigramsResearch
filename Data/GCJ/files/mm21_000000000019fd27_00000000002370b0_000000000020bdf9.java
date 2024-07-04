def isbusy(n , l) :
    for i in range(len(n)) :
        if (l[0] >= n[i][0] and l[0] < n[i][1]) or  (l[1] > n[i][0] and l[1] <= n[i][1]) :
            return True
    return False
sp = []
m = int(input())
p = 0
while p< m :
    n = int(input())
    sp.append('') 
    t = []
    c = []
    j = []
    for i in range(n) :
        l = list(map(int,input().split()))
        if sp[p] != 'IMPOSSIBLE' :
            if isbusy(j , l) :
                if isbusy(c , l) :
                    sp[p] = 'IMPOSSIBLE'
                else :
                    sp[p] = sp[p] + 'C'
                    c.append(l)
            else :
                sp[p] = sp[p] + 'J' 
                j.append(l)
    p += 1
for p in range(m) :
    print('Case #' + str(p+1) + ': ' + sp[p])
