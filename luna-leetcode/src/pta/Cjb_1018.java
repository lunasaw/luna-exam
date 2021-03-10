package pta;

import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/10/15 12:50
 * 石头剪子布
 */
public class Cjb_1018 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int max = Integer.parseInt(in.nextLine());
        int wincountA = 0;//甲赢
        int tiedcount = 0;//平局
        int wincountB = 0;//乙赢
        int cA = 0;
        int jA = 0;
        int bA = 0;
        int cB = 0;
        int jB = 0;
        int bB = 0;
        for (int i = 0; i < max; i++) {
            String s = in.nextLine();
            String[] strings = s.split("\\ ");
            if (strings[0].equals(strings[1]) != true) {
                if (strings[0] .equals( "C") && strings[1] .equals( "J")) {//锤子-剪刀 甲赢
                    wincountA++;
                    cA++;
                }
                if (strings[0] .equals( "J") && strings[1] .equals("C")) {
                    wincountB++;//剪刀-锤子 乙赢
                    jB++;
                }
                if (strings[0] .equals( "J") && strings[1] .equals("B")) {//剪刀-布 甲赢
                    wincountA++;
                    jA++;
                }
                if (strings[0] .equals("B")&& strings[1] .equals("J")) {
                    wincountB++;//布-剪刀 乙赢
                    jB++;
                }
                if (strings[0] .equals("B") && strings[1] .equals("C") ){//布-锤子 甲赢
                    wincountA++;
                    bA++;
                } if (strings[0] .equals("C" )&& strings[1] .equals("B")) {
                    wincountB++;//锤子-布 乙赢
                    bB++;
                }
            } else {
                tiedcount++;
            }
        }
        System.out.println(wincountA+" "+tiedcount+" "+wincountB);
        System.out.println(wincountB+" "+tiedcount+" "+wincountA);
        int end=0;
        if (cA > jA)  {
            if (cA > bA) {
                end = cA;
                System.out.print("C ");
            } else {
                max = bA;
                System.out.print("B ");
            }
        } else {
            if (jA > bA) {
                max = jA;
                System.out.print("J ");
            } else {
                max = bA;
                System.out.print("B ");
            }
        }
        if (cB > jB)  {
            if (cB > bB) {
                end = cB;
                System.out.print("C");
            } else {
                max = bB;
                System.out.print("B");
            }
        } else {
            if (jB > bB) {
                max = jB;
                System.out.print("J");
            } else {
                max = bB;
                System.out.print("B");
            }
        }
    }

}
