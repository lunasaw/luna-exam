package com.luna.practice.lyx.design.factory;

/**
 * @author luna@mac
 * @className CLothesFactory.java
 * @description TODO
 * @createTime 2021年03月08日 11:07:00
 */
public class ClothesFactory<T> extends AbstractFactory {

    @Override
    public Clothes getClothes(Class c) {
        if (c.isAssignableFrom(ChangedTshirt.class)) {
            return new ChangedTshirt();
        } else if (c.isAssignableFrom(YouthGraduationSeason.class)) {
            return new YouthGraduationSeason();
        } else if (c.isAssignableFrom(FantasyLand.class)) {
            return new FantasyLand();
        } else {
            return null;
        }
    }

}

class ChangedTshirt implements Clothes {

    @Override
    public String getClothes() {
        return "百变T恤";
    }
}

class YouthGraduationSeason implements Clothes {

    @Override
    public String getClothes() {
        return "青春毕业季";
    }
}

class FantasyLand implements Clothes {

    @Override
    public String getClothes() {
        return "梦幻国度";
    }
}