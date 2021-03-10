package com.luna.practice.gw.Thread;

import java.util.Hashtable;

/**
 * @author Luna@win10
 * @date 2020/5/15 9:44
 */
public class Singleton {

    private volatile static Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        if (ourInstance == null) {
            synchronized (Singleton.class) {
                return new Singleton();
            }
        }
        return ourInstance;
    }

    private Hashtable<String,String> hashtable;

    private Singleton() {}
}
