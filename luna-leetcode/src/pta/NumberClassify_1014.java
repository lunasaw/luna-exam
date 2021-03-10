package pta;

import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/9/16 9:56
 */
public class NumberClassify_1014 {
    public static void main(String[] args) {
        NumberClassify_1014 numberClassify=new NumberClassify_1014();
        //numberClassify.function2();
        numberClassify.function1();

    }
    public void function1(){
        Scanner in =new Scanner(System.in);
        String string = in.nextLine();
        String[] split = string.split("\\s+");
        int A1 = 0;
        int A2 = 0;
        int [] a2=new int[split.length];
        int k=0;
        int t=0;
        int A3 = 0;
        float A4 = 0f;
        int count=0;
        int A5 = 0;
        for (int i=1;i<=Integer.parseInt(split[0]);i++){
            int j=Integer.parseInt(split[i]);
            if (j%5==0&&j%2==0){
                A1+=j;
            }else if (j%5==1){
                a2[k]=j;
                k++;
            }else if (j%5==2){
                A3++;
            }else if (j%5==3){
                A4+=j;
                count++;
            }else if (j%5==4){
                if (j>A5){
                    A5=j;
                }
            }
        }
        for (int i=0;i<k;i++){
            t= (int)( (Math.pow(-1,i))*a2[i]);
            A2+=t;
        }
        if (A1 != 0) {
            System.out.print(A1+" ");
        }else {
            System.out.print("N ");
        }
        if (A2 != 0) {
            System.out.print(A2+" ");
        }else {
            System.out.print("N ");
        }if (A3 != 0) {
                System.out.print(A3+" ");
        }else {
            System.out.print("N ");
        }if (A4 != 0) {
                System.out.print(String.format("%.1f",A4*1.0/count)+" ");
        }else {
            System.out.print("N ");
        }if (A5 != 0) {
                System.out.print(A5);
        }else {
            System.out.print("N");
        }

    }


    public void function2(){
                int n;
                int[] arr = new int[6];
                boolean[] ar = new boolean[6]; // 判断是否存在某一类数字
                int flag2 = -1, flag4 = 0;
                int temp, t;
                double ansarr4;
                Scanner sc = new Scanner(System.in);
                n = sc.nextInt();
                // 读取一个判断一个 计算相对应的A
                for (int i = 0; i < n; i++) {
                    t = sc.nextInt();
                    temp = t % 5;
                    if (temp == 0 && t % 2 == 0) {
                        ar[1] = true;
                        arr[1] += t;
                    } else if (temp == 1) {
                        ar[2] = true;
                        flag2 *= -1;
                        arr[2] += t * flag2;
                    } else if (temp == 2) {
                        ar[3] = true;
                        arr[3]++;
                    } else if (temp == 3) {
                        ar[4] = true;
                        flag4++;
                        arr[4] += t;
                    } else if (temp == 4) {
                        ar[5] = true;
                        arr[5] = t > arr[5] ? t : arr[5];
                    }
                }
                ansarr4 = arr[4] * 1.0 / flag4;

                for (int i = 1; i < 4; i++) {
                    if (ar[i]) {
                        System.out.print(arr[i] + " ");
                    } else {
                        System.out.print("N ");
                    }
                }
                if (ar[4]) {
                    System.out.printf("%.1f" + " ", ansarr4);
                } else {
                    System.out.print("N ");
                }
                if (ar[5]) {
                    System.out.print(arr[5]);
                } else {
                    System.out.print("N");
                }

    }


}
