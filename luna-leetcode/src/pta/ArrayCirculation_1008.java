package pta;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/9/11 23:03
 */
public class ArrayCirculation_1008 {
    public static void main(String[] args) {
        //new ArrayCirculation_1008().main();
        new ArrayCirculation_1008().test();
    }

    public void test(){
        Scanner in = new Scanner(System.in);

        int size = in.nextInt();
        int cnt = in.nextInt();
        List<Integer> list =new ArrayList<>();
        for(int i = 0; i < size; i++){
            list.add(in.nextInt());
        }
        for (int i=0;i<cnt;i++){
            Integer last = list.get(list.size()-1-i);
            list.add(0,last);
        }
        for(int i = 0; i < list.size()-cnt; i++){
            System.out.print(list.get(i));
            if(i !=list.size()-cnt-1){
                System.out.print(" ");
            }
        }
    }

        public void  main() {

            Scanner in = new Scanner(System.in);

            int size = in.nextInt();
            int cnt = in.nextInt();
            int[] array = new int [size];

            for(int i = 0; i < array.length; i++){
                array[i] = in.nextInt();
            }

            in.close();

            for(int i = 0; i < cnt; i++){//从最后一个开始  循环cnt次   需要将后cnt个数字移到前面
                int temp = array[array.length-1];//每次保留最后一个
                for(int j = array.length-1; j > 0; j--){//每次将前者赋值给后一个  将数组往后移 移动
                    //因为每次都要移动整个数组  然后将最后一位放到第一位
                    array[j] = array[j-1];
                }
                array[0] = temp;
            }


            for(int i = 0; i < array.length; i++){
                System.out.print(array[i]);
                if(i != array.length -1){
                    System.out.print(" ");
                }
            }
        }


}
