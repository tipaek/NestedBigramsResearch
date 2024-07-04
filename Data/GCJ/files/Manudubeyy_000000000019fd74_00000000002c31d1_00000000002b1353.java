#include <iostream>
#include <string>


using namespace std;

int main()
{
    int cases;
    int num;
    int i;
    cin >> cases;
    string last = "";
   
   
    for(i = 0;i < cases;i++)
    {
          cin >> num;
     last = last +  "Case #" + std::to_string(i+1) + ": \n";  
     
     if(num == 1)
     {
          last = last + "1 1\n";
         
     }
     
     else if (num == 2)
     {
          last = last + "1 1\n2 1\n";
     }
     
     else if (num == 3)
     {
           last = last + "1 1\n2 1\n2 2\n";

     }
     
      else if (num == 4)
     {
         last = last + "1 1\n2 1\n2 2\n3 3\n";
     }
     
      else if (num == 5)
     {
last = last + "1 1\n2 1\n2 2\n3 3\n4 4\n";

     }
     
      else if (num == 6)
     {
        last = last + "1 1\n2 1\n2 2\n3 3\n3 2\n";
   
     }
     
     else if (num == 7)
     {
         last = last + "1 1\n2 1\n2 2\n3 3\n3 2\n3 1\n";

     }
     
     else
     {
         last = last + "1 1\n2 1\n2 2\n3 3\n3 2\n3 1\n";
         
         int temp1 = 4;
         for (int temp2 = 0; temp2 <= num - 8; temp2++)
         {
             last = last +std::to_string(temp1)+ (" 1\n" );
             
             temp1++;
         }

         
         
     }
     
 }

std::cout << last << std::endl;

}