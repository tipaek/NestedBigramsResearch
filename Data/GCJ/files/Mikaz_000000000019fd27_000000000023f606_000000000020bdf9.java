from sys import stdin, stdout

t = int(stdin.readline().strip())

results = []

for i in range(t):
    string = stdin.readline().strip()

    lstring = list(string)

    nd = 0

    newl = []

    for x in lstring:
        if x == '1':
            newl.append('(')
            newl.append(x)
            newl.append(')')
            continue
        elif x == '2':
            for i in range(2):
                newl.append('(')
            newl.append(x)
            for i in range(2):
                newl.append(')')
            continue
        elif x == '3':
            for i in range(3):
                newl.append('(')
            newl.append(x)
            for i in range(3):
                newl.append(')')
            continue
        elif x == '4':
            for i in range(4):
                newl.append('(')
            newl.append(x)
            for i in range(4):
                newl.append(')')
            continue
        elif x == '5':
            for i in range(5):
                newl.append('(')
            newl.append(x)
            for i in range(5):
                newl.append(')')
            continue
        elif x == '6':
            for i in range(6):
                newl.append('(')
            newl.append(x)
            for i in range(6):
                newl.append(')')
            continue
        elif x == '7':
            for i in range(7):
                newl.append('(')
            newl.append(x)
            for i in range(7):
                newl.append(')')
            continue
        elif x == '8':
            for i in range(8):
                newl.append('(')
            newl.append(x)
            for i in range(8):
                newl.append(')')
            continue
        elif x == '9':
            for i in range(9):
                newl.append('(')
            newl.append(x)
            for i in range(9):
                newl.append(')')
            continue

        newl.append(x)

    str_newl = ''.join(newl)

    while ')(' in str_newl:
        list_snewl = str_newl.split(')(')
        str_newl = ''.join(list_snewl)

    results.append(str_newl)

for index in range(len(results)):
    print("Case #{}: {}".format(index + 1, results[index]))
