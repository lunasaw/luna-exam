package com.luna.practice.lyx.design.factory;

/**
 * @author luna@mac
 * @className AbstractFactoryDemo.java
 * @description TODO
 * @createTime 2021年03月08日 11:16:00
 */
public class AbstractFactoryDemo {

    public static void main(String[] args) {
        FactoryProducer producer = new FactoryProducer();
        ClothesFactory clothesFactory = (ClothesFactory)producer.getFactory(ClothesFactory.class);
        Clothes clothes1 = clothesFactory.getClothes(YouthGraduationSeason.class);
        System.out.println(clothes1.getClothes());
        Clothes clothes2 = clothesFactory.getClothes(FantasyLand.class);
        System.out.println(clothes2.getClothes());
        Clothes clothes3 = clothesFactory.getClothes(ChangedTshirt.class);
        System.out.println(clothes3.getClothes());
        PlayGroundFactory playGroundFactory = (PlayGroundFactory)producer.getFactory(PlayGroundFactory.class);
        PlayGround playGround1 = playGroundFactory.getPlayGround(MidsummerDream.class);
        System.out.println(playGround1.getGround());
        PlayGround playGround2 = playGroundFactory.getPlayGround(SpringSunshine.class);
        System.out.println(playGround2.getGround());
        PlayGround playGround3 = playGroundFactory.getPlayGround(RomanticSnow.class);
        System.out.println(playGround3.getGround());
    }

}
