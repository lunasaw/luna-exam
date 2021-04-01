package com.luna.jvm.dayone;

/**
 * @author luna@mac
 * 2021年04月01日 19:42:00
 */
public class Main {

    public static void main(String[] args) {
        System.out.print(B.c);
        int x = 0;
        x = x >> 3;
    }
}

class A {
    public static String c = "C";
    static {
        System.out.print("A");
    }
}

class B extends A {
    static {
        System.out.print("B");
    }
}
