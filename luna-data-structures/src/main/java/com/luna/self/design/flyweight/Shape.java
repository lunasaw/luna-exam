package com.luna.self.design.flyweight;

/**
 * @author luna@mac
 * @className Shape.java
 * @description TODO
 * @createTime 2021年03月10日 10:11:00
 */
public interface Shape {
    void draw();
}

class Circle implements Shape {

    private int    x, y, radius;

    private String color;

    public Circle(String color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public Circle setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public Circle setY(int y) {
        this.y = y;
        return this;
    }

    public int getRadius() {
        return radius;
    }

    public Circle setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public String getColor() {
        return color;
    }

    public Circle setColor(String color) {
        this.color = color;
        return this;
    }

    @Override
    public void draw() {
        System.out.println("Circle: Draw() [Color : " + color
            + ", x : " + x + ", y :" + y + ", radius :" + radius);
    }
}