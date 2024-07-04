class test{
    public static void main(String args[]){
        Scanner in = new Scanner(System.in); 
        System.out.println("Enter no.of test");
        int t = in.nextInt();
        
        for(int i=1;i<=t;i++){
            int a[][] = new int[][];
            System.out.println("Enter i dimentions");
            int d = in.nextInt();
            for(int m=1;m<=d;m++){
                for(int n=1;n<=d;n++){
                    System.out.println("Enter ("+m+""+n") element");
                    
                }
            }
        }
    }
}