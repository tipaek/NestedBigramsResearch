T = int(input())
for i in range(1,T+1):
    line=int(input())
    number =[]
    result = ['CJ']
    for a in range(line):
        start_end = list(map(int,input().split()))
        number.append(start_end)
    #sorting
    h=1
    #number.sort(key = lambda x: x[0])
    c_s = number[0][0]
    c_e = number[0][1]
    j_s = number[1][0]
    j_e = number[1][1]
    for a in range(line):
        if h==0:
            break
        for b in range(2,line):
            s = number[b][0]
            e = number[b][1]
            if line==2:
                break
            elif(c_e==s or (c_s>s and s<c_e) or (c_s<s and s>c_e)):
                result.append('C')
                c_e=e
                c_s=s
                if b==line-1:
                    h=0
                    break
                continue
            elif(j_e==s or (j_s>s and s<j_e) or (j_s<s and s>j_e)):
                result.append('J')
                j_e=e
                j_s=s
                if b==line-1:
                    h=0
                    break
                continue
            else:
                result=['IMPOSSIBLE']
                h=0
                break
    result="".join(x for x in result)
    i=str(i)
    print("Case #"+i+":",result)
    