package com.luna.self.design.dynamic.porxy;

/**
 * @author luna@mac
 * @className ShowImage.java
 * @description TODO
 * @createTime 2021年03月09日 15:18:00
 */
public class ShowImage {

    public static void main(String[] args) {
        DynamicProxy dynamicProxy = new DynamicProxy();
        Image image = (Image)dynamicProxy.bind(new RealImage("Java.jpg"));
        image.display();
    }
}
