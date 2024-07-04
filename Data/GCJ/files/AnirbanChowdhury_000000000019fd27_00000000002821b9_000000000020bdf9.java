def solve(task_list):
	res=[]
	res.append('J')
	w='jam'
	jam=int(task_list[0][0][1])
	cam=0
	for i in range(1,len(task_list)):
		if w=='cam':
			if int(task_list[i][0][0])>=cam:
						res.append('C')
						cam=int(task_list[i][0][1])
						w='cam'
						
			elif int(task_list[i][0][0])>=jam:
						res.append('J')
						jam=int(task_list[i][0][1])
						w='jam'
						
		else:
			if int(task_list[i][0][0])>=jam:
						res.append('J')
						jam=int(task_list[i][0][1])
						w='jam'
						
			elif int(task_list[i][0][0])>=cam:
						res.append('C')
						cam=int(task_list[i][0][1])
						w='cam'
						
				
			
			
	return res
		
		


testcases=int(input())
for t in range(1,testcases+1):
	n_task=int(input())
	task_list=[]
	for i in range(n_task):
		task=list(map(int,input().split()))
		task_list.append([task,i])
	task_list.sort()
	res=solve(task_list)
	if len(res)==n_task:
		for i in range(n_task):
			task_list[i].pop(0)
		for i in range(n_task):
			task_list[i]=res[int(*task_list[i])]	
	str_lst=''
	
	if len(res)<n_task:
		str_lst="IMPOSSIBLE"
	else:	
		for i in range(len(task_list)):
			str_lst+=task_list[i]
		
	print("Case #{}: {}".format(t,str_lst))	