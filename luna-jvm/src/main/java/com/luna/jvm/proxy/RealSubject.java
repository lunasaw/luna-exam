package com.luna.jvm.proxy;

/**
 * 实际对象
 */
public class RealSubject implements Subject {

    /**
     * 你好
     *
     * @param name
     * @return
     */
    @Override
    public String SayHello(String name) {
        return "hello " + name;
    }

    /**
     * 再见
     *
     * @return
     */
    @Override
    public String SayGoodBye() {
        return " good bye ";
    }
}