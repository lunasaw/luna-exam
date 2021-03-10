package pta;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/9/15 10:58
 */
public class AandBgreaterthanC_1011 {
    public static void main(String[] args) {
        Scanner in =new Scanner(System.in);
        int anInt = in.nextInt();
        List<String> list=new ArrayList<>();
        for (int i=0;i<anInt+1;i++){
            list.add(in.nextLine());
        }
        boolean [] judge=new boolean[anInt];
        for (int i = 1;i<list.size();i++){
            String s = list.get(i);
            String[] strings = s.split("\\s+");
            if (Integer.parseInt(strings[0])+Integer.parseInt(strings[1])>Integer.parseInt(strings[2])){
                judge [i-1] =true;
            }else {
                judge [i-1]=false;
            }
        }
        for (int i=0;i<judge.length;i++){
            System.out.println("Case #"+(i+1)+": "+judge[i]);
        }

    }
        public void test(){
                Scanner in = new Scanner(System.in);
                int T = in.nextInt();

                for(int test = 1; test <= T; ++test) {
                    long a = in.nextLong(), b = in.nextLong(), c = in.nextLong();
                    System.out.print("Case #" + test + ": ");
                    if(a + b > c) {
                        System.out.println("true");
                    }
                    else {
                        System.out.println("false");
                    }
                }


        }

}
