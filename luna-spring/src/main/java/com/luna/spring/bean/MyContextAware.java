package com.luna.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author luna@mac
 * 2021年03月31日 15:11:00
 */
public class MyContextAware implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("这里可以监控ApplicationContextAware ============");
    }
}
