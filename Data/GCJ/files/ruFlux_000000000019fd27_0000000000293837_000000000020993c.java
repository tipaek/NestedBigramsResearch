package com.default;

import java.util.Hashtable;
import java.util.Scanner;

class Vestigium
{
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);
        int T=sc.nextInt();
        for(int z=1;z<=T;z++)
        {

            int rn=sc.nextInt();
            int rmat[][]=new int[rn][rn];

            for(int i=0;i<rn;i++)
            {
                for(int j=0;j<rn;j++)
                {
                    rmat[i][j]=sc.nextInt();
                }
            }

            int k=traceCal(rmat,rn);

            int r=rows(rmat,rn);

            int c=cols(rmat,rn);

            System.out.println("Case #" +z+ ":"+" " +k+ " " +r+ " " +c); //Case #1: 4 0 0
        }
    }

    public static int traceCal(int arr[][],int rn)
    {
        int sum=0;
        for(int i=0;i<rn;i++)
        {
            for(int j=0;j<rn;j++)
            {
                if(i==j)
                    sum=sum+arr[i][j];
            }
        }
        return sum;
    }
    public static int rows(int arr[][],int rn)
    {
        Hashtable<Integer,Integer> h=new Hashtable<>();
        int count=0;
        for(int i=0;i<rn;i++)
        {
            int row[]=arr[i];
            for(int j=0;j<row.length;j++)
            {
                if(h.containsKey(row[j]))
                {
                    count++;
                    break;
                }
                else
                {
                    h.put(row[j],1);
                }
            }
            h.clear();
        }
        return count;
    }

    public static int cols(int arr[][],int rn)
    {
        Hashtable<Integer,Integer> h=new Hashtable<>();
        int count=0;
        for(int i=0;i<rn;i++)
        {
            for(int j=0;j<rn;j++)
            {
                int ele=arr[j][i];
                if(h.containsKey(ele))
                {
                    count++;
                    break;
                }
                else
                {
                    h.put(ele,1);
                }
            }
            h.clear();
        }
        return count;
    }
}