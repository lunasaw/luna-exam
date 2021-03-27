package com.luna.jvm.dayone;

import sun.misc.Launcher;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @Package: com.luna.jvm.dayone
 * @ClassName: DemoOne
 * @Author: luna
 * @CreateTime: 2020/9/1 20:35
 * @Description:
 */
public class DemoOne {

    public static void main(String[] args) {
        // int i = 2 + 3;
        int i = 2;
        int j = 3;
        int k = i + j;

        System.out.println("*****启动类加载器*******");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL);
        }

        // 为null 则是引导类加载器加载的类
        System.out.println(String.class.getClassLoader());

        System.out.println("*****扩展类加载器*******");

        HashSet<String> set = new HashSet();
        set.add("a");
    }
}
