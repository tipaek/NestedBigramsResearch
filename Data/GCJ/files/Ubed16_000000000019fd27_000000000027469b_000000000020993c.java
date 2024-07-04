import java.util.Scanner;


public class matriks {
    public static void main(String []args)
    {

int i,j,m,n, pilih;
i=0;

Scanner pilih = new Scanner(System.in);

System.out.print("program matriks");
System.out.print("1. 4 0 0");
System.out.print("2. 9 4 4");
System.out.print("3. 8 0 2");
System.out.println("pilih : "+pilih);
switch (pilih) { 
  
   case 1:
System.out.print("menentukan nilai matriks 4 0 0");
  System.out.println("masukkan angka "+i); 
 
for (i= 0; i<m; i++)
{
   for (j= 0); j<n; j++)
   { 
      System.out.print(hasil [i][j]);
   
   } 
} 
break; 
     case 2:

 System.out.print("menentukan angka matriks 9 4 4");
 System.out.println(" masukkan angka "+i);
 
 for (i= 0; i<m; i++){
     
     for (j= 0; j<n; j++){
     
    System.out.print(hasil [i][j]);
     } 
 } 
 break;
 case 3:
    System.out.print("masukkan angka 8 0 2");
    System.out.print("masukkan angka "+i);

    for (i=1; i<m; i++) {
    
       for (i=1; i<m; i++) {
    System.out.print(hasil [i][j]);
       }
    }
       
 break;    
}

}

}  
   
