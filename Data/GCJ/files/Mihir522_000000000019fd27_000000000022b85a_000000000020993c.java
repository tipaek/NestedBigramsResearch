from sys import stdin, stdout

t = int(stdin.readline().strip())

resutl = []

for i in range(t):
	string = stdin.readline().strip()

	lstring = list(string)

	nd = 0

	for j in range(len(lstring)):
	index = 0
	while(string[index] == '1'):
	    lstring.insert(index, '(')
	    lstring.insert(index+2, ')')
	    index == 1

	print(lstring)