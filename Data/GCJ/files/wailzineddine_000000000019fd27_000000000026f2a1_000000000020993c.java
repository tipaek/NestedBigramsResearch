#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

    bool rep(int mat[],int n) {
        for(int i=0;i<n;i++){
            for(int j=i+1;j<n;j++){
                if(mat[i]==mat[j])return true;
            }
        }return false;
    }
        void solve4(){
        int n;
        scanf("%d",&n);
         int mat[n][n];
         int colone[n][n];
        int  sum=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                scanf("%d",&mat[i][j]);
                colone[j][i]=mat[i][j];
                if(i==j)sum=sum+mat[i][j];
            }
        } int h=0,v=0,h1,v1;
        for(int i=0;i<n;i++){
            if(rep(mat[i],n)==true)h++;
            if(rep(colone[i],n)==true)v++;
        }


        printf("%d %d %d\n",sum,h,v);
    }




int main()
{
   int test;
   scanf("%d",&test);
    printf("\n");
        int i=0;
        while(i<test){
            printf("Case #%d: ",(i+1));
            solve4();
            i++;
        }
    return 0;
}

