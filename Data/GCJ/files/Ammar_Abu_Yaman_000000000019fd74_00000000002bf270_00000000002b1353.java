tt = int(input())
qv = 1
while  qv <= tt:

  counter = 0
  
  num = int(input())
  
  if  num == 501:
  
    print("Case #{}:".format(qv))
    
    for i in range(1 , 500):
    
      if  i == 3:
      
        print(3 , 2)
        
        print(3 , 1)
        
      else:
      
        print(i , 1)   
        
  else:
  
    print("Case #{}:".format(qv))
    
    for i in range(1 , num + 1):
    
      print(i , 1)  
      
  qv += 1