package com.luna.practice.ls;

import java.util.Scanner;

/**
 * @author czy@win10
 * @date 2019/10/10 7:59
 */
public class StringMapping {
    public static void main(String[] args) {
        mapping();
    }

    public static void mapping() {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        //adsssfadf ss
        String[] strings = s.split("\\ ");
        System.out.println(strings[0]);
        System.out.println(strings[1]);
        int tag = 0;
        int firstindex = -1;
        int flag = 0;
        char[] big = null;
        char[] small = null;
        int i = 0;
        int j = 0;
        if (strings[0].length() >= strings[1].length()) {
            tag = 1;
        }
        if (tag == 1) {
            big = strings[0].toCharArray();
            small = strings[1].toCharArray();
        }
        while (i < big.length && j < small.length) {
            if (small[j] == big[i]) {
                flag++;
                if (i <= big.length - small.length) {
                    firstindex = i;
                }
                j++;
                i++;
            } else if (j != small.length) {
                i++;
                j = 0;
            } else {
                break;
            }
        }
//        i = strings[1].indexOf(strings[0]);
//        System.out.println(i);
//        System.out.println("flag->" + flag);
//        System.out.println("index->" + firstindex);
        if (firstindex == -1) {
            System.out.println("不匹配");
        } else if (big.length == small.length && flag == small.length) {
            System.out.println("匹配->" + firstindex);
        } else if (flag <= small.length && big.length != small.length) {
            firstindex = (firstindex - flag) + 1;
            System.out.println("匹配->" + firstindex);
        } else {
            System.out.println("匹配->" + firstindex);
        }
    }
}
