t = int(input())
for i in range(0, t):
    size = int(input())
    times = []
    for i in range(0, size):
        splt = input().split(" ")
        add = [int(splt[0]), int(splt[1])]
        times.append(add)
        times.sort()
        c_end = -1
        j_end = -1
        ret = ""
    for j in range(0, size):
        if times[j][0] >= c_end and ret != "IMPOSSIBLE":
            c_end = times[j][1]
            ret += "C"
        elif times[j][0] >= j_end and ret != "IMPOSSIBLE":
            j_end = times[j][1]
            ret += "J"
        else:
            ret = "IMPOSSIBLE"
    print(ret)