package com.luna.self.design.prototype;

/**
 * @author luna@mac
 * @className Shape.java
 * @description TODO
 * @createTime 2021年03月09日 11:35:00
 */
public abstract class Shape implements Cloneable {

    private String   id;

    protected String type;

    abstract void draw();

    public String getId() {
        return id;
    }

    public Shape setId(String id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public Shape setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }
}

class Circle extends Shape {

    public Circle() {
        type = "Circle";
    }

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}

class Rectangle extends Shape {
    public Rectangle() {
        type = "Rectangle";
    }

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}

class Square extends Shape {
    public Square() {
        type = "Square";
    }

    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}