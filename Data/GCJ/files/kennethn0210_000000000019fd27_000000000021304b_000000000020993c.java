a = int(input())
for i in range(a):
    n = int(input())
    mat = []
    for j in range(n):
        mat.append([])
        mat[j] = [int(x) for x in input().split()]
    k = 0
    for j in range(n):
        k += mat[j][j]
    r = 0
    for j in range(n):
        arr = []
        for jj in mat[j]:
            if(jj in arr):
                r+=1
                break
            else:
                arr.append(jj)
    c = 0
    for j in range(n):
        arr = []
        for l in range(n):
            if(mat[l][j] in arr):
                c+=1
                break
            else:
                arr.append(mat[l][j])
    print("Case #" + str(i+1) + ": " + str(k) + " " + str(r) + " " + str(c))