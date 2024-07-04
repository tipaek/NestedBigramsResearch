cases = int(input());
for q in range (1,(cases+1)):
    s = input();
    s1 = '';
    i = 0;
    while i<len(s):
        if(s[i]=='0'):
            s1=s1+'0';
            i++;
        else:
            count=0;
            for j in range(i+1,len(s)):
                if(s[j]=='1'):
                    count++;  
                else:
                    break;
            s1 = s1 + '('*(count+1) + '1'*(count+1) + ')'*(count+1);
            i = i + count+1;
        print("Case #{}: {}".format(q,s1))
        
