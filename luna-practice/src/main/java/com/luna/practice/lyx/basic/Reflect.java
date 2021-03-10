package com.luna.practice.lyx.basic;

import org.checkerframework.checker.units.qual.K;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author luna@mac
 * @className Reflect.java
 * @description TODO
 * @createTime 2021年03月05日 17:50:00
 */
public class Reflect {

    public static void main(String[] args) throws Exception {

        // 新建Person
        Person person = new Person();
        // 获取Person的Class实例
        Class<Person> c = Person.class;
        // 获取 somebody() 方法的Method实例
        Method mSomebody = c.getMethod("somebody", new Class[]{String.class, int.class});
        // 执行该方法
        mSomebody.invoke(person, new Object[]{"lily", 18});
        iteratorAnnotations(mSomebody);

        // 获取 empty() 方法的Method实例
        Method mEmpty = c.getMethod("empty", new Class[]{});
        // 执行该方法
        mEmpty.invoke(person, new Object[]{});
        iteratorAnnotations(mEmpty);

        Field nameField = c.getDeclaredField("name");
        Field ageField = c.getDeclaredField("age");
        Person obj = c.newInstance();
        nameField.setAccessible(true);
        nameField.set(obj, "小明");
        ageField.setAccessible(true);
        ageField.set(obj, 20);
        System.out.println("姓名：" + nameField.get(obj));
        System.out.println("年龄：" + ageField.get(obj));


    }

    public static void iteratorAnnotations(Method method) {

        // 判断 somebody() 方法是否包含MyAnno注解
        if (method.isAnnotationPresent(MyAnno.class)) {
            // 获取该方法的MyAnnotation注解实例
            MyAnno myAnnotation = method.getAnnotation(MyAnno.class);
            // 获取 myAnnotation的值，并打印出来
            System.out.println(" 获取MyAnno的值，并打印出来");
            String[] values = myAnnotation.value();
            for (String str : values) {
                System.out.printf(str + ", ");
            }
            System.out.println();
        }

        // 获取方法上的所有注解，并打印出来
        System.out.println("获取方法上的所有注解，并打印出来");
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
    }

}

@MyAnno
class Person {

    private String name;

    private int age;

    @MyAnno
    public Person() {
    }


    @MyAnno
    public void empty() {
        System.out.println("\nempty");
    }

    /**
     * sombody() 被 @MyAnno(value={"girl","boy"}) 所标注，
     *
     * @MyAnno(value={"girl","boy"}), 意味着MyAnno的value值是{"girl","boy"}
     */
    @MyAnno(value = {"girl", "boy"})
    public void somebody(String name, int age) {
        System.out.println("\nsomebody: " + name + ", " + age);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}


