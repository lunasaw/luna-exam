package com.luna.jvm.dayone;

/**
 * @author luna@mac
 * 2021年04月01日 19:48:00
 */
class Value {
    public int i = 15;
}

public class Test {
    public static void main(String argv[]) {

        boolean a = false;
        Test t = new Test();
        t.first();
    }

    public void first() {
        int i = 5;
        Value v = new Value();
        v.i = 25;
        second(v, i);
        System.out.println(v.i);
    }

    public void second(Value v, int i) {
        i = 0;
        v.i = 20;
        Value val = new Value();
        v = val;
        System.out.println(v.i + " " + i);
    }
}