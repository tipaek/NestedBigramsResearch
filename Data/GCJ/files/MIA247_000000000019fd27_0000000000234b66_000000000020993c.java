from collections  import Counter

n= int(input())
results = []

for i in range(n):
    d = int(input())

    flist= []
    k=0
    r=0
    c=0

    for j in range(d):
        list_row = list(map(int,input().split()))

        flist.append(list_row)

        duplist = [item for item,count in Counter(list_row).items() if count > 1 ]

        if len(duplist) >= 1:
            r+=1


    for numbers in range(len(flist)):
        k+=flist[numbers][numbers]

    list_column = []

    flist_new = [num for list_ in flist for num in list_]

    for thing in range(d):
        tl = []
        index = thing

        for a in range(d):
            tl.append(flist_new[index])
            index+=d

        list_column.append(tl)

        duplistC = [item for item,count in Counter(tl).items() if count > 1]
        if len(duplistC) >= 1:
            c+=1

    tl2 = []
    tl2.append(i+1)
    tl2.append(k)
    tl2.append(r)
    tl2.append(c)
    results.append(tl2)
for list_ in results:
    print("Case #{}: {} {} {}".format(list_[0], list_[1], list_[2],list_[3]))