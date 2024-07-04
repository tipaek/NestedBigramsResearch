firstLine = input().split()
T = int(firstLine[0])
B = int(firstLine[1])
if (B%2 == 0):
    halfB = B//2
else:
    halfB = B//2 + 1

for testCase in range(T):
    bits = [0 for i in range(B)]
    counter = 1
    same = 0
    diff = 0
    sameFound = False
    diffFound = False
    
    while True:
        for i in range(1, halfB+2):
            if (counter == 1):
                print(i)
                bits[i-1] = int(input())
                print(B+1-i)
                bits[B-i] = int(input())
                
            elif ((counter % 10) == 1):
                print(same)
                tempSame = int(input())
                print(diff)
                tempDiff = int(input())
                
                if (tempSame == bits[same-1]): ##not inverted, ignoring nothing happens case
                    if (tempDiff != bits[diff-1]): ##flipped
                        for j in range(halfB+1):
                            temp = bits[j]
                            bits[j] = bits[B-1-j]
                            bits[B-1-j] = temp
                
                elif (tempDiff == bits[diff-1]):##both
                    for j in range(halfB+1):
                        temp = bits[j]
                        bits[j] = bits[B-1-j]
                        bits[B-1-j] = temp
                            
                    for j in range(i-1):
                        if (bits[j] == 0):
                            bits[j] = 1
                        elif (bits[j] == 1):
                            bits[j] = 0

                        if (bits[B-1-j] == 0):
                            bits[B-1-j] = 1
                        elif (bits[B-1-j] == 1):
                            bits[B-1-j] = 0
                            
                else: #inverted
                    for j in range(i-1):
                        if (bits[j] == 0):
                            bits[j] = 1
                        elif (bits[j] == 1):
                            bits[j] = 0

                        if (bits[B-1-j] == 0):
                            bits[B-1-j] = 1
                        elif (bits[B-1-j] == 1):
                            bits[B-1-j] = 0
                
                i -= 2
                
                
            else:
                print(i)
                bits[i-1] = int(input())
                print(B+1-i)
                bits[B-i] = int(input())
            
            if (not sameFound):
                if (bits[i-1] == bits[B-i]):
                    same = i
                    sameFound = True
            if (not diffFound):
                if (bits[i-1] != bits[B-i]):
                    diff = i
                    diffFound = True
            
            counter += 2
        
        outString = ""
        for i in range(B):
            outString += str(bits[i])
        print(outString)
        hopeItsY = input()
        if (hopeItsY == "Y"):
            break
        else:
            continue
                            