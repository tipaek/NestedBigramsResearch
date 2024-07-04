def solve():
    n, k = map(int, input().split())
    res = "POSSIBLE\n"
    ver = []
    for m in range(n):
        ver.append([])
        
    for i in range(n):
        diag = max(k-n, min(n, k-(n-i-1)))
        row = [0] * n
        poss = True
        k -= diag
        row[i] = diag
        ver[i].append(diag)
        
        for j in range(n):
            if row[j] == 0:
                flag = True
                for x in range(1, n+1):
                    if x not in row and x not in ver[j]:
                        row[j] = x
                        ver[j].append(x)
                        flag = False
                        break
                if flag:
                    poss = False
                    break        
        res += " ".join(str(i) for i in row) + "\n"
        print(" ".join(str(i) for i in row))
    return res[:-1] if poss else "IMPOSSIBLE"

t = int(input())
for i in range(1, t+1):
    s = solve()
    print("Case #{}: {}".format(i, s))