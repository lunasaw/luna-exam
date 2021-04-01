package com.luna.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author luna@mac
 * 2021年03月31日 15:07:00
 */
public class MyBeanFactoryAware implements BeanFactoryAware {
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("这里可以监控BeanFactory============");
    }
}
