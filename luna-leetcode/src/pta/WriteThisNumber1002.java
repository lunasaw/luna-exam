package pta;

import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/8/21 14:11
 */
public class WriteThisNumber1002 {
    public static void main(String[] args) {
        char number[] = new char[101];
        String temp1;
        String temp2;
        Scanner input = new Scanner(System.in);
        temp1 = input.next();
        for (int i = 0; i < temp1.length(); i++) {
            number[i] = temp1.charAt(i);
        }//charAt  ��ȡ��Ӧ���ַ� ��ֵ��number����
        int sum = 0;

        for (int i = 0; i < temp1.length(); i++) {
            sum = sum + (Integer.valueOf(number[i]).intValue() - 48);
        }
        //����Ӧ���ַ����תΪint ��͸�sum

        char n_sum[] = new char[11];
        temp2 = String.valueOf(sum);
        //�����תΪ�ַ���
        for (int i = 0; i < temp2.length(); i++) {
            n_sum[i] = temp2.charAt(i);
        }
        //���ַ���ָ���n_sum����
        for (int i = 0; i < temp2.length(); i++) {
            if (n_sum[i] == '0') {
                System.out.print("ling");

            }
            if (n_sum[i] == '1') {
                System.out.print("yi");

            }
            if (n_sum[i] == '2') {
                System.out.print("er");

            }
            if (n_sum[i] == '3') {
                System.out.print("san");

            }
            if (n_sum[i] == '4') {
                System.out.print("si");

            }
            if (n_sum[i] == '5') {
                System.out.print("wu");

            }
            if (n_sum[i] == '6') {
                System.out.print("liu");

            }
            if (n_sum[i] == '7') {
                System.out.print("qi");

            }
            if (n_sum[i] == '8') {
                System.out.print("ba");

            }
            if (n_sum[i] == '9') {
                System.out.print("jiu");

            }
            if (i < (temp2.length() - 1)) {
                System.out.print(" ");
            }

        }
    }
}