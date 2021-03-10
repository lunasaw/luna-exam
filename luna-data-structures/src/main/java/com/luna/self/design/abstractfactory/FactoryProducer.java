package com.luna.self.design.abstractfactory;

/**
 * @author luna@mac
 * @className FactoryProducer.java
 * @description TODO
 * @createTime 2021年03月08日 10:28:00
 */
public class FactoryProducer<T> {

    public AbstractFactory getFactory(Class<T> factory) {
        if (factory.isAssignableFrom(ColorFactory.class)) {
            System.out.println(factory.getName());
            return new ColorFactory();
        } else if (factory.isAssignableFrom(CarFactory.class)) {
            System.out.println(factory.getName());
            return new CarFactory();
        } else {
            return null;
        }
    }

}
