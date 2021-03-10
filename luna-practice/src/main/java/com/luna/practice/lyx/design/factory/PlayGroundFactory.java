package com.luna.practice.lyx.design.factory;

/**
 * @author luna@mac
 * @className PlayGroundFactory.java
 * @description TODO
 * @createTime 2021年03月08日 11:01:00
 */
public class PlayGroundFactory<T> extends AbstractFactory {

    @Override
    public PlayGround getPlayGround(Class c) {
        if (c.isAssignableFrom(MidsummerDream.class)) {
            return new MidsummerDream();
        } else if (c.isAssignableFrom(SpringSunshine.class)) {
            return new SpringSunshine();
        } else if (c.isAssignableFrom(RomanticSnow.class)) {
            return new RomanticSnow();
        } else {
            return null;
        }
    }

}

class MidsummerDream implements PlayGround {

    @Override
    public String getGround() {
        return "仲夏之梦";
    }
}

class SpringSunshine implements PlayGround {
    @Override
    public String getGround() {
        return "春日阳光";
    }
}

class RomanticSnow implements PlayGround {
    @Override
    public String getGround() {
        return "浪漫冬雪";
    }
}
