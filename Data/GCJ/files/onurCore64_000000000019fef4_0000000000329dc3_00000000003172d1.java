class Solution{
  public static void main(String[] arg){
    Scanner in = new Scanner(System.in);
    int test = in.nextInt();
    int n;
    int d;
    int res;
    boolean c = false;
    boolean a = false;
    boolean b = false;
    int[] slices = new int[][];
    int slice;
    for(int i = 0;i < test;i++){
      n = in.nextInt();
      d = in.nextInt();
      slices = new int[n][2];
      if(d == 2){
        slices[0][0] = in.nextInt();
        slices[0][1] = 1;
        for(int l = 1;l < n;l++){
          res = 0;
          slice = in.nextInt();
          while(slices[res][0] != 0){
            if(slices[res][0] == slice){
              a = true;
              System.out.printl("Case #" + (i+1) +": "+ "0");
              break;
            }
            res++;
          }
          if(a != true){
            slices[res][0] = slice;
          }
        }
        if(!a){
          System.out.printl("Case #" + (i+1) +": "+ "1");
        }
      }else if(d == 3){
        slices[0][0] = in.nextInt();
        slices[0][1] = 1;
        for(int l = 1;l < n;l++){
          res = 0;
          slice = in.nextInt();
          while(slices[res][0] != 0){
            if(slices[res][0] == slice && slices[res][1] == 2){
              a = true;
              b = true;
              System.out.printl("Case #" + (i+1) +": "+ "0");
              break;
            }else if(slices[res][0] == slice && slices[res][1] == 1){
              a = true;
              for(int p = 0;p < res;p++){
                if(slice < slices[p][0]){
                  c = true;
                }
              }
              slices[res][1] = 2;
            }
            res++;
          }
          if(a != true){
            slices[res][0] = slice;
            slices[res][1] = 1;
          }
        }if(!a){
          System.out.println("Case #" + (i+1) +": "+ "2");
        }
        if(a && !b){
          if(c){
          System.out.println("Case #" + (i+1) +": "+ "1");
          }else{
          System.out.println("Case #" + (i+1) +": "+ "2");
          }
        }
      
      }
    
    
    }
  
  
  
  
  }




}