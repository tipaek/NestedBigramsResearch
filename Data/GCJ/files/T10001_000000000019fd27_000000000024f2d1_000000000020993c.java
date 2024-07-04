#include<iostream>
#include<math.h>

using namespace std;

int main()
{
    int t;
    int n;
    int trace = 0;
    int r = 0;
    int c = 0;
    int i;
    int j;
    int sum = 0;
    int temp[5][5];
    printf("enter number of test cases");
    std::cin >> t;
    printf("enter size of matrix");
    std::cin >> n;
    int matrix[n][n];
    for( i=0;i<n;i++){
        for( j=0;j<n;j++){
            std::cin >> matrix[i][j];
        }
    }
    
  for (int q =0;q<n;q++) {
     trace = trace + matrix[q][q]; 
  }  
  for(int i =0;i<n;i++) {
      for (int j=0;j<n;j++) {
          temp[i][j] = matrix[i][j];
          if(temp[i][j] == matrix[i][j+1]){
              r=r+1;
              break;
          }
      }
  }
  for(int i=0;i<n;i++) {
      for(int j=0;j<n;j++) {
          temp[i][j] = matrix[i][j];
          if(temp[i][j] == matrix[i][j+1]) {
              c=c+1;
              break;
          }
      }
  }

  printf("case#%d - %d  %d  %d \n",t,trace,r,c);
    
}