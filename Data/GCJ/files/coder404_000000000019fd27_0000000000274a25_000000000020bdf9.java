from sys import stdin,stdout
t=int(stdin.readline())

#def printMaxActivities(s , f ): 
    #n = len(f) 
    #print "The following activities are selected"
  
    # The first activity is always selected 
    #i = 0
    #print i, 
  
    # Consider rest of the activities 
    #for j in xrange(n): 
  
        # If this activity has start time greater than 
        # or equal to the finish time of previously 
        # selected activity, then select it 
        #if s[j] >= f[i]: 
           # print j, 
            #i = j */
for ll in range(t):
    N=int(stdin.readline())
    ti=[]
    inds={}
    for i in range(N):
        a=[int(x) for x in stdin.readline().split()]
        inds[tuple(a)]=i
        ti.append(a)
    times2=ti[:]
    ti.sort(key=lambda x :x[1])
    ans=["C" for i in range(N)]
    impossible1,impossible2=False,False
    j,c=0,0
    for i in range(N):
        if c<=ti[i][0]:
            ans[inds[tuple(ti[i])]]="C"
            c=ti[i][1]
        elif j<=ti[i][0]:
            ans[inds[tuple(ti[i])]]="J"
            j=ti[i][1]
        else:
            impossible1=True
            break
    if impossible1:
        ans=["C" for i in range(N)]
        times2.sort()
        c,j=0,0
        for i in range(N):
            if c<=times2[i][0]:
                ans[inds[tuple(times2[i])]]="C"
                c=times2[i][1]
            elif j<=times2[i][0]:
                ans[inds[tuple(times2[i])]]="J"
                j=times2[i][1]
            else:
                impossible2=True
                break 
    ans="IMPOSSIBLE" if impossible1 and impossible2 else "".join(ans)
    print("Case #{0}: {1}".format(ll+1,ans))
