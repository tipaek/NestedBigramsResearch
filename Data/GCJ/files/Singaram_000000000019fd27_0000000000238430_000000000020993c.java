import java.util.Scanner;

class solution{
    public vector<int> latinsqcheck(int size){
        vector<int> result;
        int values[][]=new int[size][size];
        int k=0,r=0,c=0;
        Scanner sc=new Scanner(System.in);
        
        for(int j=0;j<size;j++){
            for(int i=0;i<size;i++){
                values[i][j]=sc.nextInt();
            }
        }
    
        int j=0,x,y;
        while(j<size){
            x=0;
            while(x<size){
                int temp=values[x][j];
                int ref=r;
                y=x+1;
                while(y<size){
                    if(temp==values[y][j]){r++;break;}
                    y++;
                }
                if(r<ref)break;
                x++;   
            }
        
            j++;
        }
        j=0;
        while(j<size){
            x=0;
            while(x<size){
                int temp=values[j][x];
                int ref=c;
                y=x+1;
                while(y<size){
                    if(temp==values[j][y]){c++;break;}
                    y++;
                }
                if(ref<c)break;
                x++;
            }
            j++;
        }
        for(j=0;j<size;j++){
            k+=values[j][j];
        }
        result.push_back(k);result.push_back(r);result.push_back(c);
        return result;
    }
    
}