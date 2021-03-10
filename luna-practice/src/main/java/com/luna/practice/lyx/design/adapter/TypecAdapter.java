package com.luna.practice.lyx.design.adapter;

/**
 * @author luna@mac
 * @className Typec.java
 * @description TODO
 * @createTime 2021年03月09日 11:04:00
 */
public interface TypecAdapter {

    public void useUdisk();

    public void usePower();

    public void useHdmi();
}

class Disk implements TypecAdapter {

    @Override
    public void useUdisk() {
        System.out.println("typc 使用Disk");
    }

    @Override
    public void usePower() {

    }

    @Override
    public void useHdmi() {

    }
}

class Power implements TypecAdapter {

    @Override
    public void useUdisk() {

    }

    @Override
    public void usePower() {
        System.out.println("typc 使用电源");
    }

    @Override
    public void useHdmi() {}
}

class Hdmi implements TypecAdapter {

    @Override
    public void useUdisk() {

    }

    @Override
    public void usePower() {

    }

    @Override
    public void useHdmi() {
        System.out.println("typc 使用HDMI");
    }
}