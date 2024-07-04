import java.io.*;
class Main
{
public static void main(String args[])
{
Scanner sc=new Scanner(System.in);
int T=sc.nextInt();
for(int i=0;i<size;i++)
{
for(int j=0;j<size;j++)
{
mat[i][j]=sc.nextInt();
}
} 

for(int i=0;i<size;i++)
{
for(int j=0;j<size;j++)
{
    System.out.print(mat[i][j]);
    }
    System.out.Println();
}

int k=trace(mat,size);
int r=row_duplicate(mat,size);
int c=col_duplicate(mat,size);
System.out.Print("Case #" +z+ ";"+" " +k+ " " +r+ " " +c);
}
}

public static int row_duplicate(int arr[],int size)
{
int sum=0;
for(int i=0;i<size;i++)
{
for(int j=0;j<size;j++)
{
if(i==j)
sum=sum+arr[i][j];
}
}
return sum;
}


public static int row_duplicate(int arr[][],int size)
{
Hashtable<Integer,Integer>h=new Hashtabled<>();
int count=0;
for(int i=0;i<size;i++)
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

public static int col_duplicate(int arr[][],int size)
{
Hashtable<Integer,Integer>h=new Hashtable<>();
int count=0;
for(int i=0;i<size;i++)
{
for(int j=0;j<size;j++)
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





























































