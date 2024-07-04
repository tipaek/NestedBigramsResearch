import java.io.*;
import java.util.*;
import java.lang.*;
public static void main(String args[])
{
    int s[];
    int e[];
    int res[];
    int t,n,j,k,count,a,i;
    Scanner scan=new Scanner(System.in);
    try
    {
        t=scan.nextInt();
        while(t>0) 
        {
            n=scan.nextInt();
            s=new Int(n);
            e=new Int(n);
            res=new Int(n);
            i=0;
            while(i<n)
            {
                s[i]=scan.nextInt();
                e[i]=scan.nextInt();
                i=i+1;
            }
            i=0;
            while(i<n)
            {
                j=0;
                k=0;
                while (j<(n-1))
                {
                    k=j+1;
                    while (k<n)
                    {
                        if (e[j]>e[k])
                        {
                            if (res[j]=="J")
                            {
                                if (res[k]=="J")
                                {
                                    System.out.print("case #");
                                    System.out.print(t);
                                    System.out.println(": IMPOSSIBLE");
                                    count=90;
                                    break;
                                }
                                else
                                {
                                    res[k]="C";
                                }
                            else if(res[j]=="C")
                            {
                                if(res[k]=="C")
                                {
                                    System.out.print("case #");
                                    System.out.print(t);
                                    System.out.println(": IMPOSSIBLE");
                                    count=90;
                                    break;
                                else
                                {
                                    res[k]="J";
                                }
                            else if (res[j]==null)
                            {
                                if(res[k]=="J")
                                {
                                    res[j]=="C";
                                }
                                else
                                {
                                    res[j]=="J";
                                }
                            }
                        else if (e[j]>s[k])
                        {
                            if (res[j]=="J")
                            {
                                if (res[k]=="J")
                                {
                                    System.out.print("case #");
                                    System.out.print(t);
                                    System.out.println(": IMPOSSIBLE");
                                    count=90;
                                    break;
                                }
                                else
                                {
                                    res[k]="C";
                                }
                            else if(res[j]=="C")
                            {
                                if(res[k]=="C")
                                {
                                    System.out.print("case #");
                                    System.out.print(t);
                                    System.out.println(": IMPOSSIBLE");
                                    count=90;
                                    break;
                                else
                                {
                                    res[k]="J";
                                }
                            else if (res[j]==null)
                            {
                                if(res[k]=="J")
                                {
                                    res[j]=="C";
                                }
                                else
                                {
                                    res[j]=="J";
                                }
                            }
                        else
                        {
                           k=k+1;
                        }
                    if(count==90)
                        break;
                    else
                        j=j+1;
                if (count==90)
                    break;
                else
                    i=i+1;
            if count==90:
                System.out.print();
            else:
                a=0
                System.out.print("Case #",t);
                System.out.print(": ")
                while (a<n)
                {
                    if (res[a]==null)
                    {
                        System.out.print("J");
                    }
                    else:
                        System.out.print(res[a]);
                    a++;
            t--;
    catch(Exception z):
        System.out.print("Error");