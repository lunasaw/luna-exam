package com.luna.spring.aop;

/**
 * @author luna@mac
 * 2021年04月09日 08:36
 */
public interface MyAop {

    void printAop();

    @Luna
    void doPrint(String name);

    public void doPrint2(@Luna Aop aop);

    void getName(String name);
}
