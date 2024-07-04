nbtest = int(input())
numtest = 1
while numtest <= nbtest:
    s = input()
    n = int(s[0])
    r = '(' * n + str(n)
    i = 1
    while i <= len(s) - 1:
        n = int(s[i-1]) - int(s[i])
        if n >= 0:
            r += ')' * n + str(s[i])
        else:
            n = n * (-1)
            r += '(' * n + str(s[i])
        i += 1
    r += ')' * int(s[-1])
    print("Case #{}: {}".format(numtest, r))
    numtest += 1
