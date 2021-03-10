package pta;

import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/9/9 22:55
 */
public class PrintInt_1006 {
    /*


    public static void main(String[] args) {
        Scanner in =new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        List<String > listB=new ArrayList<>();
        List<String > listS=new ArrayList<>();

        if (n<1000){
            int s=n/100;
            for(int i=0;i<s;i++){
                listS.add("S");
            }
            int b=(n-s*100)/10;
            for (int i=0;i<b;i++){
                listB.add("B");
            }
            int j=n-s*100-b*10;
            for (int i=1;i<=j;i++){
                listB.add(""+i);
            }
        }
        for (String  c:listS){
            System.out.print(c);
        }
        for (String  c:listB){
            System.out.print(c);
        }
        //System.out.println(listS.toString()+listB.toString());
    }
      */
    /*
     * 思路:
     * 1.题目给出正整数n（<1000）所以测试用例最多是3位数
     * 2.分别用a1 a2 a3 表示百位  十位  个位
     * 3.输出a1个'B' a2个'S'
     */
        public static void main(String[] args){
            Scanner sc = new Scanner(System.in);
            int n = sc.nextInt();
            int a1 = n/100;
            int a2 = n%100/10;
            int a3 = n%10;
            while(a1>0){
                System.out.print("B");
                a1--;
            }
            while(a2>0){
                System.out.print("S");
                a2--;
            }
            for(int i=1 ;i<=a3 ;i++){
                System.out.print(i);
            }
            System.out.println();
        }
    }

