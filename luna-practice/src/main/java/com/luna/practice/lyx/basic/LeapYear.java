package com.luna.practice.lyx.basic;

/**
 * @author luna@mac
 * @className LeapYear.java
 * @description TODO
 * @createTime 2021年03月01日 15:13:00
 */
public class LeapYear {

    public static void main(String[] args) {
        leapYear(2000, 3000);
    }

    public static void leapYear(int start, int end) {
        for (int i = start; i <= end; i++) {
            if ((i % 400 == 0) || (i % 100 != 0) && (i % 4 == 0)) {
                System.out.println(i);
            }
        }
    }

}
