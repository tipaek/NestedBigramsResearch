def solve(A, N):
    # compute trace
    trace = 0
    for k in range(N):
        trace += A[k][k]

    # compute num of dupe rows
    dupe_rows = 0
    j = 0
    k = 0
    while j < N:
        k =  0
        while k < N - 1:
            l = k + 1
            while l in range(k+1, N):
                if A[j][k] == A[j][l]:
                    dupe_rows += 1
                    l = N
                    k = N - 1
                else:
                    l += 1
            k += 1
        j += 1
        
    # compute num of dupe cols
    dupe_cols = 0
    j = k = 0
    while j < N:
        k = 0
        while k in range(N-1):
            l = k + 1
            while l in range(k+1, N):
                if A[k][j] == A[l][j]:
                    dupe_cols += 1
                    l = N
                    k = N - 1
                else:
                    l += 1
            k += 1
        j += 1
        
    return (trace, dupe_rows, dupe_cols)


if __name__ == "__main__":
    T = int(input())
    for i in range(T):
        N = int(input())
        c = N
        r = N
        A = []
        for j in range(N):
            row = [int(s) for s in input().split(" ")]
            A.append(row)

        ans = solve(A, N)
        print("Case #{}: {} {} {}".format(str(i+1), str(ans[0]), str(ans[1]), str(ans[2])))