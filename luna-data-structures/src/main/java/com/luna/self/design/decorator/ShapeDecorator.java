package com.luna.self.design.decorator;

/**
 * @author luna@mac
 * @className ShapeDecorator.java
 * @description TODO
 * @createTime 2021年03月09日 14:27:00
 */
public abstract class ShapeDecorator implements Shape {

    protected Shape decoratedShape;

    public Shape getDecoratedShape() {
        return decoratedShape;
    }

    public ShapeDecorator setDecoratedShape(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
        return this;
    }

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

    @Override
    public void draw() {
        decoratedShape.draw();
    }
}

class RedShapeDecorator extends ShapeDecorator {

    public RedShapeDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        decoratedShape.draw();
        setDecoratedShape(decoratedShape);
    }

    private void setRedBorder(Shape decoratedShape) {
        System.out.println("Border Color: Red");
    }
}

class DecoratorPatternDemo {
    public static void main(String[] args) {

        Shape circle = new Circle();
        ShapeDecorator redCircle = new RedShapeDecorator(new Circle());
        ShapeDecorator redRectangle = new RedShapeDecorator(new Rectangle());
        // Shape redCircle = new RedShapeDecorator(new Circle());
        // Shape redRectangle = new RedShapeDecorator(new Rectangle());
        System.out.println("Circle with normal border");
        circle.draw();

        System.out.println("\nCircle of red border");
        redCircle.draw();

        System.out.println("\nRectangle of red border");
        redRectangle.draw();
    }
}