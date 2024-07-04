T = int(input())
for t in range(T):
    N, K = [int(s) for s in input().split()]
    if K % N != 0:
        print('Case #{}: IMPOSSIBLE'.format(t + 1))
    else:
        print('Case #{}: POSSIBLE'.format(t + 1))
        nums = []
        dg = int(K/N)

        for i in range(N):
            for j in range(N):
                print(dg, end=' ')
                dg += 1
                if dg > N:
                    dg = 1
            dg -= 1
            if dg < 1:
                dg = N
            print()