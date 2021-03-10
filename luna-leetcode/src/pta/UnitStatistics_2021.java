package pta;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/10/19 9:11
 * N=100311，则有 2 个 0，3 个 1，和 1 个 3。
 */
public class UnitStatistics_2021 {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        String s = in.nextLine();
        char[] array = s.toCharArray();
        Integer [] numver=new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            numver[i]=Integer.parseInt(String.valueOf(array[i]));
        }
        List<Integer> list = complain(numver);
        for (int i = 0; i < 10; i++) {
            if (list.get(i)==0){
                continue;
            }
            System.out.println(""+i+":"+list.get(i));
//            System.out.print(numver[i]);
        }
    }
    public static List<Integer> complain(Integer [] number){
        ArrayList<Integer> arrayList=new ArrayList<>();
        Integer intege0=0;
        Integer intege1=0;
        Integer intege2=0;
        Integer intege3=0;
        Integer intege4=0;
        Integer intege5=0;
        Integer intege6=0;
        Integer intege7=0;
        Integer intege8=0;
        Integer intege9=0;

        for (int i = 0; i <number.length; i++) {
            if (number[i]==0){
                intege0+=1;
            }else if (number[i]==1){
                intege1+=1;
            }else if (number[i]==2){
                intege2+=1;
            }else if (number[i]==3){
                intege3+=1;
            }else if (number[i]==4){
                intege4+=1;
            }else if (number[i]==5){
                intege5+=1;
            }else if (number[i]==6){
                intege6+=1;
            }else if (number[i]==7){
                intege7+=1;
            }else if (number[i]==8){
                intege8+=1;
            }else if (number[i]==9){
                intege9+=1;
            }
        }

        arrayList.add(intege0);
        arrayList.add(intege1);
        arrayList.add(intege2);
        arrayList.add(intege3);
        arrayList.add(intege4);
        arrayList.add(intege5);
        arrayList.add(intege6);
        arrayList.add(intege7);
        arrayList.add(intege8);
        arrayList.add(intege9);
        return arrayList;
    }
}
