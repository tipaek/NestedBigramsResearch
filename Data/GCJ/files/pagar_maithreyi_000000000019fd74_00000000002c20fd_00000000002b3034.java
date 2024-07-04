def match(first, second): 

	if len(first) == 0 and len(second) == 0: 
		return True

	if len(first) > 1 and first[0] == '*' and len(second) == 0: 
		return False

	if (len(first) > 1 and first[0] == '?') or (len(first) != 0
		and len(second) !=0 and first[0] == second[0]): 
		return match(first[1:],second[1:]); 

	if len(first) !=0 and first[0] == '*': 
		return match(first[1:],second) or match(first,second[1:]) 

	return False

 
def test(first, second): 
    t=0
    if(match(first, second)):
        t=1 
    else:
        t=0
    return t
	
t=int(input())
for k in range(t):
    n=int(input())
    flag = False
    x1,x2,ss=[],[],[]
    for i in range(n):
        s = str(input())
        ss.append(s)
        x=s.split('*')
        x1.append(x[0])
        x2.append(x[1])
    x1.sort(key=len)
    x2.sort(key=len)
    c = x1[n-1]+x2[n-1]
    for i in range(n):
        t = test(ss[i],c) 
        if(t==1):
            flag = False
        else:
            flag=True
            print("Case #",k+1,": ","*","",sep="")
            break
    if(flag==False):
        print("Case #",k+1,": ",c,"",sep="")