for z in range(1, int(input()) + 1):
    n=int(input())
    s=""
    j = []
    c = []
    o=0
    sc=[]
    for p in range(n):
        sc.append(list(map(int,input().split())))
    sc.sort()
    for r in range(len(sc)):
        jco=0
        cco=0
        if len(j)!=0:
            for i in range(len(j)):
                if (sc[r][0]<=j[i][0] and sc[r][1]<=j[i][0]) or (sc[r][0]>=j[i][1] and sc[r][1]>=j[i][1]):
                    jco=0
                else:
                    jco=1
                    break
        wq=""
        if jco==0:
            j.append(sc[r])
            s=s+"J"
        else:
            if len(c)!=0:
              for k in range(len(c)):
                  if (sc[r][0] <= c[k][0] and sc[r][1] <= c[k][0]) or (sc[r][0] >= c[k][1] and sc[r][1] >= c[k][1]):
                      cco = 0
                  else:
                      cco = 1
                      break

            if cco==0:
                c.append(sc[r])
                s=s+"C"
            else:
                print("Case #{}: {}".format(z,"IMPOSSIBLE"))
                o=1
                break
    if o!=1:
        print("Case #{}: {}".format(z, s))


