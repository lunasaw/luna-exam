package com.luna.spring.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author luna@mac
 * 2021年04月09日 14:14
 */
public class MyProxy {

    public static void main(String[] args) {

        RealMovie realmovie = new RealMovie();
        Movie movie = new Cinema(realmovie);
        movie.play();

        MaotaiJiu maotaijiu = new MaotaiJiu();

        InvocationHandler jingxiao2 = new GuitaiA(new Wuliangye());

        InvocationHandler jingxiao1 = new GuitaiA(maotaijiu);
        SellWine dynamicProxy = (SellWine)Proxy.newProxyInstance(MyProxy.class.getClassLoader(),
            MaotaiJiu.class.getInterfaces(), jingxiao1);
        dynamicProxy.mainJiu();
        SellWine dynamicProxy1 = (SellWine)Proxy.newProxyInstance(MaotaiJiu.class.getClassLoader(),
            MaotaiJiu.class.getInterfaces(), jingxiao2);

        dynamicProxy1.mainJiu();
        System.out.println(dynamicProxy.getClass().getName());
        System.out.println(dynamicProxy1.getClass().getClassLoader().getParent());
        System.out.println(dynamicProxy == dynamicProxy1);

    }
}

/**
 * 电影
 */
interface Movie {
    void play();
}

/**
 * 卖酒
 */
interface SellWine {
    void mainJiu();
}

class RealMovie implements Movie {

    @Override
    public void play() {
        System.out.println("您正在观看电影 《肖申克的救赎》");
    }
}

class MaotaiJiu implements SellWine {

    @Override
    public void mainJiu() {
        System.out.println("我卖得是茅台酒。");

    }

}

class Wuliangye implements SellWine {

    @Override
    public void mainJiu() {
        System.out.println("我卖得是五粮液。");
    }

}

/**
 * 静态代理 增强卖电影的操作
 */
class Cinema implements Movie {

    RealMovie movie;

    public Cinema(RealMovie movie) {
        super();
        this.movie = movie;
    }

    @Override
    public void play() {

        guanggao(true);

        movie.play();

        guanggao(false);
    }

    public void guanggao(boolean isStart) {
        if (isStart) {
            System.out.println("电影马上开始了，爆米花、可乐、口香糖9.8折，快来买啊！");
        } else {
            System.out.println("电影马上结束了，爆米花、可乐、口香糖9.8折，买回家吃吧！");
        }
    }

}

class GuitaiA implements InvocationHandler {

    /**
     * 被代理对象
     */
    private Object pingpai;

    public GuitaiA(Object pingpai) {
        this.pingpai = pingpai;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable {
        System.out.println("销售开始  柜台是： " + this.getClass().getSimpleName());
        method.invoke(pingpai, args);
        System.out.println("销售结束");
        return proxy;
    }

}
