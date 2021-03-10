package com.luna.self.design.abstractfactory;

/**
 * @author luna@mac
 * @className CarFactory.java
 * @description TODO
 * @createTime 2021年03月08日 10:01:00
 */
public class CarFactory extends AbstractFactory {

    public static final Integer BWM        = 1;
    public static final Integer PROSCHE    = 2;
    public static final Integer HOME_GUANG = 3;

    @Override
    public Car getCar(int n) {
        if (n == BWM) {
            return new Bmw();
        } else if (n == PROSCHE) {
            return new Prosche();
        } else if (n == HOME_GUANG) {
            return new HongGuang();
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        CarFactory carFactory = new CarFactory();
        carFactory.getCar(BWM).speed();
        carFactory.getCar(PROSCHE).speed();
        carFactory.getCar(HOME_GUANG).speed();
    }

}

class Bmw implements Car {

    @Override
    public String speed() {
        return "宝马是中速";
    }
}

class Prosche implements Car {

    @Override
    public String speed() {
        return "保时捷是高速";
    }
}

class HongGuang implements Car {

    @Override
    public String speed() {
        return "五菱宏光是飞速";
    }
}
