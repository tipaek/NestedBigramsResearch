num_tests = int(input())


def merge(last_num, str1, str2):
    #str 1 has parenthesis
    #str 2 is just a number character
    num_cut = min(last_num, int(str2))
    
    #remove from left
    for i in range(num_cut):
        str1 = str1[:-1]
    
    paren_add = int(str2) - num_cut
    
    #add the next nums to left
    for i in range(paren_add):
        str1 += "("
    str1 += str2
    for i in range(int(str2)):
        str1 += ")"
    
    return str1
    


def initialize(input):
    if input == "0":
        return "0"
    string = ""
    for i in range(int(input)):
        string += "("
    string += input
    for i in range(len(input)):
        string += ")"
    return string

def get_depth(input):
    output = ""

    #get first num and add parenthesis, then number
    
    total = ""

    first = 0
    next = 1
    curr = ""
    while first < len(input):
        #initialize the current thing
        curr += initialize(input[first])
        last_num = int(input[first])
        while next < len(input) and input[next] != 0:
            curr = merge(last_num, curr, input[next])
            last_num = int(input[next])
            next += 1
        
        total += curr
        if next < len(input) and input[next] == "0":
            total += "0"
        first = next + 1
        next = first + 1
        curr = ""
    
    return total


responses = []

for i in range(num_tests):
    next_string = str(input())
    responses.append(get_depth(next_string))

for i in range(len(responses)):
    response = responses[i]
    print("Case #{}: {}".format(i+1, response))