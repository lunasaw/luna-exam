package com.luna.jvm.dayone;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;

/**
 * @Package: com.luna.jvm.dayone
 * @ClassName: DemoOne
 * @Author: luna
 * @CreateTime: 2020/9/1 20:35
 * @Description:
 */
public class DemoOne {

    public static void main(String[] args) throws IOException {
        // int i = 2 + 3;
        int i = 2;
        int j = 3;
        int k = i + j;

        System.out.println("*****启动类加载器*******");
        Enumeration<URL> resources = ClassLoader.getSystemResources("");
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            System.out.println(url.getFile());
        }
        // 为null 则是引导类加载器加载的类
        System.out.println(String.class.getClassLoader());

        System.out.println("*****扩展类加载器*******");

        HashSet<String> set = new HashSet();
        set.add("a");
    }
}
