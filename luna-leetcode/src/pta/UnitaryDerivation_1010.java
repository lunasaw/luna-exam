package pta;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/9/14 10:51
 */
public class UnitaryDerivation_1010 {
    public static void main(String[] args) {
        UnitaryDerivation_1010 unitaryDerivation=new UnitaryDerivation_1010();
        //unitaryDerivation.Test();
        System.out.println("分隔符----------");
        unitaryDerivation.test01();
    }
    public void test01() {
        Scanner in = new Scanner(System.in);
        String nextInt = String.valueOf(in.nextLine());
        String[] words = nextInt.split("\\s+");
        int[] last = new int[words.length];
        //System.out.println(words.length);
        int k = 0;
        for (int i = 0; i < words.length;i += 2 ) {

            if (Integer.parseInt(words[i])==0){
                last[k]=0;
                last[k+1]=0;
                k+=2;
            }
            else if (Integer.parseInt(words[i])!=0&&Integer.parseInt(words[i + 1])!=0) {

                last[k] = Integer.parseInt(words[i]) * Integer.parseInt(words[i + 1]);
                last[k + 1] = Integer.parseInt(words[i + 1]) - 1;
                k += 2;

            } else if(Integer.valueOf(words[i]) != 0 && Integer.valueOf(words[i+1]) == 0) {

            }

        }
        for (int i = 0; i < k-1; i++) {
            System.out.print(last[i] + " ");
        }
        System.out.print(last[k-1]);
    }

    public void test() {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String[] newStr = str.split("\\s+");                //切割字符串
        ArrayList<Integer> alist = new ArrayList<Integer>();
        for (int i = 0; i < newStr.length; i += 2) {
            int j = i + 1;
            if (Integer.valueOf(newStr[i]) == 0) {                //常数项为0
                alist.add(0);
                alist.add(0);
            }
            if (Integer.valueOf(newStr[i]) != 0 && Integer.valueOf(newStr[j]) == 0) {    //指数为0且常数项不为0

            }
            if (Integer.valueOf(newStr[i]) != 0 && Integer.valueOf(newStr[j]) != 0) {    //	常数项和指数项都不为0
                alist.add(Integer.valueOf(newStr[i]) * Integer.valueOf(newStr[j]));
                alist.add(Integer.valueOf(newStr[j]) - 1);
            }
        }
        if (alist.isEmpty()) {            //如果将要输出的是空字符串，那么就输出0 0
            System.out.println("0 0");
        } else {
            for (int i = 0; i < alist.size(); i++) {
                System.out.print(alist.get(i));
                if (i != alist.size() - 1) {
                    System.out.print(" ");    //行末不能有空格  控制空格的输出
                }
            }
            System.out.println();
        }

    }
}



