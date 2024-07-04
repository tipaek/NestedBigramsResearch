tc = int(input())

for case in range(tc):
    nums = input()
    brac = 0
    nest = ""
    if(len(nums) == 1):
        nest += "("*int(nums) + nums + ")"*int(nums)
    else:
        for index,n in enumerate(nums):
            if(index == 0):
                lbrac = int(n)
                rbrac = int(n)-int(nums[index+1])
            elif(index == (len(nums)-1)):
                lbrac = int(n)-int(nums[index-1])
                if(lbrac > 0):
                    nest += "("*lbrac
                    brac+=lbrac
                rbrac = brac
                nest+=n
                nest += ")"*rbrac
                break

            else:
                lbrac = int(n)-int(nums[index-1])
                rbrac = int(n)-int(nums[index+1])
            if(lbrac>0):
                nest += "("*lbrac
                brac+=lbrac
            nest += n
            if(rbrac>=0):
                nest += ")"*rbrac
                brac -= rbrac

  
    #print('Case # %d : %d '% (case,nest))
    print('Case # ' + str(case+1) + ': ' + str(nest))