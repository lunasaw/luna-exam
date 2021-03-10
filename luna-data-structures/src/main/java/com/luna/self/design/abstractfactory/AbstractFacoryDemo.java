package com.luna.self.design.abstractfactory;

/**
 * @author luna@mac
 * @className AbstractFacoryDemo.java
 * @description TODO
 * @createTime 2021年03月08日 10:39:00
 */
public class AbstractFacoryDemo {

    public static void main(String[] args) {
        FactoryProducer producer = new FactoryProducer();
        ColorFactory colorFactory = (ColorFactory)producer.getFactory(ColorFactory.class);
        Color color = colorFactory.getColor(ColorFactory.BLUE);
        System.out.println(color.getColor());
        CarFactory carFactory = (CarFactory)producer.getFactory(CarFactory.class);
        Car car = carFactory.getCar(CarFactory.HOME_GUANG);
        System.out.println(car.speed());
    }

}
