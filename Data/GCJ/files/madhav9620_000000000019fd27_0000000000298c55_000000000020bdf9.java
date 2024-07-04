using System;
using System.Linq;
using System.Collections.Generic;

namespace Parenting
{
    class Program
    {
        class Task
        {
            public short start;
            public short finish;
            public short index;
            public Task(short start, short finish, short index)
            {
                this.start = start;
                this.finish = finish;
                this.index = index;
            }
        }

        static void Main(string[] args)
        {
            // Read no. of test cases
            int T = Convert.ToInt32(Console.ReadLine());
            for (int t = 0; t < T; ++t)
            {
                bool possible = true;
                int noOfTasks = Convert.ToInt32(Console.ReadLine());
                SortedDictionary<string, Task> tasks = new SortedDictionary<string, Task>();

                // Read all tasks
                for (short i = 0; i < noOfTasks; i++)
                {
                    short[] timeframe = Array.ConvertAll(Console.ReadLine().Split(), short.Parse);
                    string key = $"{String.Format("{0:0000}", timeframe[0])}{String.Format("{0:0000}", timeframe[1])}0";
                    if (tasks.ContainsKey(key))
                    {
                        key = $"{string.Format("{0:0000}", timeframe[0])}{string.Format("{0:0000}", timeframe[1])}1";
                        if (tasks.ContainsKey(key))
                        {
                            possible = false;
                            break;
                        }
                    }

                    tasks.Add(key, new Task(timeframe[0], timeframe[1], i));
                }

                string[] order = new string[noOfTasks];
                if (possible)
                {
                    Task C = new Task(-1, -1, -1);
                    Task J = new Task(-1, -1, -1);

                    // Loop over them and figure stuff out
                    foreach (var task in tasks.Values)
                    {
                        if (task.start >= C.finish)
                        {
                            C = task;
                            order[task.index] = "C";
                        }
                        else if (task.start >= J.finish)
                        {
                            J = task;
                            order[task.index] = "J";
                        }
                        else
                        {
                            possible = false;
                            break;
                        }
                    }
                }

                Console.WriteLine($"Case #{t + 1}: {(possible ? string.Join("", order) : "IMPOSSIBLE")}");
            }
        }
    }
}