package com.luna.self.design.builder;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luna@mac
 * @className FastFood.java
 * @description TODO
 * @createTime 2021年03月08日 15:44:00
 */
public class FastFood {

    private String burger;

    private String drink;

    public String getBurger() {
        return burger;
    }

    public FastFood setBurger(String burger) {
        this.burger = burger;
        return this;
    }

    public String getDrink() {
        return drink;
    }

    public FastFood setDrink(String drink) {
        this.drink = drink;
        return this;
    }

    public static void main(String[] args) {
        List<FastFood> fastFoodList = new ArrayList<>();
        fastFoodList.add(new FastFood().setBurger("素质汉堡").setDrink("coke"));
        fastFoodList.add(new FastFood().setBurger("鸡肉汉堡").setDrink("coke"));
        fastFoodList.add(new FastFood().setBurger("素质汉堡").setDrink("pepsi"));
        fastFoodList.add(new FastFood().setBurger("鸡肉汉堡").setDrink("pepsi"));
    }
}
