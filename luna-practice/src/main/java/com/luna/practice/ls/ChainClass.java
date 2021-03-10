package com.luna.practice.ls;

/**
 * @Package: com.luna.practice.ls
 * @ClassName: ChainClass
 * @Author: luna
 * @CreateTime: 2020/10/10 23:38
 * @Description:
 */
public class ChainClass {

    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public ChainClass setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public ChainClass setAge(Integer age) {
        this.age = age;
        return this;
    }

    public static void main(String[] args) {
        ChainClass chainClass = new ChainClass().setAge(11).setName("luna");
        chainClass.setName("wang");
        System.out.println(chainClass.getAge());
        System.out.println(chainClass.getName());
    }
}
