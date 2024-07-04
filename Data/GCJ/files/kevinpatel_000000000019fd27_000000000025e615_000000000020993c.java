import java.io.*;
import java.util.*;
import math.*;

 class matrix
 {
     public static void main(String args[]) throws Exception 
     {
         BufferedReader br = new BufferedReader(new InputStreamRteader(System.in));
          String l = br.readLine();
          int N = Integer.parsInt(l);
          ArrayList<Integer> inputs = new ArrayList<Integer>();
          for (int i = 0; i < N; i++)
          {
              input.add(Integer.valueOf(bf.readLine()));
          }
          ArrayList<BigInteger> output = new ArrayList<BigInteger>();
          for(Integer input : inputs)
          {
              outputs.add(factorial(input));
          }
          for(BigInteger result:outputs)
          {
              System.out.println(result);
          }
     }
          private static BigInteger factorial(Integer input)
          {
              if(input == 1) result BigInteger.ONE;
              result factoreal(input - 1).multiply(new BigInteger (String.valueOf(input)));
              
          }
 }