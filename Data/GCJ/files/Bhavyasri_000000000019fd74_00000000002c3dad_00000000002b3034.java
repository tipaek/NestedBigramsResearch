testCase = int(input())
for io in range(1, testCase + 1):
    arr= []
    for _ in range(int(input())):
        arr.append(input().split('*'))
        ans = arr[0][1]
        for i in range(len(arr)-1):
            if len(ans) <= len(arr[i+1][1]):
                if arr[i][1] in arr[i+1][1]:
                    ans = arr[i+1][1]
                else:
                    ans = '*'
                    break
            else:
                if len(arr[i+1][1]) < len(ans):
                    if arr[i+1][1] in arr[i][1]:
                        continue
                    else:
                        ans = '*'
                        break

print("Case #{}: {}".format(io, ans))