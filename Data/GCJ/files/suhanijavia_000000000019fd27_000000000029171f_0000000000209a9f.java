a=int(input())
for k in range(1,a+1):
    b=input()
    c=''
    n=0
    
    if len(b)<101 and len(b)>0:
        for i in range(len(b)):
            if(n==int(b[i])):
                c+=b[i]
            elif(n>int(b[i])):
                for j in range(n-int(b[i])):
                    c=c+')'
                    n=n-1
                c=c+b[i]
            else:
                for j in range(int(b[i])-n):
                    c=c+'('
                    n=n+1
                c=c+b[i]
            if(i==len(b)-1):
                for i in range(n):
                    c=c+')'
        print('Case #{}: '.format(k)+c)