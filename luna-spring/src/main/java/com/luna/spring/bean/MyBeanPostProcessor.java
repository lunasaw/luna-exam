package com.luna.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * bean的后置处理器
 * 分别在bean的初始化前后对bean对象提供自己的实例化逻辑
 * postProcessAfterInitialization：初始化之后对bean进行增强处理
 * postProcessBeforeInitialization：初始化之前对bean进行增强处理
 * 
 * @author LinJie
 * 
 */
public class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     * 对初始化之后的Bean进行处理
     * 参数：bean：即将初始化的bean
     * 参数：beanname：bean的名称
     * 返回值：返回给用户的那个bean,可以修改bean也可以返回一个新的bean
     * 
     * @param bean
     * @param beanname
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanname) throws BeansException {
        Student stu = null;
        System.out.println("对初始化之后的Bean进行处理,将Bean的成员变量的值修改了");
        if ("name".equals(beanname) || bean instanceof Student) {
            stu = (Student)bean;
            stu.setName("Jack");
        }
        return stu;
    }

    /**
     * 对初始化之前的Bean进行处理
     * 参数：bean：即将初始化的bean
     * 参数：beanname：bean的名称
     * 返回值：返回给用户的那个bean,可以修改bean也可以返回一个新的bean
     * 
     * @param bean
     * @param beanname
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanname) throws BeansException {
        System.out.println("对初始化之前的Bean进行处理,此时我的名字" + bean);
        return bean;
    }

}
