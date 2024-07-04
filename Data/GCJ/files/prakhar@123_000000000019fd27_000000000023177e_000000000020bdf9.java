T = int(input())

for t in range(T):
    C = []
    J = []
    N = int(input())
    r = ""
    for i in range(N):
        S,E = map(int,input().split())
        if len(C)==0 or (not any([(E>s and S<e) for s,e in C])):
            C.append([S,E])
            r += "C"
        elif len(J)==0 or (not any([(E>s and S<e) for s,e in J])):
            J.append([S,E])
            r += "J"
        else:
            r = "IMPOSSIBLE"
            break
    print("Case #"+str(t+1)+": "+r)