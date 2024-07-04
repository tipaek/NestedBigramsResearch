t = int(input())

def check(partner, m, n):
    temp = [0] + partner[:-1]
    
    for i in range(0,len(temp),2):
        if temp[i]<=m and temp[i+1]>=n:
            return 1
            
    return 0
    
for i in range(1, t+1):
    task, taskList, C, J, squence = int(input()), [], [], [], ""
    
    for j in range(task):
        m, n = [int(s) for s in input().split(" ")]
        
        if C==[] or C[-1]<=m or check(C, m, n):
            C.append(m)
            C.append(n)
            if squence == 'IMPOSSIBLE':
                continue
            else:
                squence += 'C'
            
        elif J==[] or J[-1]<=m or check(J, m, n):
            J.append(m)
            J.append(n)
            if squence == 'IMPOSSIBLE':
                continue
            else:
                squence += 'J'
            
        else:
            squence = 'IMPOSSIBLE'
            
    print("case #{}: {}".format(i, squence))