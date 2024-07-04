def trace(arr,size):
    tot=0
    for i in range(size):
        tot += arr[i][i]
    return tot 

def col(arr,size):
    count =0
    for i in range(size) :
        val = 0
        list=[]
        for j in range(size):
           #val= arr[i].count(arr[i][i])
            list.append(arr[j][i])
        for h in range(size):
            val = list.count(list[h])
           
        if val>1:
            count+=1
            val =0
    return count

def row(arr,size):
    count =0
    for i in range(size) :
        val = 0
        for j in range(size):
           val= arr[i].count(arr[i][i])
        
           
        if val>1:
            count+=1
            val =0
    return count 
num_lines = sum(1 for line in open('file.txt'))
with open('file.txt', 'r') as f:
    numOfMat = [int(x) for x in next(f).split()]
    for w in range(num_lines-2):
        array = []
        matSize= [int(x) for x in next(f).split()]
        
        for i in range(matSize[0]):
            array.append([int(x) for x in next(f).split()])
        # print(array)
        k= trace(array,matSize[0])
        r= row(array,matSize[0])
        c= col(array,matSize[0])

        for h in numOfMat:
            print("Case #{}, {} {} {}".format(h,k,r,c))
            