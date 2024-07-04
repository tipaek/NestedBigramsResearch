t=int(input())
for i in range(t):
    s=input()
    ans=str()
    for j in range(len(s)):
        if(s[j]=='0'):
            ans+=s[j]
        else:
            if(j==0 or s[j-1]=='0'):
                ans+='(1'
            else:
                ans+='1'
            if(s[j]=='1' and j==len(s)-1):
                ans+=')'
                break
            if(s[j]==s[j+1]):
                ans+=""
            else:
                ans+=')'
                    
    print("Case #" + str(i+1) + ": " + ans)