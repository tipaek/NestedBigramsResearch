using System;
using System.Collections.Generic;
using System.Text;

namespace Google
{
    class Vestigium
    {
        static void Main(String[] args)
        {
            int test_cases = Convert.ToInt32(Console.ReadLine());
            for (int i = 0; i < test_cases; i++)
            {
                int size = Convert.ToInt32(Console.ReadLine());
                List<List<string>> list = new List<List<string>>();
                int row = 0, col = 0, trace = 0, counter = 0;
                for(int j = 0; j<size; j++)
                {
                    List<string> line = new List<string>(Console.ReadLine().Split(" "));
                    HashSet<string> set = new HashSet<string>(line);
                    list.Add(line);
                    trace += Convert.ToInt32(line[counter++]);
                    row += (size - set.Count) > 0 ? 1 : 0;
                }
                for (int j = 0;j<size; j++)
                {
                    HashSet<int> set = new HashSet<int>();
                    list.ForEach(x => set.Add(Convert.ToInt32(x[j])));
                    col += (size - set.Count) > 0 ? 1 : 0;
                }
                Console.WriteLine($"Case #{i}: {trace} {row} {col}");
            }
        }
    }
}
