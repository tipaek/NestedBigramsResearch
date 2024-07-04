from collections import Counter

#Jadi input berupa matrix n x n 
#4 x 4 Matrix

case = 0
x = int(input().strip())

for i in range(x):
    y = int(input().strip())
    
    #list untuk menyimpan matrix dalam bentuk list
    xlist = []
    #untuk menyimpan value yang different/berbeda
    hashlist = []
    diff = []
    
    k = 0         #jumlah trace / linear algebra
    r = 0         #jumlah duplicate row
    c = 0         #jumlah duplicate kolom




    #untuk memasukkan tiap input kedalam 1 list 
    for i in range(y):
        row = list(map(int,input().strip().split()))        
        #menghitung duplicate row
        xlist.append(row)
        hashDict = Counter(row) #dijadikan tuple karena list unhashable            
        dups_row = [item for item , count in hashDict.items() if count > 1 ]
        if len(dups_row) >= 1 :
            r+=1
        
    for i in range(y):
        #menghitung duplicate column
        col = [[xlist[j][i] for j in range(len(xlist))] for i in range(len(xlist[0]))]
        
        
        tupCol = col[i]
        hashDict2 = Counter(tupCol)
        dups_col = [item for item , count in hashDict2.items() if count > 1 ]
        if len(dups_col) >= 1 :
            c+=1
    
    for i in range(y):
        for j in range(y):
            if (i == j): 
                k += xlist[i][j] 

    
    
        
    # setxList = set(xlist)
    
    # for val in xlist :
    #     if val not in diff :
    #         diff.append(val)
    #         setxList.add(val)

    
    case+=1

    print('Case #{}: {} {} {}'.format(case,k,r,c))