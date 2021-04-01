package com.luna.spring.bean;

import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luna@mac
 * 2021年03月31日 11:28:00
 */
@Configuration
public class SpringConfiguration {

    @Bean(destroyMethod = "destroyStudent", initMethod = "initStudent")
    public Student student() {
        Student student = new Student();
        student.setName("luna");
        return student;
    }

    @Bean
    public MyBeanPostProcessor myBeanPostProcessor() {
        return new MyBeanPostProcessor();
    }

    @Bean
    public MyInitializeBean myInitializeBean() {
        return new MyInitializeBean();
    }

    public Person person() {
        return new Person();
    }

    @Bean
    public MyBeanFactoryPostProcessor myBeanFactoryPostProcessor() {
        return new MyBeanFactoryPostProcessor();
    }

    @Bean
    public MyFactoryBean myFactoryBean() {
        return new MyFactoryBean();
    }

    @Bean
    public BeanFactoryAware beanFactoryAware() {
        return new MyBeanFactoryAware();
    }

    @Bean
    public MyContextAware myContextAware() {
        return new MyContextAware();
    }
}
