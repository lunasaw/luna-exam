package com.luna.jvm.overload;

public class Main {
    double method(int i) throws Exception {
        return i / 0;
    }

    boolean method(boolean b) {
        return !b;
    }

    static double method(int x, double y) throws Exception {
        return x + y;
    }

    static double method(double x, double y) {
        return x + y - 3;
    }

    public static void main(String[] args) {
        Main mn = new Main();
        try {
            System.out.println(method(10, 20.0));
            System.out.println(method(10.0, 20));
            System.out.println(method(10.0, 20.0));
            System.out.println(mn.method(10));
        } catch (Exception ex) {
            System.out.println("exception occoure: " + ex);
        }
    }
}