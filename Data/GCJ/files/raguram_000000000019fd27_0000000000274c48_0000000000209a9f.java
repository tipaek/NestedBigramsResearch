for i in range(int(input())):
    s=input()
    n=int(s)
    ss=''
    temp=''
    for j in range(len(s)):
        try:    
            if(s[j]!=s[j+1]):
                temp+=str(s[j])
                for k in range(int(s[j])):
                    ss+='('
                ss+=temp
                for k in range(int(s[j])):
                    ss+=')'
                temp=''
            else:
                temp+=s[j]
            
        except:
            temp+=str(s[j])
            for k in range(int(s[j])):
                ss+='('
            ss+=temp
            for k in range(int(s[j])):
                ss+=')'
            temp=''

  
    l=list(ss)
    l1=list(ss)
    #print(len(l))
    co=0
    while(co<len(l)):
        for j in range(len(l)):
            try:
                if(l1[j]==')' and l1[j+1]=='('):
                    #print(l1[j])
                    del l1[j]
                    #print(l1[j])
                    del l1[j]
                    #print(l1)
                    break
            
            except:
                None
        co+=1
        #print(co)
    ss=''
    for k in l1:
        ss+=str(k)

    
        
    print("Case #{}: {}".format(i+1,ss))