package com.luna.self.design.singleton;

import java.io.Serializable;

/**
 * @author luna@mac
 * @className SingleObject.java
 * @description TODO
 *
 * @createTime 2021年03月08日 13:40:00
 */
public class Singleton implements Serializable, Cloneable {
    /**
     * 破坏单例模式的三种方式
     * 反射
     * 序列化
     * 克隆
     *
     * 解决方案如下：
     * 1、防止反射
     * 定义一个全局变量，当第二次创建的时候抛出异常
     * 2、防止克隆破坏
     * 重写clone(),直接返回单例对象
     * 3、防止序列化破坏
     * 添加readResolve(),返回Object对象
     */
    private static Singleton          instance = null;

    // 1.默认是第一次创建
    private static volatile boolean   isCreate = false;

    // 2.本类内部创建对象实例
    private static volatile Singleton volatileInstance;

    public Singleton() {
        if (isCreate) {
            throw new RuntimeException("已然被实例化一次，不能在实例化");
        }
        isCreate = true;
    }

    public Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                instance = new Singleton();
            }
        }
        return instance;
    }

    public static Singleton getStaticInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return instance;
    }

    /**
     * 防止序列化破环
     * 
     * @return
     */
    private Object readResolve() {
        return instance;
    }

}

/**
 * 懒汉式是定性的时间换空间，不加同步的懒汉式是线程不安全的
 */
class LazySingleton {
    private static LazySingleton instance = null;

    private LazySingleton() {

    }

    public LazySingleton getInstance() {
        if (instance == null) {
            // 双重检查加锁
            synchronized (LazySingleton.class) {
                instance = new LazySingleton();
            }
        }
        return instance;
    }
}

/**
 * 饿汉式是典型的空间换时间，当类装载的时候就会创建类实例，不管你用不用，先创建出来，然后每次调用的时候，就不需要判断了，节省了运行时间。
 */
class HungrySingleton {
    private static HungrySingleton instance = new HungrySingleton();

    private HungrySingleton() {}

    public HungrySingleton getInstance() {
        return instance;
    }
}

/**
 * 当getInstance方法第一次被调用的时候,它第一次读取SingletonHolder.instance，导致SingletonHolder类得到初始化；
 * 而这个类在装载并被初始化的时候，会初始化它的静态域，从而创建Singleton的实例，由于是静态的域，
 * 因此只会在虚拟机装载类的时候初始化一次，并由虚拟机来保证它的线程安全性。
 * 
 */
class SingletonHolder {
    /**
     * 静态初始化器，由JVM来保证线程安全
     */
    private static SingletonHolder instance = new SingletonHolder();

    private SingletonHolder() {}

    public SingletonHolder getInstance() {
        return SingletonHolder.instance;
    }
}
