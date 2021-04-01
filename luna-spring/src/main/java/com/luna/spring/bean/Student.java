package com.luna.spring.bean;

import org.checkerframework.checker.initialization.qual.Initialized;
import org.springframework.beans.factory.BeanNameAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author LinJie
 * @Description:一个学生类(Bean)，能体现其生命周期的Bean
 */
public class Student implements BeanNameAware {
    private String name;

    /**
     * 无参构造方法
     */
    public Student() {
        super();
    }

    /**
     * 设置对象属性
     * 
     * @param name the name to set
     */
    public void setName(String name) {
        System.out.println("设置对象属性setName()..========= " + name);
        this.name = name;
    }

    /**
     * Bean注解的初始化方法
     */
    public void initStudent() {
        System.out.println("Student这个Bean：初始化 ========= Bean注解的初始化方法 initMethod 属性");
    }

    /**
     * Bean注解的销毁方法
     */
    public void destroyStudent() {
        System.out.println("Student这个Bean：销毁 ========= Bean注解的销毁方法 destroyMethod 属性");
    }

    /**
     * @PostConstruct 注解标注的方法为bean的初始化方法，
     */
    @PostConstruct
    public void postConstruct() {
        System.out.println("Student这个Bean：初始化 ========= 注解 PostConstruct 方法为bean的初始化方法，");
    }

    /**
     * @PostConstruct 注解标注的方法为bean的销毁方法，
     */
    @PreDestroy
    public void preDestroy() {
        System.out.println("Student这个Bean：销毁 ========= 注解 PreDestroy 方法为bean的销毁方法 ");
    }

    /**
     * Bean的使用
     */
    public void play() {
        System.out.println("Student这个Bean：使用");
    }

    /* 重写toString
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Student [name = " + name + "]";
    }

    // 调用BeanNameAware的setBeanName()
    // 传递Bean的ID。
    @Override
    public void setBeanName(String name) {
        System.out.println("调用BeanNameAware的setBeanName()...");
    }

}
