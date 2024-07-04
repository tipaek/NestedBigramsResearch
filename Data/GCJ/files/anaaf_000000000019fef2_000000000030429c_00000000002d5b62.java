package com.company;
import java.time.LocalTime;
import java.util.*;
import java.io.*;
import java.lang.*;

public class Main {
   public static void peaks() {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.

        for (int j = 1; j <= t; j++) {

//        }

            int N = in.nextInt();
            int[] chk = new int[N];

            for (int i = 0; i < chk.length; i++) {
                chk[i] = in.nextInt();
            }

            int peaks = 0;

            for (int i = 0; i < chk.length - 2; i++) {
                if (chk[i] > chk[i - 1] && chk[i] > chk[i + 1]) {
                    peaks++;
                }
            }
            System.out.println("Case #"+ j +": " +  peaks);
        }
    }

    public static void main(String[] args)  {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.

//        for (int j = 1; j <= t; j++) {

        int x = 0;
        int y = 0;
        int targetX = in.nextInt();
        int targetY = in.nextInt();
        boolean isPossible = true;
        String finalString = "";
        int moves = 0;
        boolean fmove = true

        while(isPossible) {
//            if (n == 0)
//                return false;
//
//            while (n != 1)
//            {
//                if (n % 2 != 0)
//                    return false;
//                n = n / 2;
//            }

            if(targetY % 2 !=0) {
                if(!fmove) {
                    finalString = "IMPOSSIBLE";
                } else {
                    if(targetY == 1) {
                        finalString = finalString + "N";
                        fmove = false;
                        moves++;
                    } else  if(targetY == -1)  {
                        finalString = finalString + "S";
                        fmove = false;
                        moves++;
                    } else if((targetY - 1) % 2 == 0 ) {
                        y --;
                        moves++;
                        finalString = finalString + "S";
                        fmove = false;
                    } else if((targetY + 1) % 2 == 0 ) {
                        y++;
                        moves++;
                        finalString = finalString + "N";
                        fmove = false;
                    }
                }
            } else if(targetX % 2 !=0) {
                if(!fmove) {
                    finalString = "IMPOSSIBLE";
                } else {
                    if(targetX == 1) {
                        finalString = finalString + "E";
                        x--;
                        moves++;
                        fmove = false;
                    } else  if(targetX == -1)  {
                        finalString = finalString + "W";
                        x--;
                        moves++;
                        fmove = false;
                    } else if((targetX - 1) % 2 == 0 ) {
                        x --;
                        moves++;
                        finalString = finalString + "W";
                        fmove = false;
                    } else if((targetX + 1) % 2 == 0 ) {
                        y++;
                        moves++;
                        finalString = finalString + "E";
                        fmove = false;
                    }
                }
            } else {
                if() {

                }
            }

            }


        }