package pta;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/10/16 21:50
 * 给定任一个各位数字不完全相同的 4 位正整数，如果我们先把 4 个数字按非递增排序，再按非递减排序，然后用第 1 个数字减第 2 个数字，将得到一个新的数字。
 * 一直重复这样做，我们很快会停在有“数字黑洞”之称的 6174，这个神奇的数字也叫 Kaprekar 常数。
 */
public class KaprekarConstant_1019 {
    public static void main(String[] args) {
            Scanner in=new Scanner(System.in);
            String string = in.nextLine();
            int tag=0;
            while (Integer.parseInt(string) != 6174) {
                char[] array = string.toCharArray();
                StringBuffer stringBuffer = new StringBuffer();
                Integer[] number = new Integer[array.length];
                Integer[] renumber = new Integer[number.length];
                for (int i = 0; i < array.length; i++) {
                    number[i] = Integer.parseInt(String.valueOf(array[i]));
                }

                Arrays.sort(number);
//        for (int c:number){
//            System.out.print(c);
//        }
                for (int i = 0; i < number.length; i++) {
                    stringBuffer.append(number[i]);
                }
                char[] chars = stringBuffer.reverse().toString().toCharArray();
                for (int i = 0; i < number.length; i++) {
                    renumber[i] = Integer.parseInt(String.valueOf(chars[i]));
                }

                //System.out.println(stringBuffer.toString());
                //7766 - 6677 = 1089

                for (int i = 0; i < number.length; i++) {
                    if (number[i].equals(renumber[i])==true){
                        tag=1;
                        break;

                    }
                }
                if (tag==1){
                    break;
                }

                int a = number[0] + number[1] * 10 + number[2] * 100 + number[3] * 1000;
                int b = renumber[0] + renumber[1] * 10 + renumber[2] * 100 + renumber[3] * 1000;
                int result = a - b;
                ArrayList<Integer> list =new ArrayList<>();
//                for (int i = 0; i < number.length; i++) {
//                    list.add(number[i]);
//                }
//                System.out.println(list.toString());
                //System.out.println(""+number[3]+number[2]+number[1]+number[0]);
                System.out.println(""+number[3]+number[2]+number[1]+number[0] + " - " + ""+number[0]+number[1]+number[2]+number[3] + " = " + result);
                string = Integer.toString(result);
            }
            if (tag==1){
                System.out.println(Integer.parseInt(string) + " - " + Integer.parseInt(string) + " = " + "0000");

            }
    }

    public class Main {

        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        public  void main(String[] args) {
            Scanner in = new Scanner(System.in);
            int num = in.nextInt();
            yc(num);
        }

        private  void yc(int num) {
            int[] g = new int[4];
            g[0] = num % 10;
            g[1] = (num % 100) / 10;
            g[2] = (num % 1000) / 100;
            g[3] = num / 1000;
            if(g[0] == g[1] && g[1] == g[2] && g[2] == g[3]) {
                out.printf("%d - %d = 0000" , num , num);
                out.flush();
            } else {
                Arrays.sort(g);
                int[] reg = new int[4];
                int s1 = 0 , s2 = 0;
                for (int i = 0; i < 4; i++) {
                    reg[i] = g[3 - i];
                    s1 = s1 * 10 + g[3-i];
                    s2 = s2 * 10 + g[i];
                }
                out.printf("%04d - %04d = %04d\n", s1,s2,s1-s2);
                out.flush();
                if(s1 - s2 == 6174 || s1 - s2 == 0) {
                    return;
                }
                yc(s1-s2);
            }
        }
    }
}
