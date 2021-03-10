package com.luna.practice.lyx.design.proxy;

/**
 * @author luna@mac
 * @className Manufacturer.java
 * @description TODO
 * @createTime 2021年03月09日 14:07:00
 */
public interface Manufacturer {

    public void create();
}

class QiaoWei implements Manufacturer {

    @Override
    public void create() {
        System.out.println("我是乔伟，我能产衣服");
    }
}

class HuangZiXi implements Manufacturer {

    @Override
    public void create() {
        System.out.println("我是皇子鑫，我不能产衣服，但我可以找乔伟产衣服");
        Manufacturer manufacturer = new QiaoWei();
        manufacturer.create();
    }
}

class ZhouZhiYang {
    public static void main(String[] args) {
        System.out.println("我是皱智洋，我找中介产衣服");
        HuangZiXi huangZiXi = new HuangZiXi();
        huangZiXi.create();
    }
}
