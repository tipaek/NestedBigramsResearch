import java.io.*;
import java.util.*;
class Solution {
	public static void main (String[] args) {
		Scanner sc=new Scanner(System.in);
		int test=sc.nextInt();
		for(int i=1;i<=test;++i){
		    long n=sc.nextInt();
		    System.out.println("Case #"+i+":");
		    long sum=1,temp=1,r=1,c=1;
		    System.out.println(r+" "+c);
		    c++;
		    while(sum+temp<=n){
		        sum=sum+temp;
		        temp++;
		        r++;
		        System.out.println(r+" "+c);
		        
		    }
		    c--;
		    while(sum!=n){
                sum++;
		        
		        System.out.println(r+" "+c);
		        r++;
		    }
		}
	