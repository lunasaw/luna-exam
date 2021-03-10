package com.luna.self.design.dynamic.porxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author luna@mac
 * @className DynamicProxy.java
 * @description TODO 动态代理
 * @createTime 2021年03月09日 14:56:00
 */
public class DynamicProxy implements InvocationHandler {

    private Object object;

    /**
     * 类加载器，一个包含表示类实现的所有接口的对象的数组
     * 
     * @return
     */
    public Object bind(Object object) {
        this.object = object;
        System.out.println("bind...." + object);
        Class<?>[] interfaces = this.object.getClass().getInterfaces();
        System.out.println("interfaces...." + interfaces.toString());
        // 绑定该类实现的所有接口，取得代理类
        Object newProxyInstance =
            Proxy.newProxyInstance(this.object.getClass().getClassLoader(), interfaces,
                this);
        System.out.println("newProxyInstance..." + newProxyInstance);
        return newProxyInstance;

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("pre execute proxy...");
        Object invoke = method.invoke(this.object, args);
        System.out.println("post execute proxy..." + invoke);
        return invoke;
    }
}
