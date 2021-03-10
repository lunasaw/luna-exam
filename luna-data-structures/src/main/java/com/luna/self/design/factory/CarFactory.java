package com.luna.self.design.factory;

/**
 * @author luna@mac
 * @className CarFactory.java
 * @description TODO
 * @createTime 2021年03月08日 10:01:00
 */
public class CarFactory {

    public static final Integer BWM        = 1;
    public static final Integer PROSCHE    = 2;
    public static final Integer HOME_GUANG = 0;

    public Car produce(int n) {
        if (n == BWM) {
            return new Bmw();
        } else if (n == PROSCHE) {
            return new Prosche();
        } else {
            return new HongGuang();
        }
    }

    public static void main(String[] args) {
        CarFactory carFactory = new CarFactory();
        carFactory.produce(BWM).display();
        carFactory.produce(PROSCHE).display();
        carFactory.produce(HOME_GUANG).display();
    }

}

class Bmw implements Car {

    @Override
    public void display() {
        System.out.println("宝马是中速");
    }
}

class Prosche implements Car {

    @Override
    public void display() {
        System.out.println("保时捷是高速");
    }
}

class HongGuang implements Car {

    @Override
    public void display() {
        System.out.println("五菱宏光是飞速");
    }
}
