package com.luna.self.design.bridge;

/**
 * @author luna@mac
 * @className Begin.java
 * @description TODO
 * @createTime 2021年03月10日 10:00:00
 */
public interface Begin {
    void man(int w, int h);
}

class Human implements Begin {

    @Override
    public void man(int w, int h) {
        System.out.println("人形态高：" + h + "体重：" + w);
    }
}

class God implements Begin {

    @Override
    public void man(int w, int h) {
        System.out.println("神仙态高：" + h + "体重：" + w);
    }
}

abstract class Soul {
    public Begin begin;

    public Soul(Begin begin) {
        this.begin = begin;
    }

    public abstract void soul();
}

class Bajie extends Soul {

    private int weight, height;

    public Bajie(Begin begin, int weight, int height) {
        super(begin);
        this.weight = weight;
        this.height = height;
    }

    @Override
    public void soul() {
        begin.man(weight, height);
    }

    public static void main(String[] args) {
        Bajie bajieMan = new Bajie(new Human(), 250, 180);
        Bajie bajieGod = new Bajie(new God(), 100, 180);
        bajieMan.soul();
        bajieGod.soul();
    }
}