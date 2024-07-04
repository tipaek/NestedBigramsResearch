import java.io.*;;
import java.util.*;
public class Solution
{
    public static void main(String[] args) throws IOException
    {
        Scanner r = new Scanner(System.in);
        int cases = Integer.parseInt(r.nextLine());
        StringTokenizer st;
        int activities;
        for(int i = 1; i <= cases; i++)
        {
            activities = Integer.parseInt(r.nextLine());
            Activity[] sched = new Activity[activities];
            for(int i = 0; i < activities; i++)
            {
                st = new StringTokenizer(r.nextLine());
                sched[i] = new Activity(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            }
            ArrayList<Activity> cam = new ArrayList<Activity>();
            ArrayList<Activity> jam = new ArrayList<Activity>();
            String out = "";
            for(Activity a: sched)
            {
                if(fine(a, cam)) out = out + "C";
                else if(fine(a, jam)) out = out + "J";
                else
                {
                    out = "IMPOSSIBLE";
                    break;
                }
            }
            System.out.println("Case #" + i + ": " + out);
        }
    }
    public static boolean fine(Activity ac, ArrayList<Activity> person)
    {
        for(Activity a : person)
        {
            if(!((ac.start >= a.end && ac.end >= a.end) || (ac.start <= a.start && ac.end <= a.start))) return false;
        }
        return true;
    }

    
}

class Activity
{
    int start, int end, char owner;
    public Activity(int s, int e)
    {
        start = s;
        end = e;
        owner = 'N';
    }
}