package com.luna.practice.ls.daily;

import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/11/26 12:09
 */
public class Test_11_26 {
    public static void main(String[] args) {
//        System.out.println("��1-----------------");
//        factorial(2);
//        factorial(4);
//        factorial(6);
//        factorial(10);//��1
//        System.out.println("��2-----------------");
//        findMaxAndMin();
//        System.out.println("��3-----------------");
//        sysPrime();//��3
//        System.out.println("��4-----------------");
//        findRandom();
        System.out.println("��5-----------------");
        divisorAndmultiple();
//        System.out.println("\n��11-----------------");
//        sysmouth();//��11
    }

    private static void findRandom() {
        int i=0,tag=0;
        int max=0,min=50;
        int next;
        while (i<100){
//            Random random=new Random();
//            random.nextInt(100);
            double random = Math.random();
            next=(int)(random*100);
//            next=random.nextInt(100);
//            System.out.println(next);
            if (next>max){
                max=next;
            }
            if (next<=min){
                min=next;
            }
            if (next>50){
                tag++;
            }
            i++;
        }
        System.out.println("�������������"+max+"��С����"+min);
        System.out.println("100��������д���50�ĸ���Ϊ"+tag+"��!");
    }

    private static void findMaxAndMin() {
        System.out.println("������������x,y,z���Կո�����");
        Scanner scanner = new Scanner(System.in);
        int a[] = new int[3];
        int i = 0, max = 0, min = 0;
        while (i < 3) {
            a[i] = scanner.nextInt();
            i++;
        }
        if (a[0] >= a[1]) {
            if (a[0] >= a[2]) {
                max = a[0];
                if (a[1] >= a[2]) {
                    min = a[2];
                } else {
                    min = a[1];
                }
            } else {
                max = a[2];
                min = a[1];
            }
        } else {
            if (a[1] >= a[2]) {
                max = a[1];
                if (a[0] >= a[2]) {
                    min = a[2];
                } else {
                    min = a[0];
                }
            } else {
                max = a[2];
                min = a[0];
            }
        }
        System.out.println("���ֵ=" + max + "��Сֵ=" + min);

    }

    private static void divisorAndmultiple() {
        int m = 0, n = 0, m1 = 0, n1 = 0;
        int a;
        Scanner scanner = new Scanner(System.in);
        System.out.println("������m��ֵ:");
        m = scanner.nextInt();
        System.out.println("������n��ֵ:");
        n = scanner.nextInt();
        //�������m��nֵ���ݣ�
        m1 = m;
        n1 = n;
        //ȡ�������������������
        a = m % n;
        while (a != 0) {
            m1 = n1;
            n1 = a;
            a = m1 % n1;
        }
        System.out.println("m,n�����Լ��Ϊ��" + n1);
        //���������ֵ���С�������ķ���Ϊ�����������ĳ˻���/���������ֵ����Լ������
        System.out.println("m,n����������С������Ϊ��" + m * n / n1);
    }


    private static void sysmouth() {
        String[] month = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int i = 0;
        for (String s : month) {
            i++;
            if (i == 6)
                System.out.println(s + " ");//��ӡ����������ʱ��Ҫʹ�û��д�ӡ
            else System.out.print(s + " ");
        }
    }


    private static void factorial(int max) {
        int x = 1;
        for (int i = 1; i <= max; i++) {
            x *= i;
        }
        System.out.println(max + "!=" + x);

    }
    /*
   �ж�����
    */
    public static boolean isPrime(int num) {
        boolean isprime = true;
        for (int i = 2; i < num; i++) {
            if (num % i == 0) {
                isprime = false;
                break;
            }
        }
        return isprime;
    }
    /*
    �������
     */
    public static void sysPrime() {
        boolean prime;
        int q = 0;
        for (int i = 1; i <= 100; i++) {
            prime = isPrime(i);
            if (prime) {
                System.out.print(i + " ");
                q++;
                if (q % 5 == 0) {
                    System.out.println();
                }
            }

        }
    }
}
