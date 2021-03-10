package com.luna.practice.lyx.design.singleton;

/**
 * @author luna@mac
 * @className Singleton.java
 * @description TODO
 * @createTime 2021年03月08日 14:06:00
 */
public class Singleton {

    private static Singleton singleton = new Singleton();

    private Singleton() {}

    public Singleton getInstance() {
        return singleton;
    }

    public void showMessage() {
        System.out.println("hello world");
    }
}

class LazySingleton {
    private static LazySingleton instance = null;

    private LazySingleton() {

    }

    public LazySingleton getInstance() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                instance = new LazySingleton();
            }
        }
        return instance;
    }

    public void showMessage() {
        System.out.println(instance);
    }
}