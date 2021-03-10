package com.luna.self.design.decorator;

/**
 * @author luna@mac
 * @className Shape.java
 * @description TODO
 * @createTime 2021年03月09日 13:53:00
 */
public interface Shape {

    void draw();
}

class Circle implements Shape {

    @Override
    public void draw() {
        System.out.println("circle");
    }
}

class Rectangle implements Shape {

    @Override
    public void draw() {
        System.out.println("rectangle");
    }
}