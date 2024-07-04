def solve(t,result,prev):
    if prev!=" and t!=":
        if int(prev)>int(t[0]):
            for i in range(int(prev)-int(t[0])):
                result=result+ ')'
    if t == '':
        for i in range(int(prev)):
            result = result + ')'
        return result
if int(t[0]) == 0:
    return solve(t[1:],result + t[0],t[0])
if int(t[0]) == 1:
    if prev != '':
        if int(prev) == 0:
            result = result + '(' + t[0]
        else:
            result = result + t[0]
    else:
        result = result + '(' + t[0]
    return solve(t[1:],result,t[0])
if int(t[0]) > 1:
    if prev != '':
        for i in range(int(t[0]) - int(prev)):
            result = result + '('
            result = result + t[0]
    else:
        for i in range(int(t[0])):
            result = result + '('
        result = result + t[0]
    return solve(t[1:],result,t[0])
t = int(input())
for m in range(0,t):
    k = input()
    print('Case #{}: {}'.format(m + 1,solve(k,'','')))
t=int(input())
for m in range(1,t+1):
    t1=int(input())
    li=[]
    for j in range(t1):
        li.append(list(map(int,input().split())))
    l=0
    kk=[]
    for i in range(len(li)):
        kk.append(li[i][i])
    l=sum(kk)
    r=0
    for k in range(len(li)):
        t2=set(li[k])
        if(len(t2)!=len(li[i])):
            r+=1
    c=0
    for h in range(len(li[0])):
        nli=[]
        for hh in range(t1):
            nli.append(li[hh][h])
        ns=set(nli)
    if(len(nli)!=len(ns)):
        c+=1
    print("Case #{}: {} {} {}".format(m,l,r,c))
    