package com.luna.practice.lyx.design.factory;

/**
 * @author luna@mac
 * @className FactoryProducer.java
 * @description TODO
 * @createTime 2021年03月08日 10:28:00
 */
public class FactoryProducer<T> {

    public AbstractFactory getFactory(Class<T> factory) {
        if (factory.isAssignableFrom(PlayGroundFactory.class)) {
            return new PlayGroundFactory();
        } else if (factory.isAssignableFrom(ClothesFactory.class)) {
            return new ClothesFactory();
        } else {
            return null;
        }
    }

}
