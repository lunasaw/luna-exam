package com.luna.practice.ls.daily;

import java.util.Stack;

/**
 * @author czy@win10
 * @date 2019/11/7 8:02
 */
public class Test_11_7 {
    public static void main(String[] args) {
        /*
        쳲��������еĵ�k����
         */
//        {
//            Scanner in = new Scanner(System.in);
//            int i = in.nextInt();
//            i = fibonacci(i);
//            System.out.println(i);
//        }
        /*
        ���1~100������
         */
//        sysPrime();
        /*
        ��һ���������ֽ�����
         */
//        {
//            Scanner in = new Scanner(System.in);
//            int i = in.nextInt();
//            System.out.print(i + "=");
//            sysResolve(i);
//        }
        /*
        ��ӡ����
         */
//        Scanner in = new Scanner(System.in);
//        int i = in.nextInt();
//        sysLing(i);
        /*
        ���ӳ���
         */
//        Scanner in = new Scanner(System.in);
//        int i = in.nextInt();
//        int monkeyEat = monkeyEat(i);
//        System.out.println(monkeyEat);
        /*
        ��������
         */
//        int[] a = {1, 4, 5, 6, 3, 8, 2, 9, 7, 0};
//        int[] sort = sort(a);
//        for (int x : sort) {
//            System.out.print(x + " ");
//        }
        /*
        ��������
         */
//        int[] a = {1, 4, 5, 6, 3, 8, 2, 9, 7, 0};
//        int[] b = new int[a.length];
//        replace(a, b);
//        for (int x : b) {
//            System.out.print(x + " ");
//        }
        /*
        �Ұ��� ÿ�����ֵ,ÿ����Сֵ
        {1,12,3}
        {4,89,21}
        {4,22,9}
         */
//        int [][]arr={{1,12,3},
//                {4,89,21},
//                {4,22,9}};
//        int i = saddlePoint(arr);
//        System.out.println(i);
        /*
        ����ƥ������
         */

        String expression = "{((1+3)+(2+4))+9*7}" ;
        boolean flag = matching(expression) ;
        if(flag)
        {
            System.out.println("ƥ��ɹ�!") ;
        }
        else
        {
            System.out.println("ƥ��ʧ��!");
        }


    }

    /*
    쳲�����
     */
    public static int fibonacci(int a) {
        if (a < 0) {
            System.out.println("n����С��0");
            return 0;
        } else if (a == 0) {
            return 0;
        } else if (a == 1) {
            return 1;
        } else {
            return fibonacci(a - 1) + fibonacci(a - 2);
        }
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
        for (int i = 1; i <= 100; i++) {
            prime = isPrime(i);
            if (prime) {
                System.out.print(i + " ");
            }
        }
    }

    /*
    �ҳ�����
     */
    public static int resolve(int a) {
        boolean prime = isPrime(a);
        if (prime) {
            return a;
        } else {
            for (int i = 2; i < a; i++) {
                if (a % i == 0) {
                    return i;
                }
            }
        }
        return a;
    }

    /*
    �������
     */
    public static void sysResolve(int i) {
        int resolve = 0;
        for (int k = 0; k < 10; k++) {
            resolve = resolve(i);
            if (i != 1) {
                System.out.print(resolve + "*");
            }
            i = i / resolve;
        }
        System.out.print("\b");
    }

    /*
    ��ӡ����
     */
    public static void sysLing(int a) {
        for (int i = 1; i <= a; i++) {
            for (int j = a; j > i; j--) {
                System.out.print(" ");
            }
            for (int k = 0; k < 2 * i - 1; k++) {
                System.out.print("*");
            }
            System.out.println();
        }
        for (int i = a - 1; i > 0; i--) {
            for (int j = a; j > i; j--) {
                System.out.print(" ");
            }
            for (int k = 0; k < 2 * i - 1; k++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

    /*
    ���ӳ���
     */
    public static int monkeyEat(int num) {
        int sum = 0;
        for (int i = 0; i < num; i++) {
            sum = (sum + 1) * 2;
        }
        return sum;
    }

    /*
    ����
     */
    public static int[] sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            if (min != i) {
                swap(arr, i, min);
            }
        }
        return arr;
    }

    /*
    ������Ԫ��
     */
    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /*
    ת��
     */
    public static void replace(int reverseArray[], int originArray[]) {
        for (int i = 0; i < reverseArray.length; i++) {
            originArray[i] = reverseArray[reverseArray.length - i - 1];
        }
    }

    /*
    �Ұ��� ÿ�����ֵ,ÿ����Сֵ
        {1,12,3}
        {4,5,21}
        {4,22,9}
     */

    public static int saddlePoint(int[][] arr) {
        int rowmax=arr[0][0];
        int col=0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if(arr[i][j]>rowmax){	//�õ��������Ԫ��
                    rowmax=arr[i][j];
                    col=j;
                }
            }
            if(isColmin(arr,rowmax,col)){
                //�ж��Ƿ���������С
                return rowmax;
            }
        }
        return 0;

    }

    //�ж��Ƿ���������С
    public static boolean isColmin(int[][] arr,int rowmax,int col){
        int colmin=rowmax;
        boolean flag=true;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i][col]<colmin){
                flag=false;
                break;
            }
        }
        return flag;
    }

    //����ƥ��
    public static boolean matching(String expression)
    {
        if(expression==null||expression=="")
        {
            System.out.println( "������ʽΪ�ջ�û��������ʽ" ) ;
        }

        Stack<Character> stack = new Stack<Character>() ;

        for(int index=0 ; index<expression.length();index++)
        {
            switch(expression.charAt(index))
            {
                case '(':
                    stack.push(expression.charAt(index)) ;
                    break ;
                case '{':
                    stack.push(expression.charAt(index)) ;
                    break ;
                case ')':
                    if(!stack.empty()&&stack.peek()=='(')
                    {
                        stack.pop() ;
                    }
                    break ;

                case '}':
                    if(!stack.empty()&&stack.peek()=='{')
                    {
                        stack.pop();
                    }
            }
        }

        if(stack.empty())
            return true ;
        return false ;
    }


}
