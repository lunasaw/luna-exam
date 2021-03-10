package pta;

import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/9/13 14:34
 */
public class Ironic_1009 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        Ironic_1009 ironic = new Ironic_1009();
        ironic.test(str);
//        System.out.println("中国");
        ironic.test01(str);

    }
    public void test(String s){
        /*
         * 思路:
         * 1.读取一行字符串
         * 2.以一个或者多个空格拆分为字符串数组
         * 3.逆序输出数组元素
         */
//        Scanner sc = new Scanner(System.in);
//        String s = sc.nextLine();
        String[]words = s.split("\\s+");
        for(int i=words.length-1 ;i>0 ;i--){
            System.out.print(words[i]+" ");
        }
        System.out.println(words[0]);

    }


    public void test01(String s){
//        Scanner in =new Scanner(System.in);
//        String string = in.nextLine();
        String reverse = new StringBuffer(s).reverse().toString();
        reverse.trim();
        System.out.println(reverse);
    }
}
