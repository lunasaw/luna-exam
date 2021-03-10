package com.luna.practice.lyx.basic;

/**
 * @author luna@mac
 * @className MyException.java
 * @description TODO
 * @createTime 2021年03月03日 18:38:00
 */
public class MyException {

    public static void main(String[] args) {
        try {
            int i = 1 / 0;
        } catch (ArithmeticException e) {
            e.printStackTrace();
        } finally {
        }

        try {
            String s = null;
            System.out.println(s.charAt(1));
        } catch (NullPointerException e) {
            e.printStackTrace();
        } finally {
        }

        try {
            System.out.println(Integer.parseInt("it"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally {
        }

        try {
            int[] arr = new int[]{1, 2};
            System.out.println(args[3]);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } finally {
        }

        try {
            Animal a1 = new Dog();
            Animal a2 = new Cat();
            Dog d1 = (Dog) a1;
            Dog d2 = (Dog) a2;
        } catch (ClassCastException e) {
            e.printStackTrace();
        } finally {
        }

    }

}

class Animal {

    public String name;
    public Integer age;

}

class Dog extends Animal {

}

class Cat extends Animal {

}