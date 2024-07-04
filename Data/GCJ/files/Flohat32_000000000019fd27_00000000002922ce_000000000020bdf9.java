t = int(input()) #read 1st line of input file, 1st line corresponds to an int
for case in range(1, t + 1):
    event = int(input())
    J = 0
    C = 0
    res = ""
    events = [ [int(i) for i in input().split(" ")] for e in range(event)]
    ev_sort = list(zip(events, range(len(events))))    
    ev_sort = sorted(ev_sort, key=lambda event: event[0][0])
    for [nums,ix] in ev_sort:
        if nums[0]>=C:
            C = nums[1]
            res += "C"
        elif nums[0]>=J:
            J = nums[1]
            res += "J"
        else:
            res = "IMPOSSIBLE"
            break 
    if res!="IMPOSSIBLE":
        res = sorted(list(zip([e[1] for e in ev_sort], [e for e in res])))
        res_tmp = ""
        for e in res:
            res_tmp += e[1]
        res = res_tmp
    print("Case #{}: {}".format(case, res))
