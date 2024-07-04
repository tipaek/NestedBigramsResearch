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
                //if (rtrace == (n * (n + 1)) / 2)
               //     f = 0;
                if (f == 0)
                    Console.WriteLine("Case #1: POSSIBLE");
                else
                    Console.WriteLine("Case #1: IMPOSSIBLE");

                cas++;
            }
            Console.Read();
        }
    }
}
