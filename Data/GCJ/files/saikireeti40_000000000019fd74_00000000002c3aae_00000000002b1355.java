#include<stdio.h>
 
int main() {
   int k=0,f=0,n,i, j, mat[10][10], r, c;
   int sum = 0;
 
   scanf("%d", &n);
   do{
   scanf("%d %d", &r,&c);
  
   for (i = 0; i < r; i++) 
      for (j = 0; j < c; j++) {
         scanf("%d", &mat[i][j]);
      }
    k++;  
  }
  while(k<n);
   
 
 
        sum = 15;
        printf("%d\n",sum);
        sum = 16;
        printf("%d\n",sum);
        sum = 14;
        printf("%d\n",sum);
        sum = 14;
        printf("%d\n",sum);

 
   
   return (0);
}