package com.luna.practice.ls;

import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/9/26 7:59
 */
public class Longadd {
    public static void main(String[] args) {
        //add();
        //sub();
        multiply();
    }

    public static void sub(){
        String s = new String();
        Scanner in = new Scanner(System.in);
        s = in.nextLine();
        String[] split = s.split("\\-");
        char[] array = split[0].toCharArray();
        char[] Array = split[1].toCharArray();

        System.out.println(array);
        System.out.println(Array);

        int arraylen = Integer.parseInt(String.valueOf(array.length));
        int arrarylen1 = Integer.parseInt(String.valueOf(Array.length));
        int i = arraylen - arrarylen1 ;
        int q = array.length > Array.length ? arraylen - arrarylen1 : arrarylen1 - arraylen;
        int j = array.length > Array.length ? arraylen : arrarylen1;
        int max1=0;
        int[] a = new int[j];
        int[] b = new int[j];
        if (i<0) {


            for (int k=0,m=0 ; m< Array.length; k++,m++) {
                b[k] = Integer.parseInt(String.valueOf(Array[m]));
            }

            for (int k = q , m = 0; m < array.length;m++, k++) {
                a[k] = Integer.parseInt(String.valueOf((array[m])));
            }
        } else {

            for (int k = q, m = 0; m < arrarylen1; k++, m++) {
                b[k] = Integer.parseInt(String.valueOf(Array[m]));
            }

            for (int k = 0 , m = 0; m < array.length;m++, k++) {
                a[k] = Integer.parseInt(String.valueOf((array[m])));
            }
        }
        int [] l=new int[j];
        if (i<0) {
            for (int u=j-1;u>=0;u--){
                l[u]=b[u]-a[u]+l[u];
                if (l[u]>=0){

                }else{
                    l[u-1]--;
                    l[u]=l[u]+10;
                }
            }
        }else {

            for (int u=j-1;u>=0;u--){
                l[u]=a[u]-b[u]+l[u];
                if (l[u]>=0){

                }else{
                    l[u-1]--;
                    l[u]=l[u]+10;
                }
            }
        }
        //System.out.println(max1);
        //678678678-1231231231212121

        if (i<0) {
            System.out.print("-");
            for (int i1 = 0; i1 < l.length; i1++) {
                System.out.print(l[i1]);
            }
        }else {
            for (int i1=0;i1<l.length;i1++){

                    System.out.print(l[i1]);

            }
        }


    }

    public static void add(){
        String s = new String();
        Scanner in = new Scanner(System.in);
        s = in.nextLine();
        String[] split = s.split("\\+");
        char[] array = split[0].toCharArray();
        char[] Array = split[1].toCharArray();

        System.out.println(array);
        System.out.println(Array);

        int arraylen = array.length;
        int arrarylen1 =Array.length;
        int i = array.length > Array.length ? arraylen - arrarylen1 : arrarylen1 - arraylen;
        int j = array.length > Array.length ? arraylen : arrarylen1;
        j++;
        int[] a = new int[j];
        int[] b = new int[j];
        if (arraylen - arrarylen1 > 0) {
            for (int k = 0; k <=i; k++) {
                a[k] = 0;
            }

            for (int k = i+1, m = 0; m < arrarylen1; k++, m++) {
                a[k] = Integer.parseInt(String.valueOf(Array[m]));
            }
            b[0]=0;
            for (int k = 1 , m = 0; m < array.length;m++, k++) {
                b[k] = Integer.parseInt(String.valueOf((array[m])));
            }
        } else {
            for (int k = 0; k < i; k++) {
                a[k] = 0;
            }
            for (int k = i+1, m = 0; m < arraylen; k++, m++) {
                a[k] = Integer.parseInt(String.valueOf(array[m]));
            }
            b[0]=0;
            for (int k = 1 , m = 0; m < Array.length;m++, k++) {
                b[k] = Integer.parseInt(String.valueOf((Array[m])));
            }
        }
        //678678678+1231231231212121
        int[] e = new int[j];
        for (int u = j-1 ; u >0; u--) {

            e[u] = a[u] + b[u] +e[u];
            if (e[u] >= 10) {
                e[u - 1] += 1;
                e[u] = e[u] - 10;
            }

        }
        if (e[0]==1) {
            for (int c : e
            ) {
                System.out.print(c);
            }
        }else {
            for (int i1 =1;i1<e.length;i1++){
                System.out.print(e[i1]);
            }
        }
    }

    private static void multiply() {
        String s = new String();
        Scanner in = new Scanner(System.in);
        s = in.nextLine();
        String[] split = s.split("\\*");

        //用来存储结果
        StringBuilder ans = new StringBuilder();
        //反转两个大整数,以便从个位开始相乘
        StringBuilder a1 = new StringBuilder(split[0]);
        StringBuilder b1 = new StringBuilder(split[1]);
        a1.reverse();
        b1.reverse();
        //用来存储中间计算的结果
        int[] arr = new int[a1.length() + b1.length()];
        //开始相乘,从两个大整数的个位开始,也就是反转后的第一位
        for(int i = 0; i < a1.length(); i++){
            //b的每一位与a的第i为乘积,每循环乘b每一位一次,a移动一位
            for(int j =0; j < b1.length();j++){
                //错位相加,进而保存了结果中每一位的累加和,这里数字的字符与‘0’字符相减等于数字本身
                arr[i+j] += (a1.charAt(i) - '0') * (b1.charAt(j) - '0');

            }
        }
        //给每一位大于十的进位,保留余数
        for(int i = 0; i < arr.length - 1; i++){
            arr[i+1] += arr[i] / 10;
            arr[i] %= 10;
        }
        //把结果放到答案集里面去
        for(int i = 0; i < arr.length - 1; i++){
            ans.append(arr[i]);
        }

        System.out.println(ans.reverse().toString());
    }

}
