package com.luna.practice.lyx.basic;

import java.util.Random;

/**
 * @author luna@mac
 * @className User.java
 * @description TODO
 * @createTime 2021年03月03日 18:53:00
 */
public class Shopping {

    public static void main(String[] args) {
        User user = new User("小明");
        Store store = new Store(2);
        user.setGoods(new int[store.getGoods().length]);
        // 物品good买count次
        try {
            int count = 3;
            int good = 1;
            store.sell(good, count);
            user.goods[good] += count;
            store.sell(good, count);
            user.goods[good] += count;
        } catch (SellException e) {
            System.out.println(e.message);
            e.printStackTrace();
        }
    }
}

class User {

    String name;

    int[]  goods;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public int[] getGoods() {
        return goods;
    }

    public User setGoods(int[] goods) {
        this.goods = goods;
        return this;
    }
}

class Store {

    /** goods[1]=2 表示物品1库存为2 */
    private int[] goods;

    public int[] getGoods() {
        return goods;
    }

    public Store setGoods(int[] goods) {
        this.goods = goods;
        return this;
    }

    public Store(int n) {
        this.goods = new int[n];
        // 初始化物品库存为2
        for (int i = 0; i < n; i++) {
            goods[i] = 2;
        }
    }

    public void sell(int i, int count) throws SellException {
        if (goods[i] < 0) {
            throw new SellException(999, "库存为0");
        }
        goods[i] -= count;
    }
}

class SellException extends RuntimeException {
    int    code;

    String message;

    public SellException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}