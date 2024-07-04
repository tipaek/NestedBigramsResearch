def canBeGivenTo(k, el):
    # check end time of person is less than start time of task
    if k <= el[1]:
        return True
    return False


def schedule(arr, j, c, ans):
    # all tasks assigned successfully
    if len(arr) == 0:
        return True
    if canBeGivenTo(j, arr[0]):
        # store index, job assigned to person
        ans.append([arr[0][0], 'J'])
        # update j with end time of current task
        j = arr[0][2]
        # recurse through remaining tasks
        return schedule(arr[1:], j, c, ans)

    if canBeGivenTo(c, arr[0]):
        # store index, job assigned to person
        ans.append([arr[0][0], 'C'])
        # update j with end time of current task
        c = arr[0][2]
        # recurse through remaining tasks
        return schedule(arr[1:], j, c, ans)

    # if current task cannot be assigned to J or C
    return False


T = 0
try:
    T = int(input())
except EOFError as e:
    pass

for _ in range(T):
    n = int(input())
    arr = []
    for i in range(n):
        # read start and end time
        s, e = map(int, input().split())
        # store index, start, end
        arr.append([i, s, e])
    # sort array by end time.
    arr.sort(key=lambda x: x[1])
    ans = []
    res = schedule(arr, 0, 0, ans)
    if res:
        # sort ans by index
        ans.sort()
        print("Case #{}: {} ".format(_ + 1, ''.join(a[1] for a in ans)))
    else:
        print("Case #{}: {} ".format(_ + 1, "IMPOSSIBLE"))




