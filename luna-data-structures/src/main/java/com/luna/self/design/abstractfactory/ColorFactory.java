package com.luna.self.design.abstractfactory;

/**
 * @author luna@mac
 * @className ColorFactory.java
 * @description TODO
 * @createTime 2021年03月08日 10:17:00
 */
public class ColorFactory extends AbstractFactory {

    public static final Integer RED   = 1;
    public static final Integer BLUE  = 2;
    public static final Integer GREEN = 3;

    @Override
    public Color getColor(int n) {
        if (n == RED) {
            return new Red();
        } else if (n == BLUE) {
            return new Blue();
        } else if (n == GREEN) {
            return new Green();
        } else {
            return null;
        }
    }

}

class Red implements Color {

    @Override
    public String getColor() {
        return "Red";
    }
}

class Blue implements Color {

    @Override
    public String getColor() {
        return "Blue";
    }

}

class Green implements Color {

    @Override
    public String getColor() {
        return "Green";
    }

}