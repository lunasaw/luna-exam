package pta;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/10/21 10:40
 */
public class GroupMinNumber_2021 {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        String[] strings = s.split("\\ ");
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list=new ArrayList<>();

        int[] min = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            list1.add(Integer.parseInt(strings[i]));
        }
        for (int i=0;i<list1.size();i++){
            for (Integer integer = 0; integer < list1.get(i); integer++) {
                list.add(i);
            }
        }

        min[0] = min(list, false);
        list.remove(list.indexOf(min[0]));
        int k=1;
        for ( int i = 1; i < min.length; i++) {

            if (list.size()>0){
                int a = min(list, true);
                min[i] = list.get(list.indexOf(a));
                list.remove(list.indexOf(a));
                k++;
            }

        }
        //2 2 0 0 0 3 0 0 1 0
        for (int i=0;i<k;i++){
            System.out.print(min[i]);
        }
    }


    public static int min(List<Integer> list, boolean flag) {
        int min;
        if (flag == true) {
            min = list.get(0);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) <= min) {
                    min = list.get(i);
                }
            }
        } else {
            min = 1;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == 0) {
                    continue;
                } else if (list.get(i) <= min) {
                    min = list.get(i);
                }
            }
        }
        return min;//返回最小值
    }

}


class Main {

    public static void main(String[] args) {
        int [] arr = new int [11];
        Scanner sc=new Scanner(System.in);
        for(int i=0;i<10;i++)
        {
            int num=sc.nextInt();
            arr[i]=num;
        }
        if(arr[0]==0)
        {
            for(int i=1;i<10;i++)
            {
                int s=arr[i];
                for(int j=0;j<s;j++)
                {
                    System.out.print(i);
                }
            }
        }
        else
        {
            for(int i=1;i<10;i++)
            {
                if(arr[i]!=0)
                {
                    System.out.print(i);
                    arr[i]=arr[i]-1;
                    break;
                }
            }
            for(int i=0;i<arr[0];i++)
            {
                System.out.print("0");
            }
            for(int i=1;i<10;i++)
            {
                int sd=arr[i];
                for(int j=0;j<sd;j++)
                {
                    System.out.print(i);
                }
            }
        }
        sc.close();
    }

}
