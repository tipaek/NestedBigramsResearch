not=int(input())
y=1
while y<=not:
	os=input()
	lo=len(s)
	i=0
	rbtc=0
	result=""
	j=int(os[i])
	while(j>0):
		result+="("
		j-=1
		rbtc+=1
	result+=os[i]
	i+=1
	while i<lo:
		on=int(os[i])
		po=int(os[i-1])
		diff=po-on
		while diff>0:  
			result+=")"
			rbtc-=1
			diff-=1
		while diff<0:
			result+="("
			diff+=1
			rbtc+=1
		result+=s[i]
		i+=1
	  while rbtc>0:
	  
		result+=")"
		rbtc-=1
	sol="Case #"+str(y)+":"+" "+result;
	print(sol)		
	y+=1
