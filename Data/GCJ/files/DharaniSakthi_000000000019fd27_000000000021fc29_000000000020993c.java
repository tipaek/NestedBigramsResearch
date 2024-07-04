#include<stdio.h>
//#include<conio.h>
public static void main(){
    int t,n,i,j,k,s=0,r=0,c=0;
    scanf("%d",&t);
    for(i=1;i<=t;i++){
    scanf("%d",&n);    
    int a[n][n];
    for(j=0;j<n;j++)
    for(k=0;k<n;k++)
    scanf("%d",&a[j][k]);
    for(j=0;j<n;j++)
    s+=a[j][j];
    for(j=0;j<n;j++)
    for(k=0;k<n-1;k++)
    if(a[j][k]==a[j][k+1])r++;
    for(j=0;j<n-1;j++)
    for(k=0;k<n;k++)
     if(a[j][k]==a[j+1][k])c++;
     printf("Case #%d:\t%d\t%d\t%d\n",t,s,r,c);
    }
}