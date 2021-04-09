package com.luna.spring.aop;

/**
 * @author luna@mac
 * 2021年04月09日 08:37
 */
public class HelloAop implements MyAop {

    @Override
    @Luna(value = "我是luna自定义的注解")
    public void printAop() {
        System.out.println("1111 =======按下HelloAop.printAop");
    }

    @Override
    public void doPrint(String name) {
        System.out.println("1111 =======按下HelloAop.doPrint name = " + name);
    }

    @Override
    public void doPrint2(@Luna Aop aop) {
        System.out.println("1111 =======按下HelloAop.doPrint name=");
    }

    @Override
    public void getName(String name) {
        System.out.println("1111 =======按下HelloAop.getName name=" + name);
    }

}
