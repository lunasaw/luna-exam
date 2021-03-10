package pta;

import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/10/14 12:33
 * 长整数除法
 */

public class DivisionMethod_1017 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String[] newStr = str.split("\\ ");                //切割字符串
        char[] divided = newStr[0].toCharArray();//被除数
        int[] dividedInt = new int[divided.length];
        for (int i = 0; i < divided.length; i++) {
            dividedInt[i] = Integer.parseInt(String.valueOf(divided[i]));
        }
        char[] divisor = newStr[1].toCharArray();//除数
        int c =Integer.parseInt(String.valueOf( divisor[0]));
        int x = 0;//高位和
        int q= 0;//余数
        int n=0;//过程取余
        int[] consult = new int[divided.length];//商
        for (int i = 0; i < divided.length-1; i++) {
            if (dividedInt[i] > c) {
                consult[i] = dividedInt[i] / c;
            } else {
                x = dividedInt[i] * 10 + dividedInt[i + 1];
                consult[i] = x / c;
                n=x%c;
                q = x - consult[i]*c;
                dividedInt[i + 1] = q;
            }
        }
        for (int i=0;i<consult.length-1;i++){
            System.out.print(consult[i]);
        }
        System.out.print(" "+n);
    }
}
