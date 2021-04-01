package com.luna.spring.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author luna@mac
 * 2021年03月31日 14:35:00
 */
public class MyFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        Student student = new Student();
        student.setName("MyFactoryBean");
        System.out.println("============== MyFactoryBean");
        return student;
    }

    @Override
    public Class<?> getObjectType() {
        return Student.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
