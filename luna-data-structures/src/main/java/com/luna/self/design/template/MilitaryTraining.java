package com.luna.self.design.template;

/**
 * @author luna@mac
 * @className MilitaryTraining.java
 * @description TODO
 * @createTime 2021年03月12日 11:15:00
 */
public abstract class MilitaryTraining {

    abstract void foldTheQuilt();

    abstract void standingStandard();

    abstract void singSong();

    public void militaryTraining() {
        foldTheQuilt();

        standingStandard();

        singSong();
    }
}

class EveryDay extends MilitaryTraining {

    @Override
    void foldTheQuilt() {
        System.out.println("叠豆腐块");
    }

    @Override
    void standingStandard() {
        System.out.println("站立军姿");
    }

    @Override
    void singSong() {
        System.out.println("唱军歌");
    }

    public static void main(String[] args) {
        EveryDay everyDay = new EveryDay();
        everyDay.militaryTraining();
    }
}
