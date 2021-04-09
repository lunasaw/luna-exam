package com.luna.spring.aop;

/**
 * @author luna@mac
 * 2021年04月09日 08:39
 */
public class HelloAopTwo implements MyAop {
    @Override
    public void printAop() {
        System.out.println("2222 =======按下HelloAop.printAop");
    }

    @Override
    public void doPrint(String name) {
        System.out.println("2222 =======按下HelloAop.doPrint");
    }

    @Override
    public void doPrint2(@Luna Aop aop) {
        System.out.println("2222 =======按下HelloAop.doPrint name=");
    }

    @Override
    public void getName(String name) {
        System.out.println("2222 =======按下HelloAop.getName name=" + name);
    }
}
