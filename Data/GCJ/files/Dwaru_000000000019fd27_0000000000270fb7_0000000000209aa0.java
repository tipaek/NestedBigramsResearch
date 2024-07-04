using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace indicium
{
    class Program
    {
        static void Main(string[] args)
        {
            int t = Convert.ToInt32(Console.ReadLine());

            while(t--!=0)
            {
                string s = Console.ReadLine();
                int cas=1;
                int n = Convert.ToInt32(s.Split(' ')[0]);
                int rtrace = Convert.ToInt32(s.Split(' ')[1]);
                int f = 1;
               for (int i = 1; i <= n; i++)
                {
                    if (rtrace == n*i)
                    {
                        f = 0;
                    }
                }
               /* switch(n)
                {
                    case 2: if(rtrace==2 || rtrace==4)
                               f=0;
                          break;
                    case 3: if(rtrace==3 || rtrace==6 || rtrace==9)
                               f=0;
                          break;
                    case 4: if(rtrace==4 || rtrace==8 || rtrace==12|| rtrace==16)
                               f=0;
                          break;
                    case 5: if(rtrace==5 || rtrace==10|| rtrace==15|| rtrace==20|| rtrace==25)
                               f=0;
                          break;      
                }*/
                
                if (f == 0)
                    Console.WriteLine("Case #"cas ": POSSIBLE");
                else
                    Console.WriteLine("Case #"cas ": IMPOSSIBLE");

                cas++;
            }
            Console.Read();
        }
    }
}
