t=int(input())
for _ in range(t):
    s=input()
    strr=""
    prev=-1
    for i in s:
        if int(i)==0:
            if prev==1:
                strr+=')'
            strr+=i
            prev=0
        else:
            if prev==int(i):
                strr+=i
            else:
                strr+="("
                strr+=i
                prev=1
    if prev==1:
        strr+=')'
    z="Case #"
    z+=str(_+1)
    z+=":"
    print(z,strr)